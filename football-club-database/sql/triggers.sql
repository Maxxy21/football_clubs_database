-----------------------------------------------------
-- Triggers for inclusion constraints.
-----------------------------------------------------

-- Trigger to ensure each team has at least one kit color
CREATE OR REPLACE FUNCTION teamKitColorsInclusionCheck() RETURNS TRIGGER AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM HasKitColor WHERE teamID = NEW.teamID) THEN
        RAISE EXCEPTION 'Inclusion error on %, a Team must have at least one kit Color', NEW.teamID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE CONSTRAINT TRIGGER teamKitColorsInclusionCheck
    AFTER INSERT OR UPDATE
    ON Team
    DEFERRABLE INITIALLY DEFERRED
    FOR EACH ROW
EXECUTE FUNCTION teamKitColorsInclusionCheck();

CREATE OR REPLACE FUNCTION deleteKitColorCheck() RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM HasKitColor WHERE color = OLD.color) THEN
        RAISE EXCEPTION 'Deleting this row would violate inclusion constraint on KitColor and HasKitColor, this color is still used by a team';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER deleteKitColorCheck
    BEFORE DELETE
    ON KitColors
    FOR EACH ROW
EXECUTE FUNCTION deleteKitColorCheck();

-- Trigger for inclusion constraint on `HasPSponsorship`
CREATE OR REPLACE FUNCTION pSponsorshipInsertCheck() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.psponsorshipid NOT IN (SELECT psponsorshipid FROM P_Sponsorship) THEN
        RAISE EXCEPTION 'Inclusion error on %, a Sponsorship must be present in PSponsorship', NEW.psponsorshipid;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER pSponsorshipInsertTrigger
    BEFORE INSERT
    ON HasPSponsorship
    FOR EACH ROW
EXECUTE FUNCTION pSponsorshipInsertCheck();

CREATE OR REPLACE FUNCTION pSponsorshipUpdateCheck() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.psponsorshipid NOT IN (SELECT psponsorshipid FROM P_Sponsorship) THEN
        RAISE EXCEPTION 'Inclusion error on %, a Sponsorship must be present in PSponsorship', NEW.psponsorshipid;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER pSponsorshipUpdateTrigger
    BEFORE UPDATE
    ON HasPSponsorship
    FOR EACH ROW
EXECUTE FUNCTION pSponsorshipUpdateCheck();


-- Trigger for inclusion constraint on `HasTSponsorship`
CREATE OR REPLACE FUNCTION tSponsorshipInsertCheck() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.tsponsorshipid NOT IN (SELECT tsponsorshipid FROM T_Sponsorship) THEN
        RAISE EXCEPTION 'Inclusion error on %, a Sponsorship must be present in TSponsorship', NEW.tsponsorshipid;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tSponsorshipInsertTrigger
    BEFORE INSERT
    ON HasTSponsorship
    FOR EACH ROW
EXECUTE FUNCTION tSponsorshipInsertCheck();

CREATE OR REPLACE FUNCTION tSponsorshipUpdateCheck() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.tsponsorshipid NOT IN (SELECT tsponsorshipid FROM T_Sponsorship) THEN
        RAISE EXCEPTION 'Inclusion error on %, a Sponsorship must be present in TSponsorship', NEW.tsponsorshipid;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tSponsorshipUpdateTrigger
    BEFORE UPDATE
    ON HasTSponsorship
    FOR EACH ROW
EXECUTE FUNCTION tSponsorshipUpdateCheck();


-----------------------------------------------------
-- Triggers for external constraints.
-----------------------------------------------------

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
EXECUTE FUNCTION check_appearances();

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

CREATE TRIGGER jerseyNumberRange
    BEFORE INSERT OR UPDATE
    ON PlayerContract
    FOR EACH ROW
EXECUTE FUNCTION check_jersey_number();


-- Check contract overlap: a player cannot have two contracts at the same time
CREATE OR REPLACE FUNCTION check_contract_overlap() RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*)
        FROM PlayerContract
        WHERE playerID = NEW.playerID
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
EXECUTE FUNCTION check_contract_overlap();

-- Check if a player is currently part of a team for them to be a captain
CREATE OR REPLACE FUNCTION check_captain_tenure() RETURNS TRIGGER AS
$$
DECLARE
    contract_start_date DATE;
    contract_end_date   DATE;
BEGIN
    SELECT startDate, endDate
    INTO contract_start_date, contract_end_date
    FROM PlayerContract
    WHERE playerID = NEW.playerID
      AND NEW.startDate >= startDate
      AND NEW.endDate <= endDate;

    IF contract_start_date IS NULL OR NEW.startDate < contract_start_date OR NEW.endDate > contract_end_date THEN
        RAISE EXCEPTION 'A Captain’s tenure cannot start in the future or earlier than the player’s contract start date with the team, and must end before or at the same time as the contract.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER captainTenureCheck
    BEFORE INSERT OR UPDATE
    ON CaptainHistory
    FOR EACH ROW
EXECUTE FUNCTION check_captain_tenure();

--Check if a player is part of two different teams simultaneously
CREATE OR REPLACE FUNCTION check_player_team_overlap() RETURNS TRIGGER AS
$$
DECLARE
    overlap_count INT;
BEGIN
    SELECT COUNT(*)
    INTO overlap_count
    FROM PlayerContract
    WHERE playerID = NEW.playerID
      AND teamID <> NEW.teamID
      AND ((startDate BETWEEN NEW.startDate AND NEW.endDate) OR (endDate BETWEEN NEW.startDate AND NEW.endDate));

    IF overlap_count > 0 THEN
        RAISE EXCEPTION 'A player cannot be part of two different teams simultaneously.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER playerTeamOverlap
    BEFORE INSERT OR UPDATE
    ON PlayerContract
    FOR EACH ROW
EXECUTE FUNCTION check_player_team_overlap();


CREATE OR REPLACE FUNCTION check_person_role_on_player_contract()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS (SELECT 1
               FROM CoachingStaffContract
               WHERE coachingStaffID = NEW.playerID
                 AND ((NEW.startdate BETWEEN startdate AND enddate) OR
                      (NEW.enddate BETWEEN startdate AND enddate))) THEN
        RAISE EXCEPTION 'A player cannot be a coaching staff at the same time';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_person_role_trigger_on_player_contract
    BEFORE INSERT OR UPDATE
    ON PlayerContract
    FOR EACH ROW
EXECUTE FUNCTION check_person_role_on_player_contract();


CREATE OR REPLACE FUNCTION check_person_role_on_coaching_staff_contract()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS (SELECT 1
               FROM PlayerContract
               WHERE playerID = NEW.coachingStaffID
                 AND ((NEW.startdate BETWEEN startdate AND enddate) OR
                      (NEW.enddate BETWEEN startdate AND enddate))) THEN
        RAISE EXCEPTION 'A coaching staff cannot be a player at the same time';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_person_role_trigger_on_coaching_staff_contract
    BEFORE INSERT OR UPDATE
    ON CoachingStaffContract
    FOR EACH ROW
EXECUTE FUNCTION check_person_role_on_coaching_staff_contract();


