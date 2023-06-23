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
                "[4] CoachingStaff.\n" +
                "[5] PlayerContract.\n" +
                "[6] CoachingStaffContract.\n" +
                "[7] Sponsor.\n" +
                "[8] Team Sponsorship.\n" +
                "[9] Player Sponsorship.\n" +
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
                    insertNewCoachingStaff();
                    break;
                case "5":
                    insertNewPlayerContract();
                    break;
                case "6":
                    insertNewCoachingStaffContract();
                    break;
                case "7":
                    insertNewSponsor();
                    break;
                case "8":
                    insertNewTSponsorship();
                    break;
                case "9":
                    insertNewPSponsorship();
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

        System.out.print("Enter number of kit colors: ");
        int numKitColors = nextInteger();

        String[] kitColors = new String[numKitColors];
        for (int i = 0; i < numKitColors; i++) {
            System.out.print("Enter kit color " + (i + 1) + ": ");
            kitColors[i] = nextString();
        }

        db.insertTeam(teamId, name, city, foundationYear, kitColors);
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
        System.out.print("Enter playerID: ");
        int playerID = nextInteger();

        if (db.isUnderContract(playerID, "PlayerContract")) {
            System.out.println("The coaching staff member is already under contract.");
        } else {
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

            db.insertPlayerContract(playerID, teamID, startDate, endDate, salary, jerseyNumber, position);
        }
    }

    public void insertNewCoachingStaffContract() throws SQLException {
        System.out.print("Enter coachingStaffID: ");
        int coachingStaffID = nextInteger();

        if (db.isUnderContract(coachingStaffID, "CoachingStaffContract")) {
            System.out.println("The coaching staff member is already under contract.");
        } else {

            System.out.print("Enter teamID: ");
            int teamID = nextInteger();

            System.out.print("Enter startDate (format: dd-MM-yyyy): ");
            Date startDate = nextDate();

            System.out.print("Enter endDate (format: dd-MM-yyyy): ");
            Date endDate = nextDate();

            System.out.print("Enter salary: ");
            double salary = nextDouble();

            db.insertCoachingStaffContract(coachingStaffID, teamID, startDate, endDate, salary);
        }
    }

    public void insertNewCoachingStaff() throws SQLException {
        System.out.print("Enter coachingStaffID: ");
        int coachingStaffID = nextInteger();

        System.out.print("Enter coachingStaff role: ");
        String role = nextString();

        System.out.print("Enter years of experienc: ");
        int yearsOfExperience = nextInteger();

        db.insertCoachingStaff(coachingStaffID, role, yearsOfExperience);
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

    public void insertNewKitColor() throws SQLException {
        System.out.print("Enter kit color: ");
        String color = nextString();

        System.out.print("Enter teamID: ");
        int teamID = nextInteger();

        db.insertKitColor(color, teamID);
    }

    public void insertNewTSponsorship() throws SQLException {
        System.out.print("Enter Team SponsorshipID: ");
        int tSponsorshipID = nextInteger();

        System.out.print("Enter Sponsor ID: ");
        int sponsorID = nextInteger();

        System.out.print("Enter start date (format: dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter end date (format: dd-MM-yyyy): ");
        Date endDate = nextDate();

        System.out.print("Enter type: ");
        String type = nextString();

        System.out.print("Enter team ID: ");
        int teamID = nextInteger();

        db.insertTSponsorship(tSponsorshipID, sponsorID, startDate, endDate, type, teamID);
    }

    public void insertNewPSponsorship() throws SQLException {
        System.out.print("Enter Player SponsorshipID: ");
        int pSponsorshipID = nextInteger();

        System.out.print("Enter Sponsor ID: ");
        int sponsorID = nextInteger();

        System.out.print("Enter start date (format: dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter end date (format: dd-MM-yyyy): ");
        Date endDate = nextDate();

        System.out.print("Enter type: ");
        String type = nextString();

        System.out.print("Enter player ID: ");
        int playerID = nextInteger();

        db.insertPSponsorship(pSponsorshipID, sponsorID, startDate, endDate, type, playerID);
    }

    public void insertTrains() throws SQLException {
        System.out.print("Enter coachingStaffID: ");
        int coachingStaffID = nextInteger();

        System.out.print("Enter playerID: ");
        int playerID = nextInteger();

        db.insertTrains(coachingStaffID, playerID);
    }


}
