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
        String question = "\nFrom which table would you like to delete a record?\n" +
                "[1] Team.\n" +
                "[2] Player.\n" +
                "[3] Person.\n" +
                "[4] Sponsor.\n" +
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
                case "3":
                    deletePerson();
                    break;
                case "4":
                    deleteSponsor();
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

    private void deletePerson() throws SQLException {
        System.out.print("Enter Person ID: ");
        int ID = nextInteger();

        db.deletePerson(ID);
    }

    private void deleteSponsor() throws SQLException {
        System.out.print("Enter Sponsor ID: ");
        int ID = nextInteger();

        db.deleteSponsor(ID);
    }
}
