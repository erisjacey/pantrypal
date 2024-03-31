-- V1__set_up_grocery_items.sql

-- Create grocery_items table
CREATE TABLE IF NOT EXISTS grocery_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    unit VARCHAR(255)
);

-- Delete all records from grocery_items table
DELETE FROM grocery_items;
