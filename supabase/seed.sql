-- seed.sql
-- purpose: seed local development database with test data
-- scope: auth users, households, pantries, and sample items
-- notes: only runs locally via `supabase db reset`, never affects production
--         uses fixed uuids so they can be referenced across tables

-- ============================================================================
-- test users
-- ============================================================================
-- credentials:
--   owner:  test@example.com  / password123
--   member: member@example.com / password123

insert into auth.users (
  id,
  instance_id,
  aud,
  role,
  email,
  encrypted_password,
  email_confirmed_at,
  raw_app_meta_data,
  raw_user_meta_data,
  created_at,
  updated_at,
  confirmation_token,
  recovery_token,
  email_change,
  email_change_token_new,
  email_change_token_current,
  reauthentication_token,
  is_sso_user,
  is_anonymous
) values (
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
  '00000000-0000-0000-0000-000000000000',
  'authenticated',
  'authenticated',
  'test@example.com',
  crypt('password123', gen_salt('bf')),
  now(),
  '{"provider":"email","providers":["email"]}',
  '{"name":"Test Owner"}',
  now(),
  now(),
  '', '', '', '', '', '',
  false,
  false
), (
  'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22',
  '00000000-0000-0000-0000-000000000000',
  'authenticated',
  'authenticated',
  'member@example.com',
  crypt('password123', gen_salt('bf')),
  now(),
  '{"provider":"email","providers":["email"]}',
  '{"name":"Test Member"}',
  now(),
  now(),
  '', '', '', '', '', '',
  false,
  false
);

-- identities (required for email/password sign-in to work)
insert into auth.identities (
  id,
  user_id,
  provider_id,
  identity_data,
  provider,
  last_sign_in_at,
  created_at,
  updated_at
) values (
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
  jsonb_build_object('sub', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'email', 'test@example.com'),
  'email',
  now(),
  now(),
  now()
), (
  'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22',
  'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22',
  'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22',
  jsonb_build_object('sub', 'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'email', 'member@example.com'),
  'email',
  now(),
  now(),
  now()
);

-- ============================================================================
-- household
-- ============================================================================

insert into public.households (id, name, created_by, invite_code) values
  ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'Test Family', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'TEST01');

-- ============================================================================
-- household members
-- ============================================================================

insert into public.household_members (household_id, user_id, role) values
  ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'owner'),
  ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'member');

-- ============================================================================
-- pantries
-- ============================================================================

insert into public.pantries (id, household_id, name, location) values
  ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'Kitchen Fridge', 'Kitchen'),
  ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a55', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'Kitchen Pantry', 'Kitchen');

-- ============================================================================
-- items (batch-based inventory)
-- ============================================================================
-- look up category ids from seeded categories (migration 08)

-- milk - 2 batches with different expiry dates (for testing fifo consumption)
insert into public.items (
  pantry_id, category_id, name, barcode,
  initial_quantity, current_quantity, unit,
  purchase_date, expiry_date, unit_price, currency, added_by
) values (
  'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
  (select id from public.categories where name = 'Dairy'),
  'Milk', '4800000000001',
  2.0, 2.0, 'L',
  '2026-02-01', '2026-02-15', 3.50, 'SGD',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
), (
  'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
  (select id from public.categories where name = 'Dairy'),
  'Milk', '4800000000001',
  1.0, 1.0, 'L',
  '2026-02-05', '2026-02-20', 4.00, 'SGD',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
);

-- eggs - 1 batch
insert into public.items (
  pantry_id, category_id, name, barcode,
  initial_quantity, current_quantity, unit,
  purchase_date, expiry_date, unit_price, currency, added_by
) values (
  'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
  (select id from public.categories where name = 'Dairy'),
  'Eggs', '4800000000002',
  1.0, 1.0, 'dozen',
  '2026-02-03', '2026-02-17', 5.00, 'SGD',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
);

-- butter - 1 batch
insert into public.items (
  pantry_id, category_id, name, barcode,
  initial_quantity, current_quantity, unit,
  purchase_date, expiry_date, unit_price, currency, added_by
) values (
  'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
  (select id from public.categories where name = 'Dairy'),
  'Butter', '4800000000003',
  250.0, 250.0, 'g',
  '2026-02-02', '2026-03-15', 6.50, 'SGD',
  'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22'
);

-- rice - 1 batch in kitchen pantry
insert into public.items (
  pantry_id, category_id, name, barcode,
  initial_quantity, current_quantity, unit,
  purchase_date, expiry_date, unit_price, currency, added_by
) values (
  'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a55',
  (select id from public.categories where name = 'Grains'),
  'Rice', '4800000000004',
  5.0, 5.0, 'kg',
  '2026-01-20', '2027-01-20', 8.00, 'SGD',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
);

-- canned tuna - 2 batches in kitchen pantry (different prices)
insert into public.items (
  pantry_id, category_id, name, barcode,
  initial_quantity, current_quantity, unit,
  purchase_date, expiry_date, unit_price, currency, added_by
) values (
  'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a55',
  (select id from public.categories where name = 'Canned'),
  'Canned Tuna', '4800000000005',
  3.0, 3.0, 'piece',
  '2026-01-15', '2027-06-15', 2.50, 'SGD',
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
), (
  'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a55',
  (select id from public.categories where name = 'Canned'),
  'Canned Tuna', '4800000000005',
  2.0, 2.0, 'piece',
  '2026-02-01', '2027-08-01', 3.00, 'SGD',
  'b1ffbc99-9c0b-4ef8-bb6d-6bb9bd380a22'
);
