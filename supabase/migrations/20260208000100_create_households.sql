-- migration: create_households
-- description: creates the households table for grouping users (families, roommates, etc.)
-- affected tables: public.households

create table public.households (
  id uuid primary key default uuid_generate_v4(),
  name text not null,
  created_by uuid references auth.users(id) on delete cascade,
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now()
);

-- enable rls even though policies are defined in a separate migration
alter table public.households enable row level security;

-- index for looking up households by creator
create index households_created_by_idx on public.households(created_by);
