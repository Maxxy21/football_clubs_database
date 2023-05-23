import java.sql.SQLException;

public class QueryMenu extends Menu {

    private static QueryMenu INSTANCE;

    public static QueryMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new QueryMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nWhich query would you like to execute?\n" +
                "[1] Retrieve Team's Players.\n" +
                "[2] List all sponsorships for a player\n" +
                "[3] List all sponsorships for a team.\n" +
                "[4] Retrieve a Player's contract.\n" +
                "[5] Retrieve a Player's current position.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    queryTeamPlayers();
                    break;
                case "2":
                    querySponsorsOfPlayer();
                    break;
                case "3":
                    querySponsorsOfTeam();
                    break;
                case "4":
                    queryPlayerContract();
                    break;
                case "5":
                    queryPlayerPosition();
                    break;
                default:
                    return State.Invalid;
            }
            return State.Valid;
        });
    }


    private void queryTeamPlayers() throws SQLException {
        System.out.print("Enter team ID: ");
        int teamID = nextInteger();

        db.getTeamPlayers(teamID);
    }

    private void queryPlayerContract() throws SQLException {
        System.out.print("Enter player ID: ");
        int playerID = nextInteger();

        db.getPlayerContract(playerID);
    }

    private void querySponsorsOfTeam() throws SQLException {
        System.out.print("Enter team ID: ");
        int teamID = nextInteger();

        db.getTeamSponsorships(teamID);
    }

    private void querySponsorsOfPlayer() throws SQLException {
        System.out.print("Enter player ID: ");
        int playerID = nextInteger();

        db.getPlayerSponsorships(playerID);
    }


    private void queryPlayerPosition() throws SQLException {
        System.out.print("Enter player ID: ");
        int playerID = nextInteger();

        db.getPlayerPosition(playerID);
    }

}
