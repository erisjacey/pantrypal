-- migration: create_functions
-- description: creates utility functions and triggers for the database.
--   includes auto-updating updated_at timestamps and simple aggregation helpers.
--   note: unit conversion logic lives in edge functions, not here.
-- affected tables: households, pantries, items

-- ============================================
-- auto-update updated_at timestamp on row modification
-- ============================================

create or replace function public.update_updated_at_column()
returns trigger as $$
begin
  new.updated_at = now();
  return new;
end;
$$ language plpgsql;

-- apply the updated_at trigger to all tables that have that column
create trigger update_households_updated_at
  before update on public.households
  for each row execute function public.update_updated_at_column();

create trigger update_pantries_updated_at
  before update on public.pantries
  for each row execute function public.update_updated_at_column();

create trigger update_items_updated_at
  before update on public.items
  for each row execute function public.update_updated_at_column();

-- ============================================
-- simple aggregation helpers
-- these do basic grouping only; unit-aware aggregation is in edge functions
-- ============================================

-- get a summary of a single product (by barcode) within a pantry
-- note: only aggregates items with the same unit; cross-unit aggregation
-- is handled by the get-pantry-inventory edge function
create or replace function public.get_item_summary(
  p_pantry_id uuid,
  p_barcode text
)
returns table (
  name text,
  total_quantity decimal,
  unit text,
  batch_count bigint,
  oldest_expiry date,
  avg_unit_price decimal,
  total_value decimal
) as $$
begin
  return query
  select
    i.name,
    sum(i.current_quantity) as total_quantity,
    i.unit,
    count(*) as batch_count,
    min(i.expiry_date) as oldest_expiry,
    avg(i.unit_price) as avg_unit_price,
    sum(i.current_quantity * i.unit_price) as total_value
  from public.items i
  where i.pantry_id = p_pantry_id
    and i.barcode = p_barcode
    and i.is_consumed = false
  group by i.name, i.unit;
end;
$$ language plpgsql;

-- get all active items in a pantry grouped by product
-- note: groups by unit, so items with mixed units appear as separate rows.
-- the edge function layer handles cross-unit aggregation for the ui.
create or replace function public.get_pantry_inventory(p_pantry_id uuid)
returns table (
  product_name text,
  barcode text,
  total_quantity decimal,
  unit text,
  batch_count bigint,
  oldest_expiry date,
  total_value decimal,
  category_name text
) as $$
begin
  return query
  select
    i.name as product_name,
    i.barcode,
    sum(i.current_quantity) as total_quantity,
    i.unit,
    count(*) as batch_count,
    min(i.expiry_date) as oldest_expiry,
    sum(i.current_quantity * coalesce(i.unit_price, 0)) as total_value,
    c.name as category_name
  from public.items i
  left join public.categories c on i.category_id = c.id
  where i.pantry_id = p_pantry_id
    and i.is_consumed = false
  group by i.name, i.barcode, i.unit, c.name
  order by min(i.expiry_date) asc nulls last;
end;
$$ language plpgsql;
