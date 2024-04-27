-- V1__set_up_products.sql

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

-- Delete all records from products table
DELETE FROM products;
