-- Introduction to Databases project
-- A Database for Football Clubs
-- Author: Maxwell Aboagye

BEGIN;
CREATE TABLE Team
(
    teamID         INT PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    city           VARCHAR(50) NOT NULL,
    kitColors      VARCHAR(50),
    foundationYear SMALLINT
);

CREATE TABLE Sponsor
(
    sponsorID      INT PRIMARY KEY,
    name           VARCHAR(50) UNIQUE NOT NULL,
    industry       VARCHAR(50),
    foundationYear SMALLINT
);

CREATE TABLE Person
(
    personID    INT PRIMARY KEY,
    firstName   VARCHAR(50) NOT NULL,
    middleName  VARCHAR(50),
    lastName    VARCHAR(50),
    dob         DATE,
    nationality VARCHAR(50)
);

CREATE TABLE Player
(
    playerID    INT PRIMARY KEY,
    startingXI  BOOLEAN,
    appearances INT,
    CONSTRAINT appearancesCheck CHECK (appearances >= 0),
    CONSTRAINT playerInPerson FOREIGN KEY (playerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE CoachingStaff
(
    coachingStaffID INT PRIMARY KEY,
    role            VARCHAR(50),
    CONSTRAINT coachingStaffInPerson FOREIGN KEY (coachingStaffID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Manager
(
    managerID         INT PRIMARY KEY,
    yearsOfExperience INT,
    CONSTRAINT managerInCoachingStaff FOREIGN KEY (managerID)
        REFERENCES CoachingStaff (coachingStaffID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Contract
(
    contractID   INT PRIMARY KEY,
    personID     INT,
    teamID       INT,
    startDate    DATE NOT NULL,
    endDate      DATE NOT NULL,
    salary       DECIMAL(10, 2),
    jerseyNumber INT  NULL,
    position     VARCHAR(50),
    CONSTRAINT jerseyNumberCheck CHECK (jerseyNumber BETWEEN 1 AND 99),
    CONSTRAINT endDateCheck CHECK (endDate > startDate),
    CONSTRAINT personInContract FOREIGN KEY (personID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT teamInContract FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE CaptainHistory
(
    captainHistoryID INT PRIMARY KEY,
    playerID         INT,
    startDate        DATE NOT NULL,
    endDate          DATE NOT NULL,
    CONSTRAINT playerInCaptainHistory FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE T_Sponsorship
(
    tSponsorshipID INT PRIMARY KEY,
    teamID         INT,
    sponsorID      INT,
    startDate      DATE NOT NULL,
    endDate        DATE NOT NULL,
    type           VARCHAR(50),
    CONSTRAINT teamInTSponsorship FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT sponsorInTSponsorship FOREIGN KEY (sponsorID)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE P_Sponsorship
(
    pSponsorshipID INT PRIMARY KEY,
    playerID       INT,
    sponsorID      INT,
    startDate      DATE NOT NULL,
    endDate        DATE NOT NULL,
    type           VARCHAR(50),
    CONSTRAINT playerInPSponsorship FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT sponsorInPSponsorship FOREIGN KEY (sponsorID)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Trains
(
    coachingStaffID INT,
    playerID        INT,
    PRIMARY KEY (coachingStaffID, playerID),
    CONSTRAINT coachingStaffInTrains FOREIGN KEY (coachingStaffID)
        REFERENCES CoachingStaff (coachingStaffID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT playerInTrains FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE ON
            DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

COMMIT;