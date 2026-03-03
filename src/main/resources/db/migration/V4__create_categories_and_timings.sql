-- ======================================================
--  CATEGORIES TABLE
-- ======================================================

CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    short_name VARCHAR(100),
    handle VARCHAR(150) UNIQUE,
    description TEXT,

    parent_category_id INT REFERENCES categories(id) ON DELETE SET NULL,

    kot_group VARCHAR(50),
    timing_group_id INT,
    sort_order INT DEFAULT 0,

    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);


-- ======================================================
--  CATEGORY TIMINGS TABLE
-- ======================================================

CREATE TABLE IF NOT EXISTS category_timings (
    id SERIAL PRIMARY KEY,
    category_id INT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,

    day_of_week VARCHAR(10) NOT NULL CHECK (
        day_of_week IN ('Mon','Tue','Wed','Thu','Fri','Sat','Sun')
    ),

    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);


-- ======================================================
--  TRIGGERS USING EXISTING FUNCTION: set_timestamp()
-- ======================================================

-- Trigger for categories
CREATE OR REPLACE TRIGGER trg_categories_set_timestamp
BEFORE INSERT OR UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION set_timestamp();

-- Trigger for category_timings
CREATE OR REPLACE TRIGGER trg_category_timings_set_timestamp
BEFORE INSERT OR UPDATE ON category_timings
FOR EACH ROW
EXECUTE FUNCTION set_timestamp();


-- ======================================================
--  INDEXES
-- ======================================================

CREATE INDEX IF NOT EXISTS idx_categories_handle
    ON categories (handle);

CREATE INDEX IF NOT EXISTS idx_category_timings_category_id
    ON category_timings (category_id);
