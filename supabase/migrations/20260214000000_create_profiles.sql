-- migration: create_profiles
-- description: creates user profiles table with auto-creation trigger.
--   profiles store display names and optional avatars so household members
--   can be identified by name instead of raw user ids.
--   a trigger auto-creates a profile row on sign-up using the email prefix
--   as the default display name.
-- affected tables: profiles (new)

-- ============================================
-- profiles table
-- ============================================

create table public.profiles (
  id uuid primary key references auth.users (id) on delete cascade,
  display_name text not null,
  avatar_url text,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

alter table public.profiles enable row level security;

-- ============================================
-- rls policies
-- ============================================

-- any authenticated user can view any profile (needed to show member names)
create policy "authenticated users can view profiles"
  on public.profiles for select
  to authenticated
  using (true);

-- users can only insert their own profile
create policy "users can insert own profile"
  on public.profiles for insert
  to authenticated
  with check (id = (select auth.uid()));

-- users can only update their own profile
create policy "users can update own profile"
  on public.profiles for update
  to authenticated
  using (id = (select auth.uid()))
  with check (id = (select auth.uid()));

-- ============================================
-- auto-create profile on sign-up
-- ============================================

create or replace function public.handle_new_user()
returns trigger
language plpgsql
security definer
set search_path = ''
as $$
begin
  insert into public.profiles (id, display_name)
  values (
    new.id,
    coalesce(
      new.raw_user_meta_data ->> 'display_name',
      split_part(new.email, '@', 1)
    )
  );
  return new;
end;
$$;

create trigger on_auth_user_created
  after insert on auth.users
  for each row
  execute function public.handle_new_user();

-- ============================================
-- updated_at trigger (reuse pattern from migration 07)
-- ============================================

create trigger set_profiles_updated_at
  before update on public.profiles
  for each row
  execute function public.update_updated_at_column();
