

-- Check if a person holds multiple roles concurrently
CREATE OR REPLACE FUNCTION check_person_role() RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*)
        FROM (SELECT personID FROM Manager
              UNION ALL
              SELECT personID FROM CoachingStaff
              UNION ALL
              SELECT playerID FROM Player) AS Roles
        WHERE personID = NEW.personID) > 1 THEN
        RAISE EXCEPTION 'A person cannot hold more than one role concurrently';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_person_role
    BEFORE INSERT OR UPDATE ON Person
    FOR EACH ROW
EXECUTE FUNCTION check_person_role();

CREATE OR REPLACE FUNCTION check_player_team_overlap() RETURNS TRIGGER AS $$
DECLARE
    overlap_count INT;
BEGIN
    SELECT COUNT(*)
    INTO overlap_count
    FROM Contract
    WHERE personID = NEW.personID
      AND teamID <> NEW.teamID
      AND ((startDate BETWEEN NEW.startDate AND NEW.endDate) OR (endDate BETWEEN NEW.startDate AND NEW.endDate));

    IF overlap_count > 0 THEN
        RAISE EXCEPTION 'A player cannot be part of two different teams simultaneously.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER PlayerTeamOverlap
    BEFORE INSERT OR UPDATE ON Contract
    FOR EACH ROW EXECUTE PROCEDURE check_player_team_overlap();


CREATE OR REPLACE FUNCTION check_player_contract() RETURNS TRIGGER AS $$
DECLARE
    overlap_count INT;
BEGIN
    SELECT COUNT(*)
    INTO overlap_count
    FROM Contract
    WHERE personID = NEW.personID
      AND ((startDate BETWEEN NEW.startDate AND NEW.endDate) OR (endDate BETWEEN NEW.startDate AND NEW.endDate));

    IF overlap_count > 0 THEN
        RAISE EXCEPTION 'A player cannot have overlapping contracts.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER PlayerContractOverlap
    BEFORE INSERT OR UPDATE ON Contract
    FOR EACH ROW EXECUTE PROCEDURE check_player_contract();


CREATE OR REPLACE FUNCTION check_captain_tenure() RETURNS TRIGGER AS $$
DECLARE
    contract_start_date DATE;
BEGIN
    SELECT startDate
    INTO contract_start_date
    FROM Contract
    WHERE personID = NEW.playerID
      AND NEW.startDate >= startDate
      AND NEW.startDate <= endDate;

    IF contract_start_date IS NULL OR NEW.startDate < contract_start_date THEN
        RAISE EXCEPTION 'A Captain’s tenure cannot start in the future or earlier than the player’s contract start date with the team.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER CaptainTenureCheck
    BEFORE INSERT OR UPDATE ON CaptainHistory
    FOR EACH ROW EXECUTE PROCEDURE check_captain_tenure();

CREATE OR REPLACE FUNCTION check_captain_tenure() RETURNS TRIGGER AS $$
DECLARE
    contract_start_date DATE;
BEGIN
    SELECT startDate
    INTO contract_start_date
    FROM Contract
    WHERE personID = NEW.playerID
      AND NEW.startDate >= startDate
      AND NEW.startDate <= endDate;

    IF contract_start_date IS NULL OR NEW.startDate < contract_start_date THEN
        RAISE EXCEPTION 'A Captain’s tenure cannot start in the future or earlier than the player’s contract start date with the team.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER CaptainTenureCheck
    BEFORE INSERT OR UPDATE ON CaptainHistory
    FOR EACH ROW EXECUTE PROCEDURE check_captain_tenure();


CREATE OR REPLACE FUNCTION check_contract_overlap() RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM Contract
        WHERE personID = NEW.personID
          AND ((NEW.startDate BETWEEN startDate AND endDate)
            OR (NEW.endDate BETWEEN startDate AND endDate))) > 0 THEN
        RAISE EXCEPTION 'Contract dates conflict with existing contract.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER contract_overlap_trigger
    BEFORE INSERT OR UPDATE ON Contract
    FOR EACH ROW EXECUTE PROCEDURE check_contract_overlap();

CREATE OR REPLACE FUNCTION check_role_conflict()
    RETURNS TRIGGER AS $$
BEGIN
    IF (NEW.position IS NOT NULL AND NEW.jerseyNumber IS NOT NULL) THEN
        -- New contract is for a player, check for coaching contract conflicts
        IF EXISTS (SELECT 1 FROM Contract WHERE personID = NEW.personID AND position IS NULL AND jerseyNumber IS NULL
                                            AND ((NEW.startDate <= endDate AND NEW.endDate >= startDate) OR (NEW.endDate >= startDate AND NEW.startDate <= endDate))) THEN
            RAISE EXCEPTION 'Person cannot hold player and coaching staff roles concurrently with overlapping contract dates.';
        END IF;
    ELSE
        -- New contract is for a coaching staff, check for player contract conflicts
        IF EXISTS (SELECT 1 FROM Contract WHERE personID = NEW.personID AND position IS NOT NULL AND jerseyNumber IS NOT NULL
                                            AND ((NEW.startDate <= endDate AND NEW.endDate >= startDate) OR (NEW.endDate >= startDate AND NEW.startDate <= endDate))) THEN
            RAISE EXCEPTION 'Person cannot hold player and coaching staff roles concurrently with overlapping contract dates.';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_role_conflict_trigger
    BEFORE INSERT ON Contract
    FOR EACH ROW EXECUTE PROCEDURE check_role_conflict();

