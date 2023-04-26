-- A Database for a Football Club
-- Introduction to Databases project
--

BEGIN;

CREATE TABLE Team
(
    teamID         INT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    city           VARCHAR(255),
    kitColors      VARCHAR(255),
    foundationYear INT
);

CREATE TABLE League
(
    leagueID  INT PRIMARY KEY,
    name      VARCHAR(255),
    country   VARCHAR(255),
    endDate   DATE,
    numTeams  INT,
    startDate DATE
);

CREATE TABLE Sponsor
(
    sponsorID      INT PRIMARY KEY,
    name           VARCHAR(255),
    industry       VARCHAR(255),
    foundationYear INT
);

CREATE TABLE Person
(
    personID    INT PRIMARY KEY,
    firstName   VARCHAR(255),
    middleName  VARCHAR(255),
    lastName    VARCHAR(255),
    dob         DATE,
    nationality VARCHAR(255)
);

CREATE TABLE Player
(
    personID    INT PRIMARY KEY REFERENCES Person (personID),
    startingXI  BOOLEAN,
    appearances INT
);

CREATE TABLE CoachingStaff
(
    personID INT PRIMARY KEY REFERENCES Person (personID),
    teamID   INT REFERENCES Team (teamID),
    role     VARCHAR(255)
);

CREATE TABLE Manager
(
    personID          INT PRIMARY KEY REFERENCES CoachingStaff (personID),
    teamID            INT REFERENCES Team (teamID),
    yearsOfExperience INT
);

CREATE TABLE Captain
(
    personID     INT PRIMARY KEY REFERENCES Player (personID),
    captainSince DATE,
    seniority    INT
);

CREATE TABLE Position
(
    positionID INT PRIMARY KEY,
    type       VARCHAR(255)
);
CREATE TABLE StateOfContract
(
    personID  INT REFERENCES Person (personID),
    teamID    INT REFERENCES Team (teamID),
    startDate DATE,
    endDate   DATE,
    salary    FLOAT,
    PRIMARY KEY (personID, startDate)
);

CREATE TABLE StateOfPlaysFor
(
    personID     INT REFERENCES Player (personID),
    teamID       INT REFERENCES Team (teamID),
    startDate    DATE,
    jerseyNumber INT,
    PRIMARY KEY (personID, startDate)
);

CREATE TABLE StateOfManage
(
    personID  INT REFERENCES Manager (personID),
    teamID    INT REFERENCES Team (teamID),
    startDate DATE,
    PRIMARY KEY (personID, startDate)
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
    personID  INT REFERENCES Manager (personID),
    startDate DATE,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfManage (personID, startDate)
);

CREATE TABLE HasStateP
(
    personID  INT REFERENCES Player (personID),
    startDate DATE,
    PRIMARY KEY (personID, startDate),
    FOREIGN KEY (personID, startDate) REFERENCES StateOfPlaysFor (personID, startDate)
);

CREATE TABLE Plays
(
    personID   INT REFERENCES Player (personID),
    positionID INT REFERENCES Position (positionID),
    PRIMARY KEY (personID, positionID)
);

CREATE TABLE Trains
(
    personID INT REFERENCES CoachingStaff (personID),
    playerID INT REFERENCES Player (personID),
    PRIMARY KEY (personID, playerID)
);

CREATE TABLE L_Sponsorship
(
    sponsorID INT REFERENCES Sponsor (sponsorID),
    leagueID  INT REFERENCES League (leagueID),
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(255),
    PRIMARY KEY (sponsorID, leagueID, startDate)
);

CREATE TABLE T_Sponsorship
(
    sponsorID INT REFERENCES Sponsor (sponsorID),
    teamID    INT REFERENCES Team (teamID),
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(255),
    PRIMARY KEY (sponsorID, teamID, startDate)
);

CREATE TABLE P_Sponsorship
(
    sponsorID INT REFERENCES Sponsor (sponsorID),
    personID  INT REFERENCES Player (personID),
    startDate DATE,
    endDate   DATE,
    type      VARCHAR(255),
    PRIMARY KEY (sponsorID, personID, startDate)
);
