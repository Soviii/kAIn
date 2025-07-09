-- Step 1: Connect to the new database
\c mydb

-- Step 2: Create the tables
-- Users table
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS Users (
        id SERIAL PRIMARY KEY,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        email VARCHAR(100) NOT NULL,
        pw VARCHAR(250) NOT NULL -- TODO: update to use hashing for passwords (temporarily using this method for basic dev and testing)
    );
    RAISE NOTICE '✅ Created or confirmed table: Recipes';
END
$$;

-- Recipes table
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS Recipes (
        id SERIAL PRIMARY KEY,
        user_id INTEGER NOT NULL,
        title VARCHAR(255) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
        updated_at TIMESTAMP NOT NULL DEFAULT NOW()
    );
    RAISE NOTICE '✅ Created or confirmed table: Recipes';
END
$$;

-- Ingredients table
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS Ingredients (
        id SERIAL PRIMARY KEY,
        recipe_id INTEGER REFERENCES Recipes(id) ON DELETE CASCADE,
        name VARCHAR(100) NOT NULL,
        quantity NUMERIC NOT NULL,
        unit VARCHAR(50)
    );
    RAISE NOTICE '✅ Created or confirmed table: Ingredients';
END
$$;

-- Steps table
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS Steps (
        id SERIAL PRIMARY KEY,
        recipe_id INTEGER REFERENCES Recipes(id) ON DELETE CASCADE,
        step_number INTEGER NOT NULL,
        instruction TEXT NOT NULL
    );
    RAISE NOTICE '✅ Created or confirmed table: Steps';
END
$$;

-- Messages table
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS Messages (
        id SERIAL PRIMARY KEY,
        recipe_id INTEGER REFERENCES recipes(id) ON DELETE CASCADE,
        sender VARCHAR(20) NOT NULL,         -- 'user' or 'ai'
        message_text TEXT NOT NULL,
        timestamp TIMESTAMP NOT NULL DEFAULT NOW()
    );
    RAISE NOTICE '✅ Created or confirmed table: Messages';
END
$$;

-- Summaries table
DO $$
BEGIN
    CREATE TABLE IF NOT EXISTS Summaries (
        id SERIAL PRIMARY KEY,
        recipe_id INTEGER REFERENCES recipes(id) ON DELETE CASCADE,
        summary_text TEXT NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );
    RAISE NOTICE '✅ Created or confirmed table: Summaries';
END
$$;