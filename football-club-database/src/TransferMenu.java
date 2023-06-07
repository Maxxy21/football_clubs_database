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
                "[b] Back to the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    transferPlayer();
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

        System.out.print("Enter new contract start date (format: dd-MM-yyyy): ");
        Date startDate = nextDate();

        System.out.print("Enter new contract end date (format: dd-MM-yyyy): ");
        Date endDate = nextDate();

        db.transferPlayer(playerID, newTeamID, jerseyNumber, position, salary, startDate, endDate);
        System.out.println("Player transferred successfully.");
    }

}
