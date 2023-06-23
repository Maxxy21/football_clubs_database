-- View of all players and their current team:
CREATE VIEW Players_Teams AS
SELECT P.personid, P.firstName, P.middleName, P.lastName, T.name AS Team
FROM Person AS P
         JOIN PlayerContract AS PC ON P.personid = PC.playerid
         JOIN Team AS T ON PC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN PC.startDate AND PC.endDate;

-- View of all coaching staff and their current team
CREATE VIEW CoachingStaff_Contracts AS
SELECT CS.coachingStaffID, P.firstName, P.lastName, T.name AS Team, CSC.startDate, CSC.endDate, CSC.salary
FROM CoachingStaff AS CS
         JOIN Person AS P ON CS.coachingStaffID = P.personID
         JOIN CoachingStaffContract AS CSC ON CS.coachingStaffID = CSC.coachingStaffID
         JOIN Team AS T ON CSC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN CSC.startDate AND CSC.endDate;

-- View of all managers and their current team:
CREATE VIEW Managers_Teams AS
SELECT CS.coachingStaffID, P.firstName, P.middleName, P.lastName, CS.role, CS.yearsOfExperience, T.name AS Team
FROM Person AS P
         JOIN CoachingStaff AS CS ON P.personID = CS.coachingStaffID
         JOIN CoachingStaffContract AS CSC ON CS.coachingStaffID = CSC.coachingStaffID
         JOIN Team AS T ON CSC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN CSC.startDate AND CSC.endDate AND CS.role = 'Manager';

-- View of all persons and their current team:
CREATE VIEW Persons_In_Team AS
SELECT P.personID, P.firstName, P.middleName, P.lastName, P.dob, P.nationality, T.name AS Team
FROM Person AS P
         JOIN PlayerContract AS PC ON P.personID = PC.playerID
         JOIN Team AS T ON PC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN PC.startDate AND PC.endDate
UNION
SELECT P.personID, P.firstName, P.middleName, P.lastName, P.dob, P.nationality, T.name AS Team
FROM Person AS P
         JOIN CoachingStaffContract AS CSC ON P.personID = CSC.coachingStaffID
         JOIN Team AS T ON CSC.teamID = T.teamID
WHERE CURRENT_DATE BETWEEN CSC.startDate AND CSC.endDate;

