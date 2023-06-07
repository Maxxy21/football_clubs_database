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
        String question = "\nIn which table would you like to insert a record?\n" +
                "[1] Team.\n" +
                "[2] Person.\n" +
                "[3] Player.\n" +
                "[4] PersonContract.\n" +
                "[5] CoachingStaffContract.\n" +
                "[6] Sponsor.\n" +
                "[b] Back to the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    insertTeam();
                    break;
                case "2":
                    insertNewPerson();
                    break;
                case "3":
                    insertNewPlayer();
                    break;
                case "4":
                    insertNewPlayerContract();
                    break;
                case "5":
                    insertNewCoachingStaffContract();
                    break;
                case "6":
                    insertNewSponsor();
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

        System.out.print("Enter team foundationYear: ");
        int foundationYear = nextInteger();

        db.insertTeam(teamId, name, city, foundationYear);
    }

    public void insertNewPlayer() throws SQLException {
        System.out.print("Enter playerID: ");
        int playerID = nextInteger();

        System.out.print("Is the player in the starting XI? (true/false): ");
        boolean startingXI = nextBoolean();

        System.out.print("Enter appearances: ");
        int appearances = nextInteger();

        db.insertPlayer(playerID, startingXI, appearances);
    }

    public void insertNewPerson() throws SQLException {
        System.out.print("Enter personID: ");
        int personID = nextInteger();

        System.out.print("Enter firstName: ");
        String firstName = nextString();

        System.out.print("Enter middleName: ");
        String middleName = nextNullableString(); // Nullable

        System.out.print("Enter lastName: ");
        String lastName = nextString();

        System.out.print("Enter dob (format: dd-MM-yyyy): ");
        Date dob = nextDate();

        System.out.print("Enter nationality: ");
        String nationality = nextString();

        db.insertPerson(personID, firstName, middleName, lastName, dob, nationality);
    }

    public void insertNewPlayerContract() throws SQLException {
        System.out.print("Enter contractID: ");
        int contractID = nextInteger();

        System.out.print("Enter playerID: ");
        int playerID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter startDate (format: dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter endDate (format: dd-MM-yyyy): ");
        Date endDate = nextDate();

        System.out.print("Enter salary: ");
        double salary = nextDouble();

        System.out.print("Enter jerseyNumber: ");
        int jerseyNumber = nextInteger();

        System.out.print("Enter position: ");
        String position = nextString();

        db.insertPlayerContract(contractID, playerID, teamID, startDate, endDate, salary, jerseyNumber, position);
    }

    public void insertNewCoachingStaffContract() throws SQLException {
        System.out.print("Enter contractID: ");
        int contractID = nextInteger();

        System.out.print("Enter coachingStaffID: ");
        int personID = nextInteger();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter startDate (format: dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter endDate (format: dd-MM-yyyy): ");
        Date endDate = nextDate();

        System.out.print("Enter salary: ");
        double salary = nextDouble();

        db.insertCoachingStaffContract(contractID, personID, teamID, startDate, endDate, salary);
    }


    public void insertNewSponsor() throws SQLException {
        System.out.print("Enter sponsorID: ");
        int sponsorID = nextInteger();

        System.out.print("Enter sponsor name: ");
        String name = nextString();

        System.out.print("Enter sponsor industry: ");
        String industry = nextString();

        System.out.print("Enter sponsor foundation year: ");
        int foundationYear = nextInteger();

        db.insertSponsor(sponsorID, name, industry, foundationYear);
    }

}
