-- View of all players and their current team:
CREATE VIEW Players_Teams AS
SELECT P.playerID, P.firstName, P.middleName, P.lastName, T.name AS Team
FROM Player AS P
         JOIN PlayerContract AS PC ON P.playerID = PC.personID
         JOIN Team AS T ON PC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN PC.startDate AND PC.endDate;

-- View of all sponsors and the teams they sponsor
CREATE VIEW Sponsors_Teams AS
SELECT S.sponsorID, S.name AS Sponsor, T.name AS Team
FROM Sponsor AS S
         JOIN T_Sponsorship AS TS ON S.sponsorID = TS.sponsorID
         JOIN Team AS T ON TS.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN TS.startDate AND TS.endDate;

-- View of all coaching staff and their current team
CREATE VIEW CoachingStaff_Contracts AS
SELECT CS.coachingStaffID, P.firstName, P.lastName, T.name AS Team, CSC.startDate, CSC.endDate, CSC.salary
FROM CoachingStaff AS CS
         JOIN Person AS P ON CS.coachingStaffID = P.personID
         JOIN CoachingStaffContract AS CSC ON CS.coachingStaffID = CSC.coachingStaffID
         JOIN Team AS T ON CSC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN CSC.startDate AND CSC.endDate;

-- View of all players and their sponsors
CREATE VIEW Players_Sponsors AS
SELECT P.playerID, P.firstName, P.middleName, P.lastName, S.name AS Sponsor
FROM Player AS P
         JOIN P_Sponsorship AS PS ON P.playerID = PS.playerID
         JOIN Sponsor AS S ON PS.sponsorID = S.sponsorID
WHERE CURRENT_DATE BETWEEN PS.startDate AND PS.endDate;
