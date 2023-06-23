CREATE TABLE Team
(
    teamID         INT PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    city           VARCHAR(50) NOT NULL,
    foundationYear SMALLINT    NOT NULL
);

CREATE TABLE KitColors
(
    color VARCHAR(50) NOT NULL PRIMARY KEY
);


CREATE TABLE HasKitColor
(
    teamID INT         NOT NULL,
    color  VARCHAR(50) NOT NULL,
    PRIMARY KEY (teamID, color),
    CONSTRAINT teamHasKitColor FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT kitColorHasKitColor FOREIGN KEY (color)
        REFERENCES KitColors (color)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE Person
(
    personID    INT PRIMARY KEY,
    firstName   VARCHAR(50) NOT NULL,
    middleName  VARCHAR(50),
    lastName    VARCHAR(50) NOT NULL,
    dob         DATE        NOT NULL,
    nationality VARCHAR(50) NOT NULL
);

CREATE TABLE Player
(
    playerID    INT PRIMARY KEY,
    startingXI  BOOLEAN NOT NULL,
    appearances INT     NOT NULL,
    CONSTRAINT appearancesCheck CHECK (appearances >= 0),
    CONSTRAINT playerInPerson FOREIGN KEY (playerID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE CoachingStaff
(
    coachingStaffID   INT PRIMARY KEY NOT NULL,
    role              VARCHAR(50)     NOT NULL,
    yearsOfExperience INT             NOT NULL,
    CONSTRAINT yearsOfExperienceCheck CHECK (yearsOfExperience >= 0),
    CONSTRAINT coachingStaffInPerson FOREIGN KEY (coachingStaffID)
        REFERENCES Person (personID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
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

CREATE TABLE CoachingStaffContract
(
    contractID      SERIAL PRIMARY KEY,
    coachingStaffID INT            NOT NULL,
    teamID          INT            NOT NULL,
    startDate       DATE           NOT NULL,
    endDate         DATE          NOT NULL,
    salary          DECIMAL(10, 2) NOT NULL,
    CONSTRAINT coachingStaffContractUQ UNIQUE (coachingStaffID, teamID, startDate),
    CONSTRAINT endDateCheck CHECK (endDate > startDate),
    CONSTRAINT staffInContract FOREIGN KEY (coachingStaffID)
        REFERENCES CoachingStaff (coachingStaffID)
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
    playerID     INT         NOT NULL,
    teamID       INT         NOT NULL,
    startDate    DATE        NOT NULL,
    endDate      DATE       NOT NULL,
    salary       DECIMAL(10, 2),
    jerseyNumber INT         NOT NULL,
    position     VARCHAR(50) NOT NULL,
    CONSTRAINT playerContractUQ UNIQUE (playerID, teamID, startDate),
    CONSTRAINT jerseyNumberCheck CHECK (jerseyNumber BETWEEN 1 AND 99),
    CONSTRAINT endDateCheck CHECK (endDate > startDate),
    CONSTRAINT playerInContract FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
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
    playerID  INT  NOT NULL,
    teamID    INT  NOT NULL,
    startDate DATE NOT NULL,
    endDate   DATE NOT NULL,
    CONSTRAINT CaptainHistory_PK PRIMARY KEY (playerID, teamID, startDate),
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

CREATE TABLE Sponsor
(
    sponsorID      INT PRIMARY KEY    NOT NULL,
    name           VARCHAR(50) UNIQUE NOT NULL,
    industry       VARCHAR(50)        NOT NULL,
    foundationYear SMALLINT           NOT NULL
);


CREATE TABLE T_Sponsorship
(
    tSponsorshipID INT         NOT NULL PRIMARY KEY,
    sponsorID      INT         NOT NULL,
    startDate      DATE        NOT NULL,
    endDate        DATE        NOT NULL,
    type           VARCHAR(50) NOT NULL,
    CONSTRAINT sponsorInTSponsorship FOREIGN KEY (sponsorID)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE HasTSponsorship
(
    teamID         INT NOT NULL,
    tSponsorshipID INT NOT NULL,
    sponsor        INT NOT NULL,
    PRIMARY KEY (teamID, tSponsorshipID),
    CONSTRAINT teamHasTSponsorship FOREIGN KEY (teamID)
        REFERENCES Team (teamID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT tSponsorshipHasTSponsorship FOREIGN KEY (tSponsorshipID)
        REFERENCES T_Sponsorship (tSponsorshipID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT sponsorHasTSponsorship FOREIGN KEY (sponsor)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE P_Sponsorship
(
    pSponsorshipID INT         NOT NULL PRIMARY KEY,
    sponsorID      INT         NOT NULL,
    startDate      DATE        NOT NULL,
    endDate        DATE        NOT NULL,
    type           VARCHAR(50) NOT NULL,
    CONSTRAINT sponsorInPSponsorship FOREIGN KEY (sponsorID)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);


CREATE TABLE HasPSponsorship
(
    playerID       INT NOT NULL,
    pSponsorshipID INT NOT NULL,
    sponsor        INT NOT NULL,
    PRIMARY KEY (playerID, pSponsorshipID),
    CONSTRAINT playerHasPSponsorship FOREIGN KEY (playerID)
        REFERENCES Player (playerID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT pSponsorshipHasPSponsorship FOREIGN KEY (pSponsorshipID)
        REFERENCES P_Sponsorship (pSponsorshipID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT sponsorHasPSponsorship FOREIGN KEY (sponsor)
        REFERENCES Sponsor (sponsorID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


