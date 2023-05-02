import java.sql.Connection;
import java.sql.SQLException;

public class MenuTerminal extends Menu {

    private static MenuTerminal INSTANCE;

    public static MenuTerminal instance() {
        if (INSTANCE == null)
            INSTANCE = new MenuTerminal();
        return INSTANCE;
    }

    public State show() {
        askCredentials();
        return askAction();
    }

    private void askCredentials() {
        String database, username, password;
        Connection conn = null;

        while (conn == null) {
            System.out.print("\nEnter name of database: ");
            database = nextString();

            System.out.print("Enter username: ");
            username = nextString();

            System.out.print("Enter password: ");
            password = nextString();

            try {
                conn = db.connect(database, username, password);
            } catch (Exception e) {
                System.out.println("Could not connect to the database!");
                System.out.println(e.getMessage());
            }
        }
    }


    private State askAction() {
        boolean connected = true;
        State state;

        do {
            String question = "\nWhat would you like to do?\n" +
                    "[1] Commit/rollback transaction.\n" +
                    "[2] Edit the database.\n" +
                    "[3] Query the database.\n" +
                    "[q] Quit the program.";

            state = processCommand(question, (cmd) -> {
                switch (cmd) {
                    case "1":
                        return new CommitMenu().show();
                    case "2":
                        return EditMenu.instance().show();
                    case "3":
                        return QueryMenu.instance().show();
                    default:
                        return State.Invalid;
                }
            });
        } while (state != State.Quit && (connected = db.isConnected()));

        if (connected) {
            try {
                db.disconnect();
            } catch (SQLException e) {
                System.out.printf("Could not disconnect properly from the database.%n%s%n", e.getMessage());
            }
        } else
            System.out.println("Connection to database lost.");

        return state;
    }

}
