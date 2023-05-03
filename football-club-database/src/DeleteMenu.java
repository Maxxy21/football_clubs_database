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
                "[3] Person.\n" +
                "[4] Coaching Staff.\n" +
                "[5] Manager.\n" +
                "[6] Sponsor.\n" +
                "[7] League.\n" +
                "[8] Captain.\n" +
                "[9] Position.\n" +
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
                    deleteCoachingStaff();
                    break;
                case "5":
                    deleteManager();
                    break;
                case "6":
                    deleteSponsor();
                    break;
                case "7":
                    deleteLeague();
                    break;
                case "8":
                    deleteCaptain();
                    break;
                case "9":
                    deletePosition();
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

    private void deleteCoachingStaff() throws SQLException {
        System.out.print("Enter Coaching Staff ID: ");
        int ID = nextInteger();

        db.deleteCoachingStaff(ID);
    }

    private void deleteManager() throws SQLException {
        System.out.print("Enter Manager ID: ");
        int ID = nextInteger();

        db.deleteManager(ID);
    }

    private void deleteSponsor() throws SQLException {
        System.out.print("Enter Sponsor ID: ");
        int ID = nextInteger();

        db.deleteSponsor(ID);
    }

    private void deleteLeague() throws SQLException {
        System.out.print("Enter League ID: ");
        int ID = nextInteger();

        db.deleteLeague(ID);
    }

    private void deleteCaptain() throws SQLException {
        System.out.print("Enter Captain ID: ");
        int ID = nextInteger();

        db.deleteCaptain(ID);
    }

    private void deletePosition() throws SQLException {
        System.out.print("Enter Position ID: ");
        int ID = nextInteger();

        db.deletePosition(ID);
    }

}
