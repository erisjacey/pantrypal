-- migration: household_creation_and_invite_codes
-- description: adds invite code support to households and creates security definer
--   functions for household creation and joining. solves the rls deadlock where
--   the first member can't be inserted because no owner exists yet.
-- affected tables: public.households
-- new functions: generate_invite_code, create_household_with_owner,
--   join_household_with_code, regenerate_invite_code

-- ============================================================================
-- add invite_code column to households
-- ============================================================================

alter table public.households
  add column invite_code text unique;

-- index for fast lookups when joining by code
create index households_invite_code_idx on public.households(invite_code);

-- ============================================================================
-- generate_invite_code() — helper to create a random 6-char alphanumeric code
-- uses ambiguity-safe charset (no 0/O/1/I) to avoid user confusion
-- security invoker: no special privileges needed
-- volatile: generates random values
-- ============================================================================

create or replace function public.generate_invite_code()
returns text
language plpgsql
security invoker
set search_path = ''
volatile
as $$
declare
  code text;
  chars text := 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789';
  i int;
begin
  loop
    code := '';
    for i in 1..6 loop
      code := code || substr(chars, floor(random() * length(chars) + 1)::int, 1);
    end loop;
    -- check for collision
    if not exists (select 1 from public.households where invite_code = code) then
      return code;
    end if;
  end loop;
end;
$$;

-- ============================================================================
-- create_household_with_owner(p_name) — atomically creates household + first member
-- security definer: bypasses rls so the first member insert succeeds
-- (the rls policy on household_members requires is_owner_of_household, which
-- can't be true for the very first member being added)
-- volatile: inserts data
-- ============================================================================

create or replace function public.create_household_with_owner(p_name text)
returns public.households
language plpgsql
security definer
set search_path = ''
volatile
as $$
declare
  v_household public.households;
  v_user_id uuid := auth.uid();
begin
  if v_user_id is null then
    raise exception 'Not authenticated';
  end if;

  if trim(p_name) = '' then
    raise exception 'Household name cannot be empty';
  end if;

  -- create the household with an invite code
  insert into public.households (name, created_by, invite_code)
  values (trim(p_name), v_user_id, public.generate_invite_code())
  returning * into v_household;

  -- add the creator as owner (bypasses rls via security definer)
  insert into public.household_members (household_id, user_id, role)
  values (v_household.id, v_user_id, 'owner');

  return v_household;
end;
$$;

-- ============================================================================
-- join_household_with_code(p_invite_code) — joins a household using invite code
-- security definer: bypasses rls for the member insert
-- volatile: inserts data
-- ============================================================================

create or replace function public.join_household_with_code(p_invite_code text)
returns public.households
language plpgsql
security definer
set search_path = ''
volatile
as $$
declare
  v_household public.households;
  v_user_id uuid := auth.uid();
begin
  if v_user_id is null then
    raise exception 'Not authenticated';
  end if;

  -- look up the household by invite code (case-insensitive)
  select * into v_household
  from public.households
  where invite_code = upper(trim(p_invite_code));

  if v_household.id is null then
    raise exception 'Invalid invite code';
  end if;

  -- check user isn't already a member
  if exists (
    select 1 from public.household_members
    where household_id = v_household.id
    and user_id = v_user_id
  ) then
    raise exception 'Already a member of this household';
  end if;

  -- add user as member
  insert into public.household_members (household_id, user_id, role)
  values (v_household.id, v_user_id, 'member');

  return v_household;
end;
$$;

-- ============================================================================
-- regenerate_invite_code(p_household_id) — generates a new invite code
-- security definer: needs to bypass rls to update the household
-- volatile: modifies data
-- ============================================================================

create or replace function public.regenerate_invite_code(p_household_id uuid)
returns text
language plpgsql
security definer
set search_path = ''
volatile
as $$
declare
  v_new_code text;
  v_user_id uuid := auth.uid();
begin
  if v_user_id is null then
    raise exception 'Not authenticated';
  end if;

  -- only household owners can regenerate the invite code
  if not exists (
    select 1 from public.household_members
    where household_id = p_household_id
    and user_id = v_user_id
    and role = 'owner'
  ) then
    raise exception 'Only household owners can regenerate invite codes';
  end if;

  v_new_code := public.generate_invite_code();

  update public.households
  set invite_code = v_new_code
  where id = p_household_id;

  return v_new_code;
end;
$$;
