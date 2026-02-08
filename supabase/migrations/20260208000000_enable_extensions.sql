-- migration: enable_extensions
-- description: enables required postgresql extensions for the project
-- affected: extensions (uuid-ossp)

-- enable uuid generation for primary keys across all tables
create extension if not exists "uuid-ossp";
