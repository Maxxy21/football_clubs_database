-- Introduction to Databases project
-- A Database for a Football Club
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


CREATE TABLE League
(
    leagueID  INT PRIMARY KEY,
    name      VARCHAR(50) UNIQUE NOT NULL,
    country   VARCHAR(50)        NOT NULL,
    startDate DATE,
    endDate   DATE,
    numTeams  INT
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
    appearances INT CHECK (appearances >= 0),
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
    teamID          INT NOT NULL,
    CONSTRAINT coachingStaffInPerson FOREIGN KEY (coachingStaffID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT coachingStaffInTeam FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Manager
(
    managerID         INT PRIMARY KEY,
    yearsOfExperience INT,
    CONSTRAINT managerInPerson FOREIGN KEY (managerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Captain
(
    captainID    INT PRIMARY KEY,
    captainSince DATE NOT NULL CHECK (captainSince <= CURRENT_DATE),
    seniority    INT,
    teamID       INT  NOT NULL,
    CONSTRAINT captainInPlayer FOREIGN KEY (captainID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT captainInTeam FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Position
(
    positionID INT PRIMARY KEY,
    type       VARCHAR(50) NOT NULL
);

CREATE TABLE StateOfContract
(
    personID  INT,
    teamID    INT,
    startDate DATE,
    endDate   DATE CHECK (endDate > startDate),
    salary    DECIMAL(10, 2),
    PRIMARY KEY (personID, teamID, startDate),
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

CREATE TABLE StateOfPlaysFor
(
    playerID     INT,
    startDate    DATE,
    jerseyNumber INT CHECK (jerseyNumber BETWEEN 1 AND 99),
    teamID       INT,
    PRIMARY KEY (playerID, startDate),
    CONSTRAINT personInPlaysFor FOREIGN KEY (playerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT teamInPlaysFor FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE StateOfManage
(
    managerID INT,
    startDate DATE,
    teamID    INT,
    PRIMARY KEY (managerID, startDate),
    CONSTRAINT managerInStateOfManage FOREIGN KEY (managerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT teamInStateOfManage FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

/**
    * =================================================================== --
    * Relationship Tables--
    * ================================================================
    */
CREATE TABLE HasStateM
(
    managerID INT,
    startDate DATE,
    PRIMARY KEY (managerID, startDate),
    CONSTRAINT managerInHasStateM FOREIGN KEY (managerID, startDate)
        REFERENCES StateOfManage (managerID, startDate)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE HasStateP
(
    playerID  INT,
    startDate DATE,
    PRIMARY KEY (playerID, startDate),
    CONSTRAINT playerInHasStateP FOREIGN KEY (playerID, startDate)
        REFERENCES StateOfPlaysFor (playerID, startDate)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE HasStateC
(
    personID  INT REFERENCES Person (personID),
    startDate DATE,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfContract (personID, startDate)
);

CREATE TABLE ContractWith
(
    teamID    INT REFERENCES Team (teamID),
    personID  INT,
    startDate DATE,
    PRIMARY KEY (teamID, personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfContract (personID, startDate, startDate),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

CREATE TABLE Plays
(
    playerID   INT,
    positionID INT,
    PRIMARY KEY (playerID, positionID),
    FOREIGN KEY (playerID) REFERENCES Player (playerID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (positionID) REFERENCES Position (positionID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Trains
(
    personID INT,
    playerID INT,
    PRIMARY KEY (personID, playerID),
    FOREIGN KEY (personID) REFERENCES CoachingStaff (coachingStaffID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (playerID) REFERENCES Player (playerID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE L_Sponsorship
(
    sponsorID INT,
    leagueID  INT,
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(50),
    PRIMARY KEY (sponsorID, leagueID, startDate),
    FOREIGN KEY (sponsorID) REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (leagueID) REFERENCES League (leagueID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE T_Sponsorship
(
    sponsorID INT,
    teamID    INT,
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(50),
    PRIMARY KEY (sponsorID, teamID, startDate),
    FOREIGN KEY (sponsorID) REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE P_Sponsorship
(
    sponsorID INT,
    personID  INT,
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(50),
    PRIMARY KEY (sponsorID, personID, startDate),
    FOREIGN KEY (sponsorID) REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (personID) REFERENCES Player (playerID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE ParticipatesIn
(
    teamID   INT,
    leagueID INT,
    season   VARCHAR(50) NOT NULL,
    PRIMARY KEY (teamID, leagueID, season),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (leagueID) REFERENCES League (leagueID)
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE UNIQUE INDEX idx_unique_manager ON StateOfManage (teamID, startDate);
CREATE UNIQUE INDEX idx_unique_captain ON Captain (captainID, captainSince);


COMMIT;
