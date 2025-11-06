-- Drop the trigger (safe)
DROP TRIGGER IF EXISTS trg_log_barista_changes ON cafe_users;

-- Drop the function with CASCADE
DROP FUNCTION IF EXISTS log_barista_changes() CASCADE;
