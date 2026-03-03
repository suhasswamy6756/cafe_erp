-- ============================
--  MODIFIER GROUPS
-- ============================

CREATE TABLE IF NOT EXISTS public.modifier_groups
(
    modifier_group_id BIGSERIAL PRIMARY KEY,

    type VARCHAR(50),
    title VARCHAR(255) NOT NULL,
    short_name VARCHAR(150),
    handle VARCHAR(150) UNIQUE,
    sort_order INT DEFAULT 0,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Index for performance
CREATE INDEX IF NOT EXISTS idx_modifier_groups_handle
    ON public.modifier_groups(handle);

-- Timestamp trigger
CREATE OR REPLACE TRIGGER trg_modifier_groups_set_timestamp
    BEFORE INSERT OR UPDATE
    ON public.modifier_groups
    FOR EACH ROW
    EXECUTE FUNCTION public.set_timestamp();



-- ============================
--  MODIFIER OPTIONS
-- ============================

CREATE TABLE IF NOT EXISTS public.modifier_options
(
    option_id BIGSERIAL PRIMARY KEY,

    modifier_group_id BIGINT NOT NULL
        REFERENCES public.modifier_groups(modifier_group_id)
        ON DELETE CASCADE,

    name VARCHAR(255) NOT NULL,
    price_modifier NUMERIC(10,2) DEFAULT 0.00,
    sort_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Index
CREATE INDEX IF NOT EXISTS idx_modifier_options_group_id
    ON public.modifier_options(modifier_group_id);

-- Timestamp trigger
CREATE OR REPLACE TRIGGER trg_modifier_options_set_timestamp
    BEFORE INSERT OR UPDATE
    ON public.modifier_options
    FOR EACH ROW
    EXECUTE FUNCTION public.set_timestamp();
