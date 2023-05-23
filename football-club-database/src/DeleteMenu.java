import java.sql.SQLException;

public class DeleteMenu extends Menu {

    private static DeleteMenu INSTANCE;

    public static DeleteMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new DeleteMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nFrom which table would you like to delete something?\n" +
                "[1] Team.\n" +
                "[2] Player.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    deleteTeam();
                    break;
                case "2":
                    deletePlayer();
                    break;
                default:
                    return State.Invalid;
            }
            return State.Valid;
        });
    }

    private void deleteTeam() throws SQLException {
        System.out.print("Enter Team ID: ");
        int ID = nextInteger();

        db.deleteTeam(ID);
    }

    private void deletePlayer() throws SQLException {
        System.out.print("Enter Player ID: ");
        int ID = nextInteger();

        db.deletePlayer(ID);
    }
}
