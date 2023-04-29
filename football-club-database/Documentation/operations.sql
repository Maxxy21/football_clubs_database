CREATE OR REPLACE FUNCTION check_max_positions()
    RETURNS TRIGGER AS $$
DECLARE
    position_count INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO position_count
    FROM Plays
    WHERE playerID = NEW.playerID;

    IF position_count >= 11 THEN
        RAISE EXCEPTION 'A player cannot play more than 10 positions';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_max_positions
    BEFORE INSERT ON Plays
    FOR EACH ROW
EXECUTE FUNCTION check_max_positions();