-- Introduction to Databases project
-- A Database for Football Clubs
-- Author: Maxwell Aboagye

BEGIN;
CREATE TABLE Team
(
    teamID         INT PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    city           VARCHAR(50) NOT NULL,
    foundationYear SMALLINT NOT NULL
);

CREATE TABLE Sponsor
(
    sponsorID      INT PRIMARY KEY NOT NULL,
    name           VARCHAR(50) UNIQUE NOT NULL,
    industry       VARCHAR(50) NOT NULL,
    foundationYear SMALLINT NOT NULL
);

CREATE TABLE Person
(
    personID    INT PRIMARY KEY,
    firstName   VARCHAR(50) NOT NULL,
    middleName  VARCHAR(50),
    lastName    VARCHAR(50) NOT NULL,
    dob         DATE NOT NULL,
    nationality VARCHAR(50) NOT NULL
);

CREATE TABLE Player
(
    playerID    INT PRIMARY KEY,
    startingXI  BOOLEAN NOT NULL,
    appearances INT NOT NULL,
    CONSTRAINT appearancesCheck CHECK (appearances >= 0),
    CONSTRAINT playerInPerson FOREIGN KEY (playerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE CoachingStaff
(
    coachingStaffID INT PRIMARY KEY NOT NULL,
    role            VARCHAR(50) NOT NULL,
    yearsOfExperience INT NOT NULL,
    CONSTRAINT yearsOfExperienceCheck CHECK (yearsOfExperience >= 0),
    CONSTRAINT coachingStaffInPerson FOREIGN KEY (coachingStaffID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);



CREATE TABLE CoachingStaffContract
(
    contractID      SERIAL PRIMARY KEY,
    coachingStaffID INT NOT NULL,
    teamID          INT NOT NULL,
    startDate       DATE NOT NULL,
    endDate         DATE ,
    salary          DECIMAL(10, 2) NOT NULL,
    CONSTRAINT endDateCheck CHECK (endDate > startDate),
    CONSTRAINT personInCoachingStaffContract FOREIGN KEY (coachingStaffID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT teamInCoachingStaffContract FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);


CREATE TABLE PlayerContract
(
    contractID   SERIAL PRIMARY KEY,
    playerID     INT NOT NULL,
    teamID       INT NOT NULL,
    startDate    DATE NOT NULL,
    endDate      DATE ,
    salary       DECIMAL(10, 2),
    jerseyNumber INT NOT NULL,
    position     VARCHAR(50) NOT NULL,
    CONSTRAINT jerseyNumberCheck CHECK (jerseyNumber BETWEEN 1 AND 99),
    CONSTRAINT endDateCheck CHECK (endDate > startDate),
    CONSTRAINT personInPlayerContract FOREIGN KEY (playerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT teamInPlayerContract FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE CaptainHistory
(
    playerID  INT NOT NULL,
    teamID    INT NOT NULL,
    startDate DATE NOT NULL,
    endDate   DATE NOT NULL,
    CONSTRAINT playerInCaptainHistory FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT teamInCaptainHistory FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE T_Sponsorship
(
    tSponsorshipID INT  NOT NULL,
    sponsorID      INT  NOT NULL,
    teamID         INT  NOT NULL,
    startDate      DATE NOT NULL,
    endDate        DATE NOT NULL,
    type           VARCHAR(50) NOT NULL,
    PRIMARY KEY (tSponsorshipID, sponsorID),
    CONSTRAINT teamInTSponsorship FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT sponsorInTSponsorship FOREIGN KEY (sponsorID)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE P_Sponsorship
(
    pSponsorshipID INT  NOT NULL,
    sponsorID      INT  NOT NULL,
    playerID       INT  NOT NULL,
    startDate      DATE NOT NULL,
    endDate        DATE NOT NULL,
    type           VARCHAR(50) NOT NULL,
    PRIMARY KEY (pSponsorshipID, sponsorID),
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

CREATE TABLE KitColors
(
    kitColorID INT PRIMARY KEY NOT NULL,
    color      VARCHAR(50) NOT NULL,
    typeOfKit  VARCHAR(50) NOT NULL,
    teamID     INT NOT NULL,
    season     VARCHAR(9)  NOT NULL,
    CONSTRAINT typeOfKitCheck CHECK (typeOfKit IN ('Home Kit', 'Away Kit', 'Alternate Kit')),
    CONSTRAINT teamInKitColors FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT PK_KitColors UNIQUE (teamID, typeOfKit, season)
);

CREATE TABLE Trains
(
    coachingStaffID INT NOT NULL,
    playerID        INT NOT NULL,
    PRIMARY KEY (coachingStaffID, playerID),
    CONSTRAINT coachingStaffInTrains FOREIGN KEY (coachingStaffID)
        REFERENCES CoachingStaff (coachingStaffID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT playerInTrains FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED

);

COMMIT;