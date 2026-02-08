-- migration: create_items
-- description: creates the items table for batch-based inventory tracking
-- note: each row represents one purchase batch of a product.
--   multiple batches of the same product (e.g., milk) are stored as separate rows.
--   unit conversion logic lives in edge functions, not in the database.
-- affected tables: public.items

create table public.items (
  id uuid primary key default uuid_generate_v4(),
  pantry_id uuid references public.pantries(id) on delete cascade,
  category_id uuid references public.categories(id) on delete set null,

  -- product identification
  -- same barcode = same product, but different batches (rows)
  name text not null,
  barcode text,
  description text,

  -- quantity tracking
  -- stored as-is from user input; conversion happens in edge functions
  initial_quantity decimal(10, 2) not null,
  current_quantity decimal(10, 2) not null,
  unit text not null,

  -- date tracking
  purchase_date date not null,
  expiry_date date,
  opened_date date, -- some items expire faster once opened

  -- financial tracking (per batch)
  unit_price decimal(10, 2),
  total_price decimal(10, 2),
  currency text default 'SGD',

  -- metadata
  image_url text,
  notes text,
  added_by uuid references auth.users(id),

  -- consumption tracking (soft delete preserves history)
  is_consumed boolean default false,
  consumed_at timestamp with time zone,

  -- timestamps
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now(),

  -- current quantity must be between 0 and initial quantity
  constraint current_quantity_check
    check (current_quantity >= 0 and current_quantity <= initial_quantity)
);

alter table public.items enable row level security;

-- constrain units to known values supported by the edge functions domain layer
alter table public.items
  add constraint unit_check check (
    unit in (
      -- volume units
      'ml', 'L', 'fl_oz', 'cup', 'tbsp', 'tsp', 'gal', 'pt', 'qt',
      -- weight units
      'mg', 'g', 'kg', 'oz', 'lb',
      -- count units
      'piece', 'dozen', 'package'
    )
  );

-- indexes for common query patterns
create index items_pantry_id_idx on public.items(pantry_id);
create index items_barcode_idx on public.items(barcode);
-- partial indexes: only index non-consumed items for active inventory queries
create index items_expiry_date_idx on public.items(expiry_date) where is_consumed = false;
create index items_name_idx on public.items(name) where is_consumed = false;
create index items_added_by_idx on public.items(added_by);
