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
                "[1] Player.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    updatePlayer();
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
}
