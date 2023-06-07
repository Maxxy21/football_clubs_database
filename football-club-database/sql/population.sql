-- Sample Data

-- Insert Team
INSERT INTO Team(teamid, name, city, foundationyear)
VALUES (1, 'FC Barcelona', 'Barcelona', 1899),
       (2, 'Real Madrid', 'Madrid', 1902),
       (3, 'Paris Saint-Germain', 'Paris', 1970),
       (4, 'Bayern Munich', 'Munich', 1900),
       (5, 'Juventus', 'Turin', 1897),
       (6, 'Manchester United', 'Manchester', 1878),
       (7, 'Liverpool', 'Liverpool', 1892),
       (8, 'Chelsea', 'London', 1905),
       (9, 'Manchester City', 'Manchester', 1880),
       (10, 'Arsenal', 'London', 1886);

INSERT INTO Person(personid, firstname, middlename, lastname, dob, nationality)
VALUES (1, 'Lionel', 'Andres', 'Messi', '1987-06-24', 'Argentina'),
       (2, 'Cristiano', ' ', 'Ronaldo', '1985-02-05', 'Portugal'),
       (3, 'Neymar', ' ', 'Jr.', '1992-02-05', 'Brazil'),
       (4, 'Robert', ' ', 'Lewandowski', '1988-08-21', 'Poland'),
       (5, 'Zinedine', ' ', 'Zidane', '1972-06-23', 'France'),
       (6, 'Kylian', '', 'Mbappe', '1998-12-20', 'France'),
       (7, 'Mohamed', '', 'Salah', '1992-06-15', 'Egypt'),
       (8, 'Kevin', 'De', 'Bruyne', '1991-06-28', 'Belgium'),
       (9, 'Virgil', '', 'Van Dijk', '1991-07-08', 'Netherlands'),
       (10, 'Paul', '', 'Pogba', '1993-03-15', 'France'),
       (11, 'Jose', '', 'Mourinho', '1993-03-15', 'Portugal'),
       (12, 'Frank', '', 'Lampard', '1963-01-26', 'England'),
       (13, 'Philip', '', 'Lampard', '1963-01-26', 'England'),
       (14, 'Gigi', '', 'Buffon', '1963-01-26', 'Italy'),
       (15, 'Son', '', 'Mogi', '1963-01-26', 'Korea'),
       (16, 'Lil', '', 'Things', '1963-01-26', 'England'),
       (17, 'Frank', '', 'Lampard', '1963-01-26', 'England'),
       (18, 'Willock', '', 'Ulm', '1963-01-26', 'Sweden'),
       (19, 'His', '', 'John', '1963-01-26', 'Poland'),
       (20, 'Gabel', '', 'Wells', '1963-01-26', 'England');


-- Insert Sponsor
INSERT INTO Sponsor(sponsorid, name, industry, foundationyear)
VALUES (1, 'Nike', 'Sportswear', 1964),
       (2, 'Adidas', 'Sportswear', 1949),
       (3, 'Puma', 'Sportswear', 1948),
       (4, 'Under Armour', 'Sportswear', 1996),
       (5, 'New Balance', 'Sportswear', 1906),
       (6, 'Reebok', 'Sportswear', 1958),
       (7, 'Umbro', 'Sportswear', 1920),
       (8, 'Kappa', 'Sportswear', 1967),
       (9, 'Asics', 'Sportswear', 1949),
       (10, 'Joma', 'Sportswear', 1965);

-- Insert Player
INSERT INTO Player(playerid, startingxi, appearances)
VALUES (1, true, 700),
       (2, true, 800),
       (3, true, 600),
       (4, true, 650),
       (6, true, 150),
       (7, true, 160),
       (8, true, 140),
       (9, false, 130),
       (10, true, 120),
       (14, true, 400);

-- Insert CoachingStaff
INSERT INTO CoachingStaff(coachingstaffid, role)
VALUES (5, 'Manager'),
       (11, 'Manager'),
       (12, 'Goalkeeping Coach'),
       (13, 'Fitness Coach'),
       (15, 'Assistant Manager'),
       (16, 'Physiotherapist'),
       (17, 'Chief Analyst'),
       (18, 'Nutritionist'),
       (19, 'Kit Manager'),
       (20, 'Physiotherapist');


--
INSERT INTO CoachingStaffContract(coachingstaffid, teamid, startdate, enddate, salary)
VALUES (5, 1, '2020-01-01', '2022-12-31', 1000000),
       (11, 2, '2020-01-01', '2022-12-05', 1000000),
       (12, 3, '2020-01-01', '2022-12-30', 1000000),
       (13, 4, '2020-01-01', '2022-12-31', 1000000),
       (15, 5, '2020-01-01', '2022-12-31', 1000000),
       (16, 6, '2020-01-01', '2022-12-31', 1000000),
       (17, 7, '2020-01-01', '2022-12-31', 1000000),
       (18, 8, '2020-01-01', '2022-12-31', 1000000),
       (19, 9, '2020-01-01', '2022-12-31', 1000000),
       (20, 10, '2020-01-01', '2022-12-31', 1000000);

INSERT INTO PlayerContract (playerid, teamid, startdate, enddate, salary, jerseynumber, position)
VALUES (1, 1, '2020-01-01', '2022-12-31', 1000000, 10, 'Forward'),
       (2, 2, '2020-01-01', '2022-12-31', 1000000, 7, 'Forward'),
       (3, 3, '2020-01-01', '2022-12-31', 1000000, 10, 'Forward'),
       (4, 7, '2020-01-01', '2022-12-31', 1000000, 22, 'Forward'),
       (6, 4, '2020-01-01', '2022-12-31', 1000000, 99, 'Forward'),
       (7, 10, '2020-01-01', '2022-12-31', 1000000, 2, 'Defender'),
       (8, 8, '2020-01-01', '2022-12-31', 1000000, 8, 'Forward'),
       (9, 5, '2020-01-01', '2022-12-31', 100000, 7, 'Midfielder'),
       (10, 6, '2020-01-01', '2022-12-31', 1000000, 10, 'Forward'),
       (14, 9, '2020-01-01', '2022-12-31', 1000000, 10, 'Goalkeeper');


INSERT INTO CoachingStaffContract(coachingstaffid, teamid, startdate, enddate, salary)
VALUES (5, 1, '2020-01-01', '2022-12-31', 1000000),
       (11, 2, '2020-01-01', '2022-12-31', 1000000),
       (12, 3, '2020-01-01', '2022-12-31', 1000000),
       (13, 4, '2020-01-01', '2022-12-31', 1000000),
       (14, 5, '2020-01-01', '2022-12-31', 1000000),
       (15, 6, '2020-01-01', '2022-12-31', 1000000),
       (16, 10, '2020-01-01', '2022-12-31', 1000000),
       (17, 3, '2020-01-01', '2022-12-31', 1000000),
       (18, 7, '2020-01-01', '2022-12-21', 1000000),
       (19, 8, '2020-01-01', '2022-11-21', 1000000),
       (20, 9, '2020-01-01', '2022-10-31', 1000000);

-- Insert CaptainHistory
INSERT INTO CaptainHistory(playerid, teamid, startdate, enddate)
VALUES (1, 1, '2020-08-25', '2021-08-24'),
       (2, 2, '2018-07-10', '2021-07-09'),
       (3, 3, '2017-08-03', '2021-08-02'),
       (4, 4, '2014-07-09', '2021-07-08'),
       (5, 5, '2016-01-04', '2021-01-03');


-- Insert P_Sponsorship
INSERT INTO P_Sponsorship(psponsorshipid, sponsorid, playerid, startdate, enddate, type)
VALUES (1, 1, 1, '2020-01-01', '2022-12-31', 'Boot Sponsorship'),
       (2, 2, 2, '2018-01-01', '2022-12-31', 'Boot Sponsorship'),
       (3, 3, 3, '2019-01-01', '2023-12-31', 'Boot Sponsorship'),
       (4, 4, 4, '2016-01-01', '2024-12-31', 'Boot Sponsorship');

-- Insert T_Sponsorship
INSERT INTO T_Sponsorship(tsponsorshipid, sponsorid, teamid, startdate, enddate, type)
VALUES (1, 1, 1, '2020-01-01', '2023-12-31', 'Kit Sponsorship'),
       (2, 2, 2, '2019-01-01', '2023-12-31', 'Kit Sponsorship'),
       (3, 3, 3, '2018-01-01', '2022-12-31', 'Kit Sponsorship'),
       (4, 4, 4, '2017-01-01', '2021-12-31', 'Kit Sponsorship'),
       (5, 5, 5, '2020-01-01', '2024-12-31', 'Kit Sponsorship');


-- Insert Trains
INSERT INTO Trains
VALUES (5, 1),
       (5, 2),
       (5, 3),
       (5, 4),
       (12, 14),
       (11, 3),
       (15, 6),
       (16, 10),
       (17, 3),
       (18, 7),
       (19, 8),
       (20, 9);

--- Insert KitColors
INSERT INTO KitColors(kitcolorid, color, typeofkit, teamid, season)
VALUES (1, 'Blue', 'Home Kit', 1, '2020/2021'),
       (2, 'Red', 'Home Kit', 2, '2020/2021'),
       (3, 'Blue', 'Home Kit', 3, '2020/2021'),
       (4, 'Red', 'Home Kit', 4, '2020/2021'),
       (5, 'Black', 'Home Kit', 5, '2020/2021'),
       (6, 'Blue', 'Away Kit', 1, '2020/2021'),
       (7, 'White', 'Away Kit', 2, '2020/2021'),
       (8, 'White', 'Away Kit', 3, '2020/2021'),
       (9, 'White', 'Away Kit', 4, '2020/2021'),
       (10, 'White', 'Away Kit', 5, '2020/2021'),
       (11, 'Black', 'Alternate Kit', 1, '2020/2021'),
       (12, 'Black', 'Alternate Kit', 2, '2020/2021'),
       (13, 'Black', 'Alternate Kit', 3, '2020/2021'),
       (14, 'Black', 'Alternate Kit', 4, '2020/2021'),
       (15, 'Black', 'Alternate Kit', 5, '2020/2021');


-- DROP TABLE IF EXISTS captainhistory CASCADE ;
-- DROP TABLE IF EXISTS team CASCADE;
-- DROP TABLE IF EXISTS player CASCADE;
-- DROP TABLE IF EXISTS coachingstaff CASCADE;
-- DROP TABLE IF EXISTS sponsor CASCADE;
-- DROP TABLE IF EXISTS kitcolors CASCADE;
-- DROP TABLE IF EXISTS p_sponsorship CASCADE;
-- DROP TABLE IF EXISTS t_sponsorship CASCADE;
-- DROP TABLE IF EXISTS playercontract CASCADE;
-- DROP TABLE IF EXISTS coachingstaffcontract CASCADE;
-- DROP TABLE IF EXISTS trains CASCADE;
-- DROP TABLE IF EXISTS manager CASCADE;
-- DROP TABLE IF EXISTS person CASCADE;

