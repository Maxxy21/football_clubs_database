    -----------------------------------------------------
    -- Triggers for inclusion constraints.
    -----------------------------------------------------

    -- Trigger for inclusion constraint on Team's kit Color: a team must have at least one kit color
    CREATE OR REPLACE FUNCTION teamKitColorsInclusionCheck() RETURNS TRIGGER AS
    $$
    BEGIN
        IF (select COUNT(*)
            FROM Team
            WHERE teamid NOT IN (SELECT teamID FROM KitColors)) > 0 THEN
            RAISE EXCEPTION 'Inclusion error on %, a Team must have at least one kit Color', OLD;
        END IF;

        RETURN OLD;
    END;
    $$
        LANGUAGE plpgsql;

    CREATE TRIGGER teamKitColorsInclusionCheck
        AFTER DELETE OR UPDATE
        ON KitColors
        FOR EACH ROW
    EXECUTE PROCEDURE teamKitColorsInclusionCheck();

    -- Trigger for inclusion constraint on Train for Player: a team must have at least one trainer
    CREATE OR REPLACE FUNCTION trainerForPlayerInclusionCheck() RETURNS TRIGGER AS
    $$
    BEGIN
        IF (select COUNT(*)
            FROM Player
            WHERE playerID NOT IN (SELECT playerID FROM Trains)) > 0 THEN
            RAISE EXCEPTION 'Inclusion error on %, Player must have Trainers', OLD;
        END IF;

        RETURN OLD;
    END;
    $$
        LANGUAGE plpgsql;

    CREATE TRIGGER trainerForPlayerInclusionCheck
        AFTER DELETE OR UPDATE
        ON Trains
        FOR EACH ROW
    EXECUTE PROCEDURE trainerForPlayerInclusionCheck();


    -- Trigger for inclusion constraint on Train of CoachingStaff: a team must have at least one trainer
    CREATE OR REPLACE FUNCTION coachingStaffTrainerInclusionCheck() RETURNS TRIGGER AS
    $$
    BEGIN
        IF (select COUNT(*)
            FROM Coachingstaff
            WHERE coachingstaffID NOT IN (SELECT coachingstaffID FROM Trains)) > 0 THEN
            RAISE EXCEPTION 'Inclusion error on %, Coaching Staff should Train a Player', OLD;
        END IF;

        RETURN OLD;
    END;
    $$
        LANGUAGE plpgsql;

    CREATE TRIGGER coachingStaffTrainerInclusionCheck
        AFTER DELETE OR UPDATE
        ON Trains
        FOR EACH ROW
    EXECUTE PROCEDURE coachingStaffTrainerInclusionCheck();


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

    CREATE TRIGGER jerseyNumberRange
        BEFORE INSERT OR UPDATE
        ON PlayerContract
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

    CREATE TRIGGER teamSponsorshipOverlap
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

    CREATE TRIGGER playerSponsorshipOverlap
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
            WHERE teamID = NEW.teamID
              AND typeOfKit = NEW.typeOfKit
              AND season = NEW.season) > 0 THEN
            RAISE EXCEPTION 'A Team cannot have two kits of the same type in a given season.';
        END IF;
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

    CREATE TRIGGER kitTypeSeason
        BEFORE INSERT OR UPDATE
        ON KitColors
        FOR EACH ROW
    EXECUTE PROCEDURE check_kit_type_season();


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
    EXECUTE PROCEDURE check_contract_overlap();

    -- Check if a player is currently part of a team for them to be a captain
    CREATE OR REPLACE FUNCTION check_captain_tenure() RETURNS TRIGGER AS
    $$
    DECLARE
        contract_start_date DATE;
    BEGIN
        SELECT startDate
        INTO contract_start_date
        FROM PlayerContract
        WHERE playerID = NEW.playerID
          AND NEW.startDate >= startDate
          AND NEW.startDate <= endDate;

        IF contract_start_date IS NULL OR NEW.startDate < contract_start_date THEN
            RAISE EXCEPTION 'A Captain’s tenure cannot start in the future or earlier than the player’s contract start date with the team.';
        END IF;
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

    CREATE TRIGGER captainTenureCheck
        BEFORE INSERT OR UPDATE
        ON CaptainHistory
        FOR EACH ROW
    EXECUTE PROCEDURE check_captain_tenure();

    --Check if a player is part of two different teams simultaneously
    CREATE OR REPLACE FUNCTION check_player_team_overlap() RETURNS TRIGGER AS
    $$
    DECLARE
        overlap_count INT;
    BEGIN
        SELECT COUNT(*)
        INTO overlap_count
        FROM PlayerContract
        WHERE playerID= NEW.playerID
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
    EXECUTE PROCEDURE check_player_team_overlap();


    CREATE OR REPLACE FUNCTION check_person_role_on_player_contract()
        RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM CoachingStaffContract
            WHERE coachingStaffID = NEW.playerID AND
                ((NEW.startdate BETWEEN startdate AND enddate) OR
                 (NEW.enddate BETWEEN startdate AND enddate))
        ) THEN
            RAISE EXCEPTION 'A player cannot be a coaching staff at the same time';
        END IF;
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

    CREATE TRIGGER check_person_role_trigger_on_player_contract
        BEFORE INSERT OR UPDATE ON PlayerContract
        FOR EACH ROW EXECUTE PROCEDURE check_person_role_on_player_contract();



    --
    CREATE OR REPLACE FUNCTION check_person_role_on_coaching_staff_contract()
        RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM PlayerContract
            WHERE playerID = NEW.coachingStaffID AND
                ((NEW.startdate BETWEEN startdate AND enddate) OR
                 (NEW.enddate BETWEEN startdate AND enddate))
        ) THEN
            RAISE EXCEPTION 'A coaching staff cannot be a player at the same time';
        END IF;
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

    CREATE TRIGGER check_person_role_trigger_on_coaching_staff_contract
        BEFORE INSERT OR UPDATE ON CoachingStaffContract
        FOR EACH ROW EXECUTE PROCEDURE check_person_role_on_coaching_staff_contract();

