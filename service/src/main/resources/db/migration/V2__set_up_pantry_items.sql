-- V2__set_up_pantry_items.sql

-- Create pantry_items table
CREATE TABLE IF NOT EXISTS pantry_items (
    id SERIAL PRIMARY KEY,
    grocery_item_id BIGINT,
    quantity_in_stock DOUBLE PRECISION NOT NULL,
    expiration_date DATE,
    location_in_pantry VARCHAR(255),
    FOREIGN KEY (grocery_item_id) REFERENCES grocery_items(id)
);

-- Delete all records from pantry_items table
DELETE FROM pantry_items;
