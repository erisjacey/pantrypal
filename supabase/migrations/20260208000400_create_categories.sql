-- migration: create_categories
-- description: creates categories table for organizing pantry items (dairy, vegetables, etc.)
-- note: this is shared reference data accessible to all users including anonymous
-- affected tables: public.categories

create table public.categories (
  id uuid primary key default uuid_generate_v4(),
  name text not null unique,
  icon text,
  color text,
  created_at timestamp with time zone default now()
);

-- rls is enabled even though categories are public reference data
-- access is controlled via granular policies in the rls migration
alter table public.categories enable row level security;
