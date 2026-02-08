-- migration: create_pantries
-- description: creates pantries table for storage locations within a household (fridge, freezer, etc.)
-- affected tables: public.pantries

create table public.pantries (
  id uuid primary key default uuid_generate_v4(),
  household_id uuid references public.households(id) on delete cascade,
  name text not null,
  location text,
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now()
);

alter table public.pantries enable row level security;

-- index for looking up pantries by household
create index pantries_household_id_idx on public.pantries(household_id);
