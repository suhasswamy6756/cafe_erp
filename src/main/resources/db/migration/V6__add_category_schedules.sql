--------------------------------------------------------
-- ✅ ALTER categories table (ONLY if needed)
-- (Here we DON'T alter anything unless you request)
--------------------------------------------------------

-- Example: If you want to add a field later:
-- ALTER TABLE categories ADD COLUMN schedule_group_id BIGINT;

--------------------------------------------------------
-- ✅ Create category_schedules table
--------------------------------------------------------

CREATE TABLE IF NOT EXISTS category_schedules (
    schedule_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    handle VARCHAR(100) UNIQUE,
    description TEXT,

    is_everyday BOOLEAN DEFAULT TRUE,
    day_of_week INT[],

    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    is_active BOOLEAN DEFAULT TRUE,

    -- ✅ Audit logs
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

--------------------------------------------------------
-- ✅ Create category_schedule_associations table
--------------------------------------------------------

CREATE TABLE IF NOT EXISTS category_schedule_associations (
    assoc_id BIGSERIAL PRIMARY KEY,

    category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    schedule_id BIGINT NOT NULL REFERENCES category_schedules(schedule_id) ON DELETE CASCADE,

    -- ✅ Audit logs
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,

    CONSTRAINT unique_category_schedule UNIQUE (category_id, schedule_id)
);

--------------------------------------------------------
-- ✅ Create Indexes
--------------------------------------------------------

CREATE INDEX IF NOT EXISTS idx_category_schedule_category_id
    ON category_schedule_associations (category_id);

CREATE INDEX IF NOT EXISTS idx_category_schedule_schedule_id
    ON category_schedule_associations (schedule_id);

--------------------------------------------------------
-- ✅ Add timestamp trigger
--------------------------------------------------------

CREATE OR REPLACE TRIGGER trg_category_schedules_set_timestamp
    BEFORE INSERT OR UPDATE ON category_schedules
    FOR EACH ROW EXECUTE FUNCTION set_timestamp();

CREATE OR REPLACE TRIGGER trg_category_schedule_assoc_set_timestamp
    BEFORE INSERT OR UPDATE ON category_schedule_associations
    FOR EACH ROW EXECUTE FUNCTION set_timestamp();
