-- migration: create_household_members
-- description: creates the join table for the many-to-many relationship between users and households
-- affected tables: public.household_members

create table public.household_members (
  id uuid primary key default uuid_generate_v4(),
  household_id uuid references public.households(id) on delete cascade,
  user_id uuid references auth.users(id) on delete cascade,
  role text check (role in ('owner', 'member')) default 'member',
  joined_at timestamp with time zone default now(),
  -- prevent duplicate memberships
  unique(household_id, user_id)
);

alter table public.household_members enable row level security;

-- indexes for efficient lookups from both sides of the relationship
create index household_members_household_id_idx on public.household_members(household_id);
create index household_members_user_id_idx on public.household_members(user_id);
