-- V4__add_grocery_type_to_grocery_items.sql

-- Add grocery_type column to grocery_item table with a default value
ALTER TABLE grocery_items
ADD COLUMN grocery_type VARCHAR(255) DEFAULT 'OTHER';

-- Update existing rows to set grocery_type to 'OTHER'
UPDATE grocery_items
SET grocery_type = 'OTHER'
WHERE grocery_type IS NULL;
