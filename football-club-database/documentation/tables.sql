-- Introduction to Databases project
-- A Database for a Football Club
-- Author: Maxwell Aboagye


DROP TABLE IF EXISTS ParticipatesIn;
DROP TABLE IF EXISTS P_Sponsorship;
DROP TABLE IF EXISTS T_Sponsorship;
DROP TABLE IF EXISTS L_Sponsorship;
DROP TABLE IF EXISTS Trains;
DROP TABLE IF EXISTS Plays;
DROP TABLE IF EXISTS ContractWith;
DROP TABLE IF EXISTS HasStateC;
DROP TABLE IF EXISTS HasStateP;
DROP TABLE IF EXISTS HasStateM;
DROP TABLE IF EXISTS StateOfManage;
DROP TABLE IF EXISTS StateOfPlaysFor;
DROP TABLE IF EXISTS StateOfContract;
DROP TABLE IF EXISTS Position;
DROP TABLE IF EXISTS Captain;
DROP TABLE IF EXISTS Manager;
DROP TABLE IF EXISTS CoachingStaff;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Sponsor;
DROP TABLE IF EXISTS League;
DROP TABLE IF EXISTS Team;



BEGIN;

-- Tables creation

/**
* =================================================================== --
* Entities Tables--
* ================================================================
*/

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
    teamID          INT,
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
    teamID       INT,
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

-- The UNIQUE constraints remain the same
CREATE UNIQUE INDEX idx_unique_manager ON StateOfManage (teamID, startDate);
CREATE UNIQUE INDEX idx_unique_captain ON Captain (captainID, captainSince);

-- Populating Teams
INSERT INTO Team (teamID, name, city, kitColors, foundationYear)
VALUES (1, 'FC Barcelona', 'Barcelona', 'Blue, Red', 1899),
       (2, 'Real Madrid', 'Madrid', 'White', 1902),
       (3, 'Atletico Madrid', 'Madrid', 'Red, White', 1903);

-- Populating Leagues
INSERT INTO League (leagueID, name, country, startDate, endDate, numTeams)
VALUES (1, 'La Liga', 'Spain', '2023-08-13', '2024-05-26', 20);

-- Populating Sponsors
INSERT INTO Sponsor (sponsorID, name, industry, foundationYear)
VALUES (1, 'Nike', 'Sportswear', 1964),
       (2, 'Adidas', 'Sportswear', 1949);

-- Populating Persons
INSERT INTO Person (personID, firstName, middleName, lastName, dob, nationality)
VALUES (1, 'Lionel', '', 'Messi', '1987-06-24', 'Argentina'),
       (2, 'Cristiano', '', 'Ronaldo', '1985-02-05', 'Portugal'),
       (3, 'Gerard', '', 'Piqué', '1987-02-02', 'Spain'),
       (4, 'Sergio', '', 'Ramos', '1986-03-30', 'Spain');

-- Populating Players
INSERT INTO Player (playerID, startingXI, appearances)
VALUES (1, TRUE, 50),
       (2, TRUE, 45),
       (3, TRUE, 40),
       (4, TRUE, 35);

-- Populating CoachingStaff
INSERT INTO CoachingStaff (coachingStaffID, role, teamID)
VALUES (5, 'Assistant Coach', 1),
       (6, 'Goalkeeper Coach', 2);

-- Populating Managers
INSERT INTO Manager (managerID, yearsOfExperience)
VALUES (7, 15),
       (8, 12);

-- Populating Captains
INSERT INTO Captain (captainID, captainSince, seniority, teamID)
VALUES (3, '2018-08-15', 5, 1),
       (4, '2010-08-25', 9, 2);

-- Populating Positions
INSERT INTO Position (positionID, type)
VALUES (1, 'Forward'),
       (2, 'Midfielder'),
       (3, 'Defender'),
       (4, 'Goalkeeper');

-- Populating StateOfContract
INSERT INTO StateOfContract (personID, teamID, startDate, endDate, salary)
VALUES (1, 1, '2018-07-01', '2023-06-30', 40000000),
       (2, 2, '2018-07-01', '2023-06-30', 30000000),
       (3, 1, '2018-07-01', '2023-06-30', 20000000),
       (4, 2, '2018-07-01', '2023-06-30', 15000000);

-- Populating StateOfPlaysFor
INSERT INTO StateOfPlaysFor (playerID, startDate, jerseyNumber, teamID)
VALUES (1, '2018-07-01', 10, 1),
       (2, '2018-07-01', 7, 2),
       (3, '2018-07-01', 3, 1),
       (4, '2018-07-01', 4, 2);

-- Populating StateOfManage
INSERT INTO StateOfManage (managerID, startDate, teamID)
VALUES (7, '2018-07-01', 1),
       (8, '2018-07-01', 2);

-- Populating HasStateM
INSERT INTO HasStateM (managerID, startDate)
VALUES (7, '2018-07-01'),
       (8, '2018-07-01');

-- Populating HasStateP
INSERT INTO HasStateP (playerID, startDate)
VALUES (1, '2018-07-01'),
       (2, '2018-07-01'),
       (3, '2018-07-01'),
       (4, '2018-07-01');

-- Populating ContractWith
INSERT INTO ContractWith (teamID, personID, startDate)
VALUES (1, 1, '2018-07-01'),
       (2, 2, '2018-07-01'),
       (1, 3, '2018-07-01'),
       (2, 4, '2018-07-01');

-- Populating Plays
INSERT INTO Plays (playerID, positionID)
VALUES (1, 1),
       (2, 1),
       (3, 3),
       (4, 3);

-- Populating Trains
INSERT INTO Trains (personID, playerID)
VALUES (5, 1),
       (6, 2);

-- Populating L_Sponsorship
INSERT INTO L_Sponsorship (sponsorID, leagueID, startDate, endDate, type)
VALUES (1, 1, '2018-08-01', '2023-07-31', 'Title Sponsor');

-- Populating T_Sponsorship
INSERT INTO T_Sponsorship (sponsorID, teamID, startDate, endDate, type)
VALUES (1, 1, '2018-07-01', '2023-06-30', 'Kit Sponsor'),
       (2, 2, '2018-07-01', '2023-06-30', 'Kit Sponsor');

-- Populating P_Sponsorship
INSERT INTO P_Sponsorship (sponsorID, personID, startDate, endDate, type)
VALUES (1, 1, '2018-07-01', '2023-06-30', 'Boot Sponsor'),
       (2, 2, '2018-07-01', '2023-06-30', 'Boot Sponsor');

-- -- Populating ParticipatesIn
INSERT INTO ParticipatesIn (teamID, leagueID, season)
VALUES (1, 1, '2023-2024'),
       (2, 1, '2023-2024'),
       (3, 1, '2023-2024');


COMMIT;