import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public class InsertMenu extends Menu {

    private static InsertMenu INSTANCE;

    public static InsertMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new InsertMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nIn which table would you like to insert something?\n" +
                "[1] Team.\n" +
                "[2] Player.\n" +
                "[3] Coaching Staff.\n" +
                "[4] Manager.\n" +
                "[5] Sponsor.\n" +
                "[6] League.\n" +
                "[7] Position.\n" +
                "[b] Back to the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    insertTeam();
                    break;
                case "2":
                    insertNewPlayer();
                    break;
                case "3":
                    insertNewCoachingStaff();
                    break;
                case "4":
                    insertNewManager();
                    break;
                case "5":
                    insertSponsor();
                    break;
                case "6":
                    insertLeague();
                    break;
                case "7":
                    insertPosition();
                    break;
                default:
                    return State.Invalid;
            }
            return State.Valid;
        });
    }

    public void insertTeam() throws SQLException {
        System.out.print("Enter team teamId: ");
        int teamId = nextInteger();

        System.out.print("Enter team name: ");
        String name = nextString();

        System.out.print("Enter team city: ");
        String city = nextString();

        System.out.print("Enter team kitColors: ");
        String kitColors = nextString();

        System.out.print("Enter team foundationYear: ");
        int foundationYear = nextInteger();

        db.insertTeam(teamId, name, city, kitColors, foundationYear);

    }

    public void insertLeague() throws SQLException {
        System.out.print("Enter leagueID: ");
        int leagueID = nextInteger();

        System.out.print("Enter name: ");
        String leagueName = nextString();

        System.out.print("Enter league country: ");
        String leagueCountry = nextString();

        System.out.print("Enter start date (dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter end date (dd-MM-yyyy): ");
        Date endDate = nextDate();

        System.out.print("Enter number of teams: ");
        int numOfTeams = nextInteger();


        db.insertLeague(leagueID, leagueName, leagueCountry, startDate, endDate, numOfTeams);
    }


    public void insertCoachingStaff() throws SQLException {
        System.out.print("Enter coachingStaffID: ");
        int coachingStaffID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter role: ");
        String role = nextString();

        db.insertCoachingStaff(coachingStaffID, role, teamID);
    }

    public void insertNewCoachingStaff() throws SQLException {
        insertPerson();
        insertCoachingStaff();
    }

    public void insertManager() throws SQLException {
        System.out.print("Enter managerID: ");
        int managerID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        db.insertManager(managerID, teamID);
    }

    public void insertNewManager() throws SQLException {
        insertPerson();
        insertManager();
    }

    public void insertCaptain() throws SQLException {
        System.out.print("Enter captainID: ");
        int captainID = nextInteger();

        System.out.print("Enter captainSince (dd-MM-yyyy): ");
        Date captainSince = nextDate();

        System.out.print("Enter seniority: ");
        int seniority = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        db.insertCaptain(captainID, captainSince, seniority, teamID);
    }

    public void insertSponsor() throws SQLException {
        System.out.print("Enter sponsorID: ");
        int sponsorID = nextInteger();

        System.out.print("Enter sponsor name: ");
        String sponsorName = nextString();

        System.out.print("Enter industry: ");
        String industry = nextString();

        System.out.print("Enter foundation year: ");
        int foundationYear = nextInteger();

        db.insertSponsor(sponsorID, sponsorName, industry, foundationYear);
    }

    public void insertPerson() throws SQLException {
        System.out.print("Enter personID: ");
        int personID = nextInteger();

        System.out.print("Enter first name: ");
        String firstName = nextString();

        System.out.print("Do you want to enter middle name? (yes/no): ");
        String middleName = null;
        if (nextString().equalsIgnoreCase("yes")) {
            System.out.print("Enter middle name: ");
            middleName = nextString();
        }

        System.out.print("Do you want to enter last name? (yes/no): ");
        String lastName = null;
        if (nextString().equalsIgnoreCase("yes")) {
            System.out.print("Enter last name: ");
            lastName = nextString();
        }

        System.out.print("Enter date of birth (dd-MM-yyyy): ");
        Date dob = nextDate();

        System.out.print("Enter nationality: ");
        String nationality = nextString();

        db.insertPerson(personID, firstName, middleName, lastName, dob, nationality);
    }

    public void insertPlayer() throws SQLException {
        System.out.print("Enter playerID: ");
        int playerID = nextInteger();

        System.out.print("Is the player in the starting XI? (true/false): ");
        boolean startingXI = nextBoolean();

        System.out.print("Enter appearances: ");
        int appearances = nextInteger();

        db.insertPlayer(playerID, startingXI, appearances);
    }

    public void insertNewPlayer() throws SQLException {
        insertPerson();
        insertPlayer();
        insertPlayerPosition();
        insertStateOfPlaysFor();
    }

    public void insertPosition() throws SQLException {
        System.out.print("Enter positionID: ");
        int positionID = nextInteger();

        System.out.print("Enter position type: ");
        String positionType = nextString();

        db.insertPosition(positionID, positionType);
    }

    private static void insertPlayerPosition() throws SQLException {
        System.out.print("Enter player ID: ");
        int playerID = nextInteger();

        System.out.print("Enter position ID: ");
        int positionID = nextInteger();

        db.insertPlayerPosition(playerID, positionID);
        System.out.println("Player position relationship inserted successfully.");
    }

    public void insertStateOfContract() throws SQLException {
        System.out.print("Enter personID: ");
        int personID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter start date (dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter end date (dd-MM-yyyy): ");
        Date endDate = nextDate();

        System.out.print("Enter salary: ");
        BigDecimal salary = nextBigDecimal();

        db.updateInsertStateOfContract(personID, teamID, startDate, endDate, salary);
    }

    public void insertStateOfPlaysFor() throws SQLException {
        System.out.print("Enter playerID: ");
        int playerID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter start date (dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter jersey number: ");
        int jerseyNumber = nextInteger();

        db.updateInsertStateOfPlaysFor(playerID, teamID, startDate, jerseyNumber);
    }

    public void insertStateOfManage() throws SQLException {
        System.out.print("Enter managerID: ");
        int managerID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter start date (dd-MM-yyyy): ");
        Date startDate = nextDate();

        db.updateInsertStateOfManage(managerID, teamID, startDate);
    }


}
