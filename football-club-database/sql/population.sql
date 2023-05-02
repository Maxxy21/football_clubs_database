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