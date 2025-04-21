# PostgreSQL Setup Guide for Supply Chain Management Project

This guide outlines the one-time setup process for configuring PostgreSQL for the Supply Chain Management project.

---

## 1. Install and Start PostgreSQL

Run the following commands to install and start the PostgreSQL service:

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl enable postgresql
sudo systemctl start postgresql
sudo -i -u postgres
psql
```

---

## 2. Create Database and User

Access the PostgreSQL shell and execute the following SQL commands:

```sql
-- Drop the database and user if they already exist
DROP DATABASE IF EXISTS supply_chain_mgmt;
DROP ROLE IF EXISTS supply_chain_user;

-- Create a new user with a password
CREATE USER supply_chain_user WITH PASSWORD 'password';

-- Create a new database and assign ownership to the new user
CREATE DATABASE supply_chain_mgmt OWNER supply_chain_user;

-- Connect to the newly created database
\c supply_chain_mgmt

-- Grant usage and create privileges on the public schema
GRANT USAGE, CREATE ON SCHEMA public TO supply_chain_user;

-- Grant privileges on all existing tables, sequences, and functions in the public schema
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO supply_chain_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO supply_chain_user;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO supply_chain_user;

-- Grant privileges on all future tables, sequences, and functions created in the public schema
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO supply_chain_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO supply_chain_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON FUNCTIONS TO supply_chain_user;

-- Exit the PostgreSQL session
\q
```

> ⚠️ **Note**: This is a one-time setup. You do not need to repeat these steps every time you start the server. However, if you encounter any issues related to database privileges (e.g., when adding new tables or columns), revisiting these steps can help resolve those issues.

---

## 3. Insert Initial Admin and Test Users

Once the server is running for the first time, the user table will be empty. To manually insert the initial admin and test users, execute the following SQL command:

```sql
INSERT INTO users (id, available, enabled, name, password, username)
VALUES
  (2, 'f', 't', 'Seller', '$2a$10$Ga7IqcNf6u5e5lKtimfHEegQl8aq71eP8qUbiISkQSwjGN9Y7DxXe', 'seller@test.dev'),
  (4, 'f', 't', 'User', '$2a$10$VEi/bJ1Gg95YW2RJBaSJROB5H/qW0OLtUN/YgpSfIE/5c8iJmqJhu', 'user@test.dev'),
  (1, 't', 't', 'admin', '$2a$10$DLryrvF.EAtSm7FZGweTPOZifCe6oGdWm9xYBzmhNimj/OmZJLPGq', 'admin@test.dev'),
  (3, 't', 't', 'Agent', '$2a$10$lp71h10R9Tfc7vEWWDDJ8.FSRbEP3kxbhIOyrlkO99aMvGP/bizKe', 'agent@test.dev');

-- Insert roles into the user_roles join table
-- Assuming the user IDs generated are 1 (admin), 2 (seller), 3 (agent), 4 (user)
-- Adjust IDs if your sequence generates different values
INSERT INTO user_roles (user_id, role)
VALUES
  (1, 'ROLE_ADMIN'), -- Assign ROLE_ADMIN to admin user (ID 1)
  (1, 'ROLE_USER'),  -- Admin is also a USER
  (2, 'ROLE_SELLER'), -- Assign ROLE_SELLER to seller user (ID 2)
  (2, 'ROLE_USER'),  -- Seller is also a USER
  (3, 'ROLE_DELIVERY_AGENT'), -- Assign ROLE_DELIVERY_AGENT to agent user (ID 3)
  (3, 'ROLE_USER'),  -- Agent is also a USER
  (4, 'ROLE_USER');  -- Assign ROLE_USER to user user (ID 4)
```

Note: password for all the users(seller, admin, agent, user) is `password` only

This step ensures that you have an admin user and some test users available for initial login and testing.