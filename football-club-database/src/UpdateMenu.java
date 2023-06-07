import java.sql.Date;
import java.sql.SQLException;

public class UpdateMenu extends Menu {

    private static UpdateMenu INSTANCE;

    public static UpdateMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new UpdateMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nWhich table would you like to update?\n" +
                "[1] Person.\n" +
                "[2] Player.\n" +
                "[3] PlayerContract.\n" +
                "[4] CoachingStaffContract.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    updatePerson();
                    break;
                case "2":
                    updatePlayer();
                    break;
                case "3":
                    updatePlayerContract();
                    break;
                case "4":
                    updateCoachingStaffContract();
                    break;
                default:
                    return State.Invalid;
            }

            return State.Valid;
        });
    }

    private static void updatePlayer() throws SQLException {
        System.out.print("Enter Player ID: ");
        int playerID = nextInteger();

        System.out.print("Enter the new starting XI status (leave empty if not changing):");
        String startingXIStr = nextString();
        Boolean startingXI = startingXIStr.isEmpty() ? null : Boolean.parseBoolean(startingXIStr);

        System.out.print("Enter the new number of appearances (leave empty if not changing):");
        String appearancesStr = nextString();
        Integer appearances = appearancesStr.isEmpty() ? null : Integer.parseInt(appearancesStr);

        db.updatePlayer(playerID, startingXI, appearances);
        System.out.println("Player updated successfully.");
    }

    private void updatePerson() throws SQLException {
        System.out.print("Enter Person ID: ");
        int personID = nextInteger();

        System.out.print("Enter the new first name (leave empty if not changing): ");
        String firstName = nextString();

        System.out.print("Enter the new middle name (leave empty if not changing): ");
        String middleName = nextString();

        System.out.print("Enter the new last name (leave empty if not changing): ");
        String lastName = nextString();

        System.out.print("Enter the new date of birth (leave empty if not changing): ");
        String dateOfBirthStr = nextString();
        Date dateOfBirth = dateOfBirthStr.isEmpty() ? null : Date.valueOf(dateOfBirthStr);

        System.out.print("Enter the new nationality (leave empty if not changing): ");
        String nationality = nextString();

        db.updatePerson(personID, firstName, middleName, lastName, dateOfBirth, nationality);
        System.out.println("Person updated successfully.");
    }

    private void updatePlayerContract() throws SQLException {
        System.out.print("Enter Contract ID: ");
        int contractID = nextInteger();

        System.out.print("Enter the new Player ID (leave empty if not changing): ");
        String playerIDStr = nextString();
        Integer playerID = playerIDStr.isEmpty() ? null : Integer.parseInt(playerIDStr);

        System.out.print("Enter the new Team ID (leave empty if not changing): ");
        String teamIDStr = nextString();
        Integer teamID = teamIDStr.isEmpty() ? null : Integer.parseInt(teamIDStr);

        System.out.print("Enter the new Start Date in in format: dd-MM-yyyy (leave empty if not changing): ");
        String startDateStr = nextString();
        Date startDate = startDateStr.isEmpty() ? null : Date.valueOf(startDateStr);

        System.out.print("Enter the new End Date in in format: dd-MM-yyyy (leave empty if not changing): ");
        String endDateStr = nextString();
        Date endDate = endDateStr.isEmpty() ? null : Date.valueOf(endDateStr);

        System.out.print("Enter the new Salary (leave empty if not changing): ");
        String salaryStr = nextString();
        Double salary = salaryStr.isEmpty() ? null : Double.parseDouble(salaryStr);

        System.out.print("Enter the new Jersey Number (leave empty if not changing): ");
        String jerseyNumberStr = nextString();
        Integer jerseyNumber = jerseyNumberStr.isEmpty() ? null : Integer.parseInt(jerseyNumberStr);

        System.out.print("Enter the new Position (leave empty if not changing): ");
        String position = nextString();
        if (position.isEmpty()) {
            position = null;
        }

        db.updatePlayerContract(contractID, playerID, teamID, startDate, endDate, salary, jerseyNumber, position);
        System.out.println("Player contract updated successfully.");
    }

    private void updateCoachingStaffContract() throws SQLException {
        System.out.print("Enter Contract ID: ");
        int contractID = nextInteger();

        System.out.print("Enter the new Coaching Staff ID (leave empty if not changing): ");
        String coachingStaffIDStr = nextString();
        Integer coachingStaffID = coachingStaffIDStr.isEmpty() ? null : Integer.parseInt(coachingStaffIDStr);

        System.out.print("Enter the new Team ID (leave empty if not changing): ");
        String teamIDStr = nextString();
        Integer teamID = teamIDStr.isEmpty() ? null : Integer.parseInt(teamIDStr);

        System.out.print("Enter the new Start Date in format: dd-MM-yyyy (leave empty if not changing): ");
        String startDateStr = nextString();
        Date startDate = startDateStr.isEmpty() ? null : Date.valueOf(startDateStr);

        System.out.print("Enter the new End Date in format: dd-MM-yyyy (leave empty if not changing): ");
        String endDateStr = nextString();
        Date endDate = endDateStr.isEmpty() ? null : Date.valueOf(endDateStr);

        System.out.print("Enter the new Salary (leave empty if not changing): ");
        String salaryStr = nextString();
        Double salary = salaryStr.isEmpty() ? null : Double.parseDouble(salaryStr);

        db.updateCoachingStaffContract(contractID, coachingStaffID, teamID, startDate, endDate, salary);
        System.out.println("Coaching staff contract updated successfully.");
    }



}
