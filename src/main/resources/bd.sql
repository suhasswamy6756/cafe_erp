-- =====================================================
-- 1️⃣ CATEGORY TABLE
-- =====================================================
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- 2️⃣ ITEM TABLE
-- =====================================================
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    base_price DECIMAL(10,2) NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    category_id INT REFERENCES categories(id),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- 3️⃣ MODIFIER TABLE
-- =====================================================
CREATE TABLE modifiers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    additional_price DECIMAL(10,2) DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- 4️⃣ TAX TABLE
-- =====================================================
-- Step 4: Create tax table
CREATE TABLE IF NOT EXISTS public.tax
(
    id SERIAL PRIMARY KEY,
    tax_type VARCHAR(100) NOT NULL,
    tax_code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    applicable_modes VARCHAR(255), -- e.g., 'Online,Offline' or JSON
    applicable_on VARCHAR(100), -- e.g., 'Product,Service'
    tax_percentage DECIMAL(5,2) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- 5️⃣ CHARGE TABLE
-- =====================================================
CREATE TABLE charges (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    percentage BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- 6️⃣ DISCOUNT TABLE
-- =====================================================
CREATE TABLE discounts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    value DECIMAL(10,2) NOT NULL,
    percentage BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- 7️⃣ MAPPING TABLES
-- =====================================================

-- ITEM ↔ MODIFIER
CREATE TABLE item_modifiers (
    item_id INT REFERENCES items(id) ON DELETE CASCADE,
    modifier_id INT REFERENCES modifiers(id) ON DELETE CASCADE,
    PRIMARY KEY (item_id, modifier_id),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)
);

-- ITEM ↔ TAX
CREATE TABLE item_taxes (
    item_id INT REFERENCES items(id) ON DELETE CASCADE,
    tax_id INT REFERENCES taxes(id) ON DELETE CASCADE,
    PRIMARY KEY (item_id, tax_id),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)
);

-- ITEM ↔ CHARGE
CREATE TABLE item_charges (
    item_id INT REFERENCES items(id) ON DELETE CASCADE,
    charge_id INT REFERENCES charges(id) ON DELETE CASCADE,
    PRIMARY KEY (item_id, charge_id),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)
);

-- ITEM ↔ DISCOUNT
CREATE TABLE item_discounts (
    item_id INT REFERENCES items(id) ON DELETE CASCADE,
    discount_id INT REFERENCES discounts(id) ON DELETE CASCADE,
    PRIMARY KEY (item_id, discount_id),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)
);

-- =====================================================
-- 8️⃣ AUDIT LOG TABLE (Global for all CRUD tracking)
-- =====================================================
CREATE TABLE audit_logs (
    id SERIAL PRIMARY KEY,
    entity_name VARCHAR(100) NOT NULL,
    entity_id INT NOT NULL,
    action VARCHAR(10) NOT NULL,  -- INSERT / UPDATE / DELETE
    changed_by VARCHAR(100),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    old_data JSONB,
    new_data JSONB
);

-- =====================================================
-- 9️⃣ TRIGGER FUNCTION FOR AUDIT LOGGING
-- =====================================================
CREATE OR REPLACE FUNCTION log_audit()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO audit_logs(entity_name, entity_id, action, new_data)
        VALUES (TG_TABLE_NAME, NEW.id, TG_OP, row_to_json(NEW));
        RETURN NEW;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO audit_logs(entity_name, entity_id, action, old_data, new_data)
        VALUES (TG_TABLE_NAME, NEW.id, TG_OP, row_to_json(OLD), row_to_json(NEW));
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO audit_logs(entity_name, entity_id, action, old_data)
        VALUES (TG_TABLE_NAME, OLD.id, TG_OP, row_to_json(OLD));
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- =====================================================
-- 🔁 ADD TRIGGERS TO ALL MAIN TABLES
-- =====================================================
CREATE TRIGGER audit_category
AFTER INSERT OR UPDATE OR DELETE ON categories
FOR EACH ROW EXECUTE FUNCTION log_audit();

CREATE TRIGGER audit_item
AFTER INSERT OR UPDATE OR DELETE ON items
FOR EACH ROW EXECUTE FUNCTION log_audit();

CREATE TRIGGER audit_modifier
AFTER INSERT OR UPDATE OR DELETE ON modifiers
FOR EACH ROW EXECUTE FUNCTION log_audit();

CREATE TRIGGER audit_tax
AFTER INSERT OR UPDATE OR DELETE ON taxes
FOR EACH ROW EXECUTE FUNCTION log_audit();

CREATE TRIGGER audit_charge
AFTER INSERT OR UPDATE OR DELETE ON charges
FOR EACH ROW EXECUTE FUNCTION log_audit();

CREATE TRIGGER audit_discount
AFTER INSERT OR UPDATE OR DELETE ON discounts
FOR EACH ROW EXECUTE FUNCTION log_audit();
