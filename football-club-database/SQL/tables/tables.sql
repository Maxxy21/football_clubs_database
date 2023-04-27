-- A Database for a Football Club
-- Introduction to Databases project
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
    name      VARCHAR(50) NOT NULL,
    country   VARCHAR(50) NOT NULL,
    startDate DATE,
    endDate   DATE,
    numTeams  INT
);

CREATE TABLE Sponsor
(
    sponsorID      INT PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    industry       VARCHAR(50),
    foundationYear DATE
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
    FOREIGN KEY (playerID) REFERENCES Person (personID)
);

CREATE TABLE CoachingStaff
(
    coachingStaffID INT PRIMARY KEY,
    role            VARCHAR(50),
    teamID          INT,
    FOREIGN KEY (coachingStaffID) REFERENCES Person (personID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

CREATE TABLE Manager
(
    managerID         INT PRIMARY KEY,
    yearsOfExperience INT,
    FOREIGN KEY (managerID) REFERENCES Person (personID)
);

CREATE TABLE Captain
(
    captainID    INT PRIMARY KEY,
    captainSince DATE NOT NULL,
    seniority    INT,
    FOREIGN KEY (captainID) REFERENCES Player (playerID)
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
    endDate   DATE,
    salary    DECIMAL(10, 2),
    PRIMARY KEY (personID, teamID, startDate),
    FOREIGN KEY (personID) REFERENCES Person (personID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

CREATE TABLE StateOfPlaysFor
(
    personID     INT,
    startDate    DATE,
    jerseyNumber INT,
    teamID       INT,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID) REFERENCES Person (personID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

CREATE TABLE StateOfManage
(
    personID  INT,
    startDate DATE,
    teamID    INT,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID) REFERENCES Person (personID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

CREATE TABLE ContractWith
(
    teamID    INT REFERENCES Team (teamID),
    personID  INT REFERENCES Person (personID),
    startDate DATE,
    PRIMARY KEY (teamID, personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfContract (personID, startDate)
);

CREATE TABLE HasStateM
(
    personID  INT REFERENCES Manager (managerID),
    startDate DATE,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfManage (personID, startDate)
);

CREATE TABLE HasStateP
(
    personID  INT REFERENCES Player (playerID),
    startDate DATE,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfPlaysFor (personID, startDate)
);

CREATE TABLE Plays
(
    playerID   INT,
    positionID INT,
    PRIMARY KEY (playerID, positionID),
    FOREIGN KEY (playerID) REFERENCES Player (playerID),
    FOREIGN KEY (positionID) REFERENCES Position (positionID)
);

CREATE TABLE Trains
(
    personID INT REFERENCES CoachingStaff (coachingStaffID),
    playerID INT REFERENCES Player (playerID),
    PRIMARY KEY (personID, playerID)
);

CREATE TABLE L_Sponsorship
(
    sponsorID INT REFERENCES Sponsor (sponsorID),
    leagueID  INT REFERENCES League (leagueID),
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(50),
    PRIMARY KEY (sponsorID, leagueID, startDate)
);

CREATE TABLE T_Sponsorship
(
    sponsorID INT REFERENCES Sponsor (sponsorID),
    teamID    INT REFERENCES Team (teamID),
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(50),
    PRIMARY KEY (sponsorID, teamID, startDate)
);

CREATE TABLE P_Sponsorship
(
    sponsorID INT REFERENCES Sponsor (sponsorID),
    personID  INT REFERENCES Player (playerID),
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(50),
    PRIMARY KEY (sponsorID, personID, startDate)
);

COMMIT;
