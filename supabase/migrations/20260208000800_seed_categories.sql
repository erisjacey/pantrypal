-- migration: seed_categories
-- description: seeds default food categories as shared reference data
-- note: uses on conflict to make this migration idempotent (safe to re-run)
-- affected tables: public.categories

insert into public.categories (name, icon, color) values
  ('Dairy', '🥛', '#FFE4B5'),
  ('Vegetables', '🥬', '#90EE90'),
  ('Fruits', '🍎', '#FFB6C1'),
  ('Meat', '🥩', '#F08080'),
  ('Seafood', '🐟', '#87CEEB'),
  ('Grains', '🌾', '#F5DEB3'),
  ('Condiments', '🧂', '#FFDAB9'),
  ('Beverages', '🥤', '#87CEEB'),
  ('Snacks', '🍿', '#FFD700'),
  ('Frozen', '❄️', '#B0E0E6'),
  ('Bakery', '🍞', '#DEB887'),
  ('Canned', '🥫', '#CD853F'),
  ('Other', '📦', '#D3D3D3')
on conflict (name) do nothing;
