-- migration: create_rls_policies
-- description: creates granular row level security policies for all tables.
--   policies are separated per operation (select, insert, update, delete)
--   and per role (anon, authenticated) as per supabase best practices.
--   auth.uid() is wrapped in (select ...) for query planner optimization.
-- affected tables: households, household_members, pantries, categories, items

-- ============================================
-- households policies
-- ============================================

-- authenticated users can view households they belong to
create policy "authenticated users can view their households"
  on public.households for select
  to authenticated
  using (
    id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  );

-- authenticated users can create households (must be the creator)
create policy "authenticated users can create households"
  on public.households for insert
  to authenticated
  with check ((select auth.uid()) = created_by);

-- only household owners can update their households
create policy "authenticated owners can update households"
  on public.households for update
  to authenticated
  using (
    id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  )
  with check (
    id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  );

-- only household owners can delete their households
create policy "authenticated owners can delete households"
  on public.households for delete
  to authenticated
  using (
    id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  );

-- ============================================
-- household_members policies
-- ============================================

-- authenticated users can view members of households they belong to
create policy "authenticated users can view household members"
  on public.household_members for select
  to authenticated
  using (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  );

-- only household owners can add new members
create policy "authenticated owners can add members"
  on public.household_members for insert
  to authenticated
  with check (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  );

-- only household owners can update member roles
create policy "authenticated owners can update member roles"
  on public.household_members for update
  to authenticated
  using (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  )
  with check (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  );

-- only household owners can remove members
create policy "authenticated owners can remove members"
  on public.household_members for delete
  to authenticated
  using (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid()) and role = 'owner'
    )
  );

-- ============================================
-- pantries policies
-- ============================================

-- authenticated household members can view pantries in their households
create policy "authenticated members can view pantries"
  on public.pantries for select
  to authenticated
  using (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  );

-- authenticated household members can create pantries
create policy "authenticated members can create pantries"
  on public.pantries for insert
  to authenticated
  with check (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  );

-- authenticated household members can update pantries
create policy "authenticated members can update pantries"
  on public.pantries for update
  to authenticated
  using (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  )
  with check (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  );

-- authenticated household members can delete pantries
create policy "authenticated members can delete pantries"
  on public.pantries for delete
  to authenticated
  using (
    household_id in (
      select household_id from public.household_members
      where user_id = (select auth.uid())
    )
  );

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
      join public.household_members hm on p.household_id = hm.household_id
      where hm.user_id = (select auth.uid())
    )
  );

-- authenticated household members can add items to their pantries
create policy "authenticated members can add items"
  on public.items for insert
  to authenticated
  with check (
    pantry_id in (
      select p.id from public.pantries p
      join public.household_members hm on p.household_id = hm.household_id
      where hm.user_id = (select auth.uid())
    )
  );

-- authenticated household members can update items in their pantries
create policy "authenticated members can update items"
  on public.items for update
  to authenticated
  using (
    pantry_id in (
      select p.id from public.pantries p
      join public.household_members hm on p.household_id = hm.household_id
      where hm.user_id = (select auth.uid())
    )
  )
  with check (
    pantry_id in (
      select p.id from public.pantries p
      join public.household_members hm on p.household_id = hm.household_id
      where hm.user_id = (select auth.uid())
    )
  );

-- authenticated household members can delete items from their pantries
create policy "authenticated members can delete items"
  on public.items for delete
  to authenticated
  using (
    pantry_id in (
      select p.id from public.pantries p
      join public.household_members hm on p.household_id = hm.household_id
      where hm.user_id = (select auth.uid())
    )
  );
