import java.sql.Date;
import java.sql.SQLException;

public class TransferMenu extends Menu {

    private static TransferMenu INSTANCE;

    public static TransferMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new TransferMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nWhich transfer action would you like to take?\n" +
                "[1] Transfer player.\n" +
                "[2] Transfer manager.\n" +
                "[b] Back to the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    transferPlayer();
                    break;
                case "2":
                    transferManager();
                    break;
                default:
                    return State.Invalid;
            }
            return State.Valid;
        });
    }

    private void transferPlayer() throws SQLException {
        System.out.print("Enter Player ID: ");
        int playerID = nextInteger();

        System.out.print("Enter new Team ID: ");
        int newTeamID = nextInteger();

        System.out.print("Enter new jersey number: ");
        int jerseyNumber = nextInteger();

        System.out.print("Enter new position: ");
        String position = nextString();

        System.out.print("Enter new salary: ");
        double salary = nextDouble();

        db.transferPlayer(playerID, newTeamID, jerseyNumber, position, salary);
        System.out.println("Player transferred successfully.");
    }

    private void transferManager() throws SQLException {
        System.out.print("Enter Manager ID: ");
        int managerID = nextInteger();

        System.out.print("Enter new Team ID: ");
        int newTeamID = nextInteger();

        System.out.print("Enter new salary: ");
        double salary = nextDouble();

        System.out.print("Enter new start date (format yyyy-mm-dd): ");
        Date startDate = Date.valueOf(nextString());

        System.out.print("Enter new end date (format yyyy-mm-dd): ");
        Date endDate = Date.valueOf(nextString());

        db.transferManager(managerID, newTeamID, salary, startDate, endDate);
        System.out.println("Manager transferred successfully.");
    }
}
