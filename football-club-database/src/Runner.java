
public class Runner {

    static {
        try {
            // Initialize driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver for PostgreSQL not found.");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--- Football Clubs Database ---");
        MenuTerminal.instance().show();
    }
}