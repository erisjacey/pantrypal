-- V2__set_up_grocery_items.sql

-- Create grocery_items table
CREATE TABLE IF NOT EXISTS grocery_items (
    id SERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    unit VARCHAR(50) NOT NULL,
    purchased_date DATE,
    expiration_date DATE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Delete all records from grocery_items table
DELETE FROM grocery_items;
