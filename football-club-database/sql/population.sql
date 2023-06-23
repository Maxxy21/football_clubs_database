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
       (11, 'Jose', '', 'Mourinho', '1972-03-15', 'Portugal'),
       (12, 'Frank', '', 'Lampard', '1963-01-26', 'England'),
       (13, 'Philip', '', 'Lampard', '1963-01-26', 'England'),
       (14, 'Gigi', '', 'Buffon', '1963-01-26', 'Italy'),
       (15, 'Son', '', 'Mogi', '1963-01-26', 'Korea'),
       (16, 'Lil', '', 'Things', '1963-01-26', 'England'),
       (17, 'Frank', 'Di', 'Giorno', '1963-01-26', 'Austria'),
       (18, 'Willock', 'Leaf', 'Ulm', '1963-01-26', 'Sweden'),
       (19, 'His', '', 'John', '1963-01-26', 'Poland'),
       (20, 'Gabe', '', 'Wellling', '1963-01-26', 'Germany'),
       (21, 'Gabe', '', 'Wells', '1963-01-26', 'Netherland'),
       (22, 'Polo', '', 'Baggin', '1962-01-26', 'Japan'),
       (23, 'Timothy', '', 'Bulls', '1964-01-26', 'Bhutan'),
       (24, 'John', '', 'Doe', '1968-01-26', 'United Kingdom'),
       (25, 'Janes', '', 'Wells', '1963-01-26', 'United States'),
       (26, 'Loki', '', 'Asgard', '1963-01-26', 'England'),
       (27, 'Thor', '', 'Asgard', '1963-01-26', 'England'),
       (28, 'Gavin', '', 'Newsom', '1963-01-26', 'England');


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
INSERT INTO CoachingStaff(coachingstaffid, role, yearsofexperience)
VALUES (5, 'Manager', 10),
       (11, 'Manager', 20),
       (12, 'Assistant Manager', 22),
       (13, 'Fitness Coach', 23),
       (15, 'Goalkeeping Coach', 24),
       (16, 'Physiotherapist', 25),
       (17, 'Chief Analyst', 15),
       (18, 'Nutritionist', 16),
       (19, 'Kit Manager', 17),
       (20, 'Physiotherapist', 18),
       (21, 'Manager', 3),
       (22, 'Manager', 19),
       (23, 'Manager', 20),
       (24, 'Manager', 20),
       (25, 'Manager', 20),
       (26, 'Manager', 20),
       (27, 'Manager', 20),
       (28, 'Manager', 20);

--
INSERT INTO CoachingStaffContract(coachingstaffid, teamid, startdate, enddate, salary)
VALUES (5, 1, '2020-01-01', '2022-12-31', 1000000),
       (11, 2, '2020-01-01', '2022-12-05', 1000000),
       (12, 3, '2020-01-01', '2026-12-30', 1000000),
       (13, 4, '2020-01-01', '2027-12-31', 1000000),
       (15, 5, '2020-01-01', '2028-12-31', 1000000),
       (16, 6, '2020-01-01', '2022-12-31', 1000000),
       (17, 7, '2020-01-01', '2023-12-31', 1000000),
       (18, 8, '2020-01-01', '2025-12-31', 1000000),
       (19, 9, '2020-01-01', '2029-12-31', 1000000),
       (20, 10, '2020-01-01', '2027-12-31', 1000000),
       (21, 3, '2020-01-01', '2022-12-31', 1000000),
       (22, 4, '2020-01-01', '2020-12-31', 1000000),
       (23, 5, '2020-01-01', '2022-12-31', 1000000),
       (24, 6, '2020-01-01', '2022-12-31', 1000000),
       (25, 7, '2020-01-01', '2028-12-31', 1000000),
       (26, 8, '2020-01-01', '2022-12-31', 1000000),
       (27, 9, '2020-01-01', '2022-12-31', 1000000),
       (28, 10, '2020-01-01', '2022-12-31', 1000000);

INSERT INTO PlayerContract (playerid, teamid, startdate, enddate, salary, jerseynumber, position)
VALUES (1, 1, '2020-01-01', '2022-12-31', 1000000, 10, 'Forward'),
       (2, 2, '2020-01-01', '2029-12-31', 1000000, 7, 'Forward'),
       (3, 3, '2020-01-01', '2021-12-31', 1000000, 10, 'Forward'),
       (4, 4, '2020-01-01', '2029-12-31', 1000000, 22, 'Forward'),
       (6, 5, '2020-01-01', '2024-12-31', 1000000, 99, 'Forward'),
       (7, 6, '2020-01-01', '2023-12-31', 1000000, 2, 'Defender'),
       (8, 7, '2020-01-01', '2021-12-31', 1000000, 8, 'Forward'),
       (9, 8, '2020-01-01', '2029-12-31', 100000, 7, 'Midfielder'),
       (10, 9, '2020-01-01', '2022-12-31', 1000000, 10, 'Forward'),
       (14, 10, '2020-01-01', '2022-12-31', 1000000, 10, 'Goalkeeper');


-- Insert CaptainHistory
INSERT INTO CaptainHistory(playerid, teamid, startdate, enddate)
VALUES (1, 1, '2020-08-25', '2021-08-24'),
       (2, 2, '2018-07-10', '2021-07-09'),
       (3, 3, '2017-08-03', '2021-08-02'),
       (4, 4, '2014-07-09', '2021-07-08'),
       (6, 5, '2019-08-01', '2021-07-31'),
       (7, 6, '2018-08-01', '2021-07-31'),
       (8, 7, '2017-08-01', '2021-07-31'),
       (9, 8, '2016-08-01', '2021-07-31'),
       (10, 9, '2015-08-01', '2021-07-31'),
       (14, 10, '2019-08-01', '2021-07-31');


-- Insert P_Sponsorship
INSERT INTO P_Sponsorship(psponsorshipid, sponsorid, startdate, enddate, type)
VALUES (1, 1, '2020-01-01', '2022-12-31', 'Boot Sponsorship'),
       (2, 2, '2018-01-01', '2022-12-31', 'Boot Sponsorship'),
       (3, 3, '2019-01-01', '2023-12-31', 'Boot Sponsorship'),
       (4, 4, '2016-01-01', '2024-12-31', 'Boot Sponsorship');

-- Insert T_Sponsorship
INSERT INTO T_Sponsorship(tsponsorshipid, sponsorid, startdate, enddate, type)
VALUES (1, 1, '2020-01-01', '2023-12-31', 'Kit Sponsorship'),
       (2, 2, '2019-01-01', '2023-12-31', 'Kit Sponsorship'),
       (3, 3, '2018-01-01', '2022-12-31', 'Kit Sponsorship'),
       (4, 4, '2017-01-01', '2021-12-31', 'Kit Sponsorship'),
       (5, 5, '2020-01-01', '2024-12-31', 'Kit Sponsorship');


-- Insert Trains
INSERT INTO Trains
VALUES (5, 1),
       (6, 2),
       (7, 3),
       (8, 4),
       (9, 5),
       (10, 6),
       (11, 7),
       (12, 14),
       (11, 3),
       (15, 6),
       (16, 10),
       (17, 3),
       (18, 7),
       (19, 8),
       (20, 9);

--- Insert KitColors
INSERT INTO KitColors(color)
VALUES ('Red'),
       ('Blue'),
       ('Green'),
       ('Yellow'),
       ('Black'),
       ('White'),
       ('Orange'),
       ('Purple'),
       ('Pink'),
       ('Brown');


-- Insert HasKitColor
INSERT INTO HasKitColor(teamid, color)
VALUES (1, 'Red'),
       (2, 'White'),
       (3, 'Blue'),
       (4, 'Black'),
       (5, 'Yellow'),
       (6, 'Green'),
       (7, 'Purple'),
       (8, 'Orange'),
       (9, 'Pink'),
       (10, 'Brown');

-- Insert HasTSponsorship
INSERT INTO HasTSponsorship(teamid, tsponsorshipid)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 1),
       (7, 2),
       (8, 3),
       (9, 4),
       (10, 5);

-- Insert PlayerHasPSponsorship
INSERT INTO HasPSponsorship(playerid, psponsorshipid)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (6, 1),
       (7, 2),
       (8, 3),
       (9, 4),
       (10, 1),
       (14, 2);


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

