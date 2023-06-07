-- Check for player's number of appearances: cannot be negative
CREATE OR REPLACE FUNCTION check_appearances() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.appearances < 0 THEN
        RAISE EXCEPTION 'A player’s number of appearances cannot be negative.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER PlayerAppearances
    BEFORE INSERT OR UPDATE
    ON Player
    FOR EACH ROW
EXECUTE PROCEDURE check_appearances();

-- Check for player's JerseyNumber attribute range: 1-99
CREATE OR REPLACE FUNCTION check_jersey_number() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.jerseyNumber < 1 OR NEW.jerseyNumber > 99 THEN
        RAISE EXCEPTION 'A Player’s JerseyNumber attribute should be within a predefined range (1-99).';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER JerseyNumberRange
    BEFORE INSERT OR UPDATE
    ON Player
    FOR EACH ROW
EXECUTE PROCEDURE check_jersey_number();

-- Check for sponsor's duplicate sponsorship: cannot sponsor the same team twice at the same time
CREATE OR REPLACE FUNCTION check_team_sponsorship() RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*)
        FROM T_Sponsorship
        WHERE sponsorID = NEW.sponsorID
          AND ((NEW.startDate BETWEEN startDate AND endDate) OR (NEW.endDate BETWEEN startDate AND endDate))) > 0 THEN
        RAISE EXCEPTION 'A Sponsor cannot sponsor the same Team twice at the same time.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER SponsorshipOverlap
    BEFORE INSERT OR UPDATE
    ON T_Sponsorship
    FOR EACH ROW
EXECUTE PROCEDURE check_team_sponsorship();

-- Check for sponsor's duplicate sponsorship: cannot sponsor the same player twice at the same time
CREATE OR REPLACE FUNCTION check_player_sponsorships() RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*)
        FROM P_Sponsorship
        WHERE sponsorID = NEW.sponsorID
          AND ((NEW.startDate BETWEEN startDate AND endDate) OR (NEW.endDate BETWEEN startDate AND endDate))) > 0 THEN
        RAISE EXCEPTION 'A Sponsor cannot sponsor the same Player twice at the same time.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER PlayerSponsorshipOverlap
    BEFORE INSERT OR UPDATE
    ON P_Sponsorship
    FOR EACH ROW
EXECUTE PROCEDURE check_player_sponsorships();


-- Check for unique TypeOfKit for each team in a given season: cannot have two kits of the same type
CREATE OR REPLACE FUNCTION check_kit_type_season() RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*)
        FROM KitColors
        WHERE teamID = NEW.teamID AND typeOfKit = NEW.typeOfKit AND season = NEW.season) > 0 THEN
        RAISE EXCEPTION 'A Team cannot have two kits of the same type in a given season.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS KitType ON KitColors;

CREATE TRIGGER KitTypeSeason
    BEFORE INSERT OR UPDATE
    ON KitColors
    FOR EACH ROW
EXECUTE PROCEDURE check_kit_type_season();

-- Check if a person holds multiple roles concurrently
CREATE OR REPLACE FUNCTION check_person_role() RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*)
        FROM (SELECT personID
              FROM Manager
              UNION ALL
              SELECT personID
              FROM CoachingStaff
              UNION ALL
              SELECT playerID
              FROM Player) AS Roles
        WHERE personID = NEW.personID) > 1 THEN
        RAISE EXCEPTION 'A person cannot hold more than one role concurrently';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_person_role
    BEFORE INSERT OR UPDATE
    ON Person
    FOR EACH ROW
EXECUTE FUNCTION check_person_role();

CREATE OR REPLACE FUNCTION check_player_team_overlap() RETURNS TRIGGER AS
$$
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
    BEFORE INSERT OR UPDATE
    ON Contract
    FOR EACH ROW
EXECUTE PROCEDURE check_player_team_overlap();

CREATE OR REPLACE FUNCTION check_contract_overlap() RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*)
        FROM PlayerContract
        WHERE personID = NEW.personID
          AND ((NEW.startDate BETWEEN startDate AND endDate)
            OR (NEW.endDate BETWEEN startDate AND endDate))) > 0 THEN
        RAISE EXCEPTION 'Contract dates conflict with existing contract.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER contract_overlap_trigger
    BEFORE INSERT OR UPDATE
    ON PlayerContract
    FOR EACH ROW
EXECUTE PROCEDURE check_contract_overlap();


CREATE OR REPLACE FUNCTION check_captain_tenure() RETURNS TRIGGER AS
$$
DECLARE
    contract_start_date DATE;
BEGIN
    SELECT startDate
    INTO contract_start_date
    FROM PlayerContract
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
    BEFORE INSERT OR UPDATE
    ON CaptainHistory
    FOR EACH ROW
EXECUTE PROCEDURE check_captain_tenure();


CREATE OR REPLACE FUNCTION check_player_team_overlap() RETURNS TRIGGER AS
$$
DECLARE
    overlap_count INT;
BEGIN
    SELECT COUNT(*)
    INTO overlap_count
    FROM PlayerContract
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
    BEFORE INSERT OR UPDATE
    ON PlayerContract
    FOR EACH ROW
EXECUTE PROCEDURE check_player_team_overlap();

CREATE TRIGGER contract_overlap_trigger
    BEFORE INSERT OR UPDATE
    ON Contract
    FOR EACH ROW
EXECUTE PROCEDURE check_contract_overlap();

CREATE OR REPLACE FUNCTION check_role_conflict()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (NEW.position IS NOT NULL AND NEW.jerseyNumber IS NOT NULL) THEN
        -- New contract is for a player, check for coaching contract conflicts
        IF EXISTS (SELECT 1
                   FROM CoachingStaffContract
                   WHERE personID = NEW.personID
                     AND ((NEW.startDate <= endDate AND NEW.endDate >= startDate) OR
                          (NEW.endDate >= startDate AND NEW.startDate <= endDate))) THEN
            RAISE EXCEPTION 'Person cannot hold player and coaching staff roles concurrently with overlapping contract dates.';
        END IF;
    ELSE
        -- New contract is for a coaching staff, check for player contract conflicts
        IF EXISTS (SELECT 1
                   FROM PlayerContract
                   WHERE personID = NEW.personID
                     AND position IS NOT NULL
                     AND jerseyNumber IS NOT NULL
                     AND ((NEW.startDate <= endDate AND NEW.endDate >= startDate) OR
                          (NEW.endDate >= startDate AND NEW.startDate <= endDate))) THEN
            RAISE EXCEPTION 'Person cannot hold player and coaching staff roles concurrently with overlapping contract dates.';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_role_conflict_trigger_player
    BEFORE INSERT
    ON PlayerContract
    FOR EACH ROW
EXECUTE PROCEDURE check_role_conflict();

CREATE TRIGGER check_role_conflict_trigger_coaching
    BEFORE INSERT
    ON CoachingStaffContract
    FOR EACH ROW
EXECUTE PROCEDURE check_role_conflict();

