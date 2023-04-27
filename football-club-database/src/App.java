import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            String url = "jdbc:postgresql://localhost:5433/footballClubs";
            String user = "postgres";
            String password = "password";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to server successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error closing the database: " + ex.getMessage());
            }
        }
    }

}