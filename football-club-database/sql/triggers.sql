-- Check max positions for a player
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

-- Check contract end date
CREATE OR REPLACE FUNCTION check_contract_end_date() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.endDate < CURRENT_DATE THEN
        RAISE EXCEPTION 'The end date of a contract should not be in the past';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_contract_end_date
    BEFORE INSERT OR UPDATE ON StateOfContract
    FOR EACH ROW
EXECUTE FUNCTION check_contract_end_date();

-- Check captain since date
CREATE OR REPLACE FUNCTION check_captain_since() RETURNS TRIGGER AS $$
DECLARE
    min_contract_start DATE;
BEGIN
    SELECT MIN(startDate) INTO min_contract_start
    FROM StateOfContract
    WHERE personID = NEW.captainID AND teamID = NEW.teamID;

    IF NEW.captainSince < min_contract_start THEN
        RAISE EXCEPTION 'A captain’s "captainSince" date cannot be earlier than the player’s contract start date with the team';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_captain_since
    BEFORE INSERT OR UPDATE ON Captain
    FOR EACH ROW
EXECUTE FUNCTION check_captain_since();

-- Check if a person holds multiple roles concurrently
CREATE OR REPLACE FUNCTION check_person_role() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Manager M, CoachingStaff CS, Player P
        WHERE NEW.personID = M.managerID
          AND NEW.personID = CS.coachingStaffID
          AND NEW.personID = P.playerID) THEN
        RAISE EXCEPTION 'A person cannot hold the roles of manager, coaching staff member, and player concurrently';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_person_role
    BEFORE INSERT OR UPDATE ON Manager
    FOR EACH ROW
EXECUTE FUNCTION check_person_role();

CREATE TRIGGER trg_check_person_role_cs
    BEFORE INSERT OR UPDATE ON CoachingStaff
    FOR EACH ROW
EXECUTE FUNCTION check_person_role();

CREATE TRIGGER trg_check_person_role_p
    BEFORE INSERT OR UPDATE ON Player
    FOR EACH ROW
EXECUTE FUNCTION check_person_role();
