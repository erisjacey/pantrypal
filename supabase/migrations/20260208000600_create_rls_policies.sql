-- migration: create_rls_policies
-- description: creates granular row level security policies for all tables.
--   policies are separated per operation (select, insert, update, delete)
--   and per role (anon, authenticated) as per supabase best practices.
--   auth.uid() is wrapped in (select ...) for query planner optimization.
--
--   security definer helper functions are used to check household membership.
--   this avoids infinite recursion when household_members policies reference
--   the household_members table itself.
-- affected tables: households, household_members, pantries, categories, items

-- ============================================
-- helper functions (security definer)
-- these bypass rls to check membership without triggering policy recursion
-- ============================================

-- check if the current authenticated user is a member of the given household
create or replace function public.is_member_of_household(p_household_id uuid)
returns boolean
language sql
security definer
set search_path = ''
stable
as $$
  select exists (
    select 1 from public.household_members
    where household_id = p_household_id
    and user_id = auth.uid()
  );
$$;

-- check if the current authenticated user is an owner of the given household
create or replace function public.is_owner_of_household(p_household_id uuid)
returns boolean
language sql
security definer
set search_path = ''
stable
as $$
  select exists (
    select 1 from public.household_members
    where household_id = p_household_id
    and user_id = auth.uid()
    and role = 'owner'
  );
$$;

-- ============================================
-- households policies
-- ============================================

-- authenticated users can view households they belong to
create policy "authenticated users can view their households"
  on public.households for select
  to authenticated
  using (public.is_member_of_household(id));

-- authenticated users can create households (must be the creator)
create policy "authenticated users can create households"
  on public.households for insert
  to authenticated
  with check ((select auth.uid()) = created_by);

-- only household owners can update their households
create policy "authenticated owners can update households"
  on public.households for update
  to authenticated
  using (public.is_owner_of_household(id))
  with check (public.is_owner_of_household(id));

-- only household owners can delete their households
create policy "authenticated owners can delete households"
  on public.households for delete
  to authenticated
  using (public.is_owner_of_household(id));

-- ============================================
-- household_members policies
-- ============================================

-- authenticated users can view members of households they belong to
create policy "authenticated users can view household members"
  on public.household_members for select
  to authenticated
  using (public.is_member_of_household(household_id));

-- only household owners can add new members
create policy "authenticated owners can add members"
  on public.household_members for insert
  to authenticated
  with check (public.is_owner_of_household(household_id));

-- only household owners can update member roles
create policy "authenticated owners can update member roles"
  on public.household_members for update
  to authenticated
  using (public.is_owner_of_household(household_id))
  with check (public.is_owner_of_household(household_id));

-- only household owners can remove members
create policy "authenticated owners can remove members"
  on public.household_members for delete
  to authenticated
  using (public.is_owner_of_household(household_id));

-- ============================================
-- pantries policies
-- ============================================

-- authenticated household members can view pantries in their households
create policy "authenticated members can view pantries"
  on public.pantries for select
  to authenticated
  using (public.is_member_of_household(household_id));

-- authenticated household members can create pantries
create policy "authenticated members can create pantries"
  on public.pantries for insert
  to authenticated
  with check (public.is_member_of_household(household_id));

-- authenticated household members can update pantries
create policy "authenticated members can update pantries"
  on public.pantries for update
  to authenticated
  using (public.is_member_of_household(household_id))
  with check (public.is_member_of_household(household_id));

-- authenticated household members can delete pantries
create policy "authenticated members can delete pantries"
  on public.pantries for delete
  to authenticated
  using (public.is_member_of_household(household_id));

-- ============================================
-- categories policies
-- categories are public reference data, readable by everyone
-- ============================================

-- anonymous users can view categories (public reference data)
create policy "anon users can view categories"
  on public.categories for select
  to anon
  using (true);

-- authenticated users can view categories (public reference data)
create policy "authenticated users can view categories"
  on public.categories for select
  to authenticated
  using (true);

-- authenticated users can create new categories
create policy "authenticated users can create categories"
  on public.categories for insert
  to authenticated
  with check ((select auth.uid()) is not null);

-- ============================================
-- items policies
-- ============================================

-- authenticated household members can view items in their pantries
create policy "authenticated members can view items"
  on public.items for select
  to authenticated
  using (
    pantry_id in (
      select p.id from public.pantries p
      where public.is_member_of_household(p.household_id)
    )
  );

-- authenticated household members can add items to their pantries
create policy "authenticated members can add items"
  on public.items for insert
  to authenticated
  with check (
    pantry_id in (
      select p.id from public.pantries p
      where public.is_member_of_household(p.household_id)
    )
  );

-- authenticated household members can update items in their pantries
create policy "authenticated members can update items"
  on public.items for update
  to authenticated
  using (
    pantry_id in (
      select p.id from public.pantries p
      where public.is_member_of_household(p.household_id)
    )
  )
  with check (
    pantry_id in (
      select p.id from public.pantries p
      where public.is_member_of_household(p.household_id)
    )
  );

-- authenticated household members can delete items from their pantries
create policy "authenticated members can delete items"
  on public.items for delete
  to authenticated
  using (
    pantry_id in (
      select p.id from public.pantries p
      where public.is_member_of_household(p.household_id)
    )
  );
