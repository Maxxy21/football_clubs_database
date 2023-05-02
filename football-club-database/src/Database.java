import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

    private static final int TIMEOUT = 10;

    private Connection conn;

    public Connection connect(String database, String username, String password) throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/" + database;
        conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        return conn;
    }

    public void disconnect() throws SQLException {
        if (conn != null)
            conn.close();
    }

    public boolean isConnected() {
        boolean active = false;

        try {
            active = conn.isValid(TIMEOUT);
        } catch (SQLException ignored) {
        }

        return active;
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }


    /**
     * =================================================================== --
     * Methods for insertions--
     * ================================================================
     */

    // Insertion of teams
    public void insertTeam(int teamID, String name, String city, String kitColors, int foundationYear) throws SQLException {
        boolean exists = recordExists("Team", "teamID", teamID);
        if (exists) {
            System.out.println("Team with ID " + teamID + " already exists.");
        } else {
            String query = "INSERT INTO Team (teamID, name, city, kitColors, foundationYear) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, teamID);
                pstmt.setString(2, name);
                pstmt.setString(3, city);
                pstmt.setString(4, kitColors);
                pstmt.setInt(5, foundationYear);
                pstmt.executeUpdate();
            }
        }
    }


    // Insertion of leagues
    public void insertLeague(int leagueID, String name, String country, Date startDate, Date endDate, int numTeams) throws SQLException {
        boolean exists = recordExists("League", "leagueID", leagueID);
        if (exists) {
            System.out.println("League with ID " + leagueID + " already exists.");
        } else {
            String query = "INSERT INTO League (leagueID, name, country, startDate, endDate, numTeams) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, leagueID);
                pstmt.setString(2, name);
                pstmt.setString(3, country);
                pstmt.setDate(4, startDate);
                pstmt.setDate(5, endDate);
                pstmt.setInt(6, numTeams);
                pstmt.executeUpdate();
            }
        }
    }


    // Insertion of sponsors
    public void insertSponsor(int sponsorID, String name, String industry, int foundationYear) throws SQLException {
        boolean exists = recordExists("Sponsor", "sponsorID", sponsorID);
        if (exists) {
            System.out.println("Sponsor with ID " + sponsorID + " already exists.");
        } else {
            String query = "INSERT INTO Sponsor (sponsorID, name, industry, foundationYear) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, sponsorID);
                pstmt.setString(2, name);
                pstmt.setString(3, industry);
                pstmt.setInt(4, foundationYear);
                pstmt.executeUpdate();
            }
        }
    }

    // Insertion of persons
    public void insertPerson(int personID, String firstName, String middleName, String lastName, Date dob, String nationality) throws SQLException {
        boolean exists = recordExists("Person", "personID", personID);
        if (exists) {
            System.out.println("Person with ID " + personID + " already exists.");
        } else {
            String query = "INSERT INTO Person (personID, firstName, middleName, lastName, dob, nationality) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, personID);
                pstmt.setString(2, firstName);
                pstmt.setString(3, middleName);
                pstmt.setString(4, lastName);
                pstmt.setDate(5, dob);
                pstmt.setString(6, nationality);
                pstmt.executeUpdate();
            }
        }

    }

    // Insertion of players
    public void insertPlayer(int playerID, boolean startingXI, int appearances) throws SQLException {
        boolean exists = recordExists("Player", "playerID", playerID);
        if (exists) {
            System.out.println("Player with ID " + playerID + " already exists.");
        } else {
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Player (playerID, startingXI, appearances) VALUES (?, ?, ?)")) {
                pstmt.setInt(1, playerID);
                pstmt.setBoolean(2, startingXI);
                pstmt.setInt(3, appearances);
                pstmt.executeUpdate();
            }
        }
    }

    // Insertion of coaching staff
    public void insertCoachingStaff(int coachingStaffID, String role, int teamID) throws SQLException {
        boolean exists = recordExists("CoachingStaff", "coachingStaffID", coachingStaffID);
        if (exists) {
            System.out.println("Coaching staff with ID " + coachingStaffID + " already exists.");
        } else {
            String query = "INSERT INTO CoachingStaff (coachingStaffID, role, teamID) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, coachingStaffID);
                pstmt.setString(2, role);
                pstmt.setInt(3, teamID);
                pstmt.executeUpdate();
            }
        }
    }

    // Insertion of managers
    public void insertManager(int managerID, int yearsOfExperience) throws SQLException {
        boolean exists = recordExists("Manager", "managerID", managerID);
        if (exists) {
            System.out.println("Manager with ID " + managerID + " already exists.");
        } else {
            String query = "INSERT INTO Manager (managerID, yearsOfExperience) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, managerID);
                pstmt.setInt(2, yearsOfExperience);
                pstmt.executeUpdate();
            }
        }
    }

    // Insertion of captains
    public void insertCaptain(int captainID, Date captainSince, int seniority, int teamID) throws SQLException {
        boolean exists = recordExists("Captain", "captainID", captainID);
        if (exists) {
            System.out.println("Captain with ID " + captainID + " already exists.");
        } else {
            String query = "INSERT INTO Captain (captainID, captainSince, seniority, teamID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, captainID);
                pstmt.setDate(2, captainSince);
                pstmt.setInt(3, seniority);
                pstmt.setInt(4, teamID);
                pstmt.executeUpdate();
            }
        }
    }

    // Insertion of positions
    public void insertPosition(int positionID, String type) throws SQLException {
        boolean exists = recordExists("Position", "positionID", positionID);
        if (exists) {
            System.out.println("Position with ID " + positionID + " already exists.");
        } else {
            String query = "INSERT INTO Position (positionID, type) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, positionID);
                pstmt.setString(2, type);
                pstmt.executeUpdate();
            }
        }
    }

    public void insertPlayerPosition(int playerID, int positionID) throws SQLException {
        boolean playerPositionExists = recordExists("Plays", "playerID", playerID, "positionID", positionID);
        if (playerPositionExists) {
            System.out.println("Player with ID " + playerID + " already has the position with ID " + positionID + ".");
        } else {
            String query = "INSERT INTO Plays (playerID, positionID) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, playerID);
                pstmt.setInt(2, positionID);
                pstmt.executeUpdate();
            }
        }
    }

    // Insertion or update of StateOfContract
    public void updateInsertStateOfContract(int personID, int teamID, Date startDate, Date endDate, BigDecimal salary) throws SQLException {
        String tableName = "StateOfContract";
        String primaryKeyColumn = "personID";
        List<String> columns = Arrays.asList("teamID", "startDate", "endDate", "salary");
        List<Object> values = Arrays.asList(teamID, startDate, endDate, salary);

        updateInsertHistoricizedEntity(tableName, primaryKeyColumn, personID, columns, values);
    }

    // Insertion or update of StateOfManage
    public void updateInsertStateOfManage(int managerID, int teamID, Date startDate) throws SQLException {
        String tableName = "StateOfManage";
        String primaryKeyColumn = "managerID";
        List<String> columns = Arrays.asList("teamID", "startDate");
        List<Object> values = Arrays.asList(teamID, startDate);

        updateInsertHistoricizedEntity(tableName, primaryKeyColumn, managerID, columns, values);
    }

    // Insertion or update of StateOfPlaysFor
    public void updateInsertStateOfPlaysFor(int playerID, int teamID, Date startDate, int jerseyNumber) throws SQLException {
        String tableName = "StateOfPlaysFor";
        String primaryKeyColumn = "playerID";
        List<String> columns = Arrays.asList("teamID", "startDate", "jerseyNumber");
        List<Object> values = Arrays.asList(teamID, startDate, jerseyNumber);

        updateInsertHistoricizedEntity(tableName, primaryKeyColumn, playerID, columns, values);
    }

    public void updateTeam(int teamID, String name, String city, String kitColors, Integer foundationYear) throws SQLException {
        String query = "UPDATE Team SET name = COALESCE(?, name), city = COALESCE(?, city), kitColors = COALESCE(?, kitColors)," +
                " foundationYear = COALESCE(?, foundationYear) WHERE teamID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, name);
            pstmt.setObject(2, city);
            pstmt.setObject(3, kitColors);
            pstmt.setObject(4, foundationYear);
            pstmt.setInt(5, teamID);

            pstmt.executeUpdate();
        }
    }

    public void updateSponsor(int sponsorID, String name, String industry, Integer foundationYear) throws SQLException {
        String query = "UPDATE Sponsor SET name = COALESCE(?, name), industry = COALESCE(?, industry)," +
                " foundationYear = COALESCE(?, foundationYear) WHERE sponsorID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, name);
            pstmt.setObject(2, industry);
            pstmt.setObject(3, foundationYear);
            pstmt.setInt(4, sponsorID);

            pstmt.executeUpdate();
        }
    }

    public void updatePlayer(int playerID, Boolean startingXI, Integer appearances) throws SQLException {
        String query = "UPDATE Player SET startingXI = COALESCE(?, startingXI), appearances = COALESCE(?, appearances) WHERE playerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, startingXI);
            pstmt.setObject(2, appearances);
            pstmt.setInt(3, playerID);

            pstmt.executeUpdate();
        }
    }

    public void updateManager(int managerID, Integer yearsOfExperience) throws SQLException {
        String query = "UPDATE Manager SET yearsOfExperience = COALESCE(?, yearsOfExperience) WHERE managerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, yearsOfExperience);
            pstmt.setInt(2, managerID);

            pstmt.executeUpdate();
        }
    }

    public void updatePlayerPosition(int playerID, int oldPositionID, int newPositionID) throws SQLException {
        String query = "UPDATE Plays SET positionID = ? WHERE playerID = ? AND positionID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newPositionID);
            pstmt.setInt(2, playerID);
            pstmt.setInt(3, oldPositionID);
            pstmt.executeUpdate();
        }
    }

    public void updateManagerOfTeam(int newManagerID, int teamID, Date startDate) throws SQLException {
        String query = "INSERT INTO StateOfManage (managerID, teamID, startDate) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newManagerID);
            pstmt.setInt(2, teamID);
            pstmt.setDate(3, startDate);
            pstmt.executeUpdate();
        }
    }

    public void updateInsertCaptain(int captainID, Date captainSince, int seniority, int teamID) throws SQLException {
        String query = "INSERT INTO Captain (captainID, captainSince, seniority, teamID) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (teamID) DO UPDATE SET captainID = excluded.captainID, captainSince = excluded.captainSince, seniority = excluded.seniority";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, captainID);
            stmt.setDate(2, captainSince);
            stmt.setInt(3, seniority);
            stmt.setInt(4, teamID);
            stmt.executeUpdate();
        }
    }


    /**
     * =================================================================== --
     * Deletion Methods--
     * ================================================================
     */
    public void deleteTeam(int teamID) throws SQLException {
        deleteRecord("Team", "teamID", teamID);
    }

    public void deletePerson(int personID) throws SQLException {
        deleteRecord("Person", "personID", personID);
    }

    public void deletePlayer(int playerID) throws SQLException {
        deleteRecord("Player", "playerID", playerID);
    }

    public void deleteCoachingStaff(int coachingStaffID) throws SQLException {
        deleteRecord("CoachingStaff", "coachingStaffID", coachingStaffID);
    }

    public void deleteManager(int managerID) throws SQLException {
        deleteRecord("Manager", "managerID", managerID);
    }

    public void deleteCaptain(int captainID) throws SQLException {
        deleteRecord("Captain", "captainID", captainID);
    }

    public void deletePosition(int positionID) throws SQLException {
        deleteRecord("Position", "positionID", positionID);
    }

    public void deleteSponsor(int sponsorID) throws SQLException {
        deleteRecord("Sponsor", "sponsorID", sponsorID);
    }

    public void deleteLeague(int leagueID) throws SQLException {
        deleteRecord("League", "leagueID", leagueID);
    }

    public void deleteStateOfContract(int personID) throws SQLException {
        deleteRecord("StateOfContract", "personID", personID);
    }

    public void deleteStateOfManage(int managerID) throws SQLException {
        deleteRecord("StateOfManage", "managerID", managerID);
    }

    public void deleteStateOfPlaysFor(int playerID) throws SQLException {
        deleteRecord("StateOfPlaysFor", "playerID", playerID);
    }


    /**
     * =================================================================== --
     * Methods for updating and insertion of the historicized entities--
     * ================================================================
     */

    public void updateInsertHistoricizedEntity(String tableName, String primaryKeyColumn, Object primaryKeyValue, List<String> columns, List<Object> values) throws SQLException {
        // Check if the number of columns and values are the same
        if (columns.size() != values.size()) {
            throw new IllegalArgumentException("Columns and values lists must have the same size.");
        }

        // Check if a record with the given primary key value already exists
        boolean recordExists = recordExists(tableName, primaryKeyColumn, primaryKeyValue);

        // Generate the appropriate query (insert or update) based on whether the record exists
        String query;
        if (recordExists) {
            query = generateUpdateQuery(tableName, primaryKeyColumn, columns);
        } else {
            query = generateInsertQuery(tableName, columns);
        }

        // Execute the generated query with the provided values
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }
            pstmt.setObject(values.size() + 1, primaryKeyValue);
            pstmt.executeUpdate();
        }
    }

    /**
     * =================================================================== --
     * Methods for sample queries--
     * ================================================================
     */

    public String getPlayerPosition(int playerID) throws SQLException {
        String query = "SELECT p.type FROM Position p JOIN Plays pl ON p.positionID = pl.positionID " +
                "WHERE pl.playerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("type");
                }
            }
        }

        return null;
    }

    public void getTeamPlayers(int teamID) throws SQLException {
        try (TablePrinter printer = new TablePrinter("playerID")) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT playerID FROM StateOfPlaysFor WHERE teamID = ?")) {
                pstmt.setInt(1, teamID);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    printer.print(rs.getInt("playerID"));
                }
            }
        }
    }

    public void getPlayerContract(int playerID) throws SQLException {
        try (TablePrinter printer = new TablePrinter("personID", "teamID", "startDate", "endDate", "salary")) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StateOfContract WHERE personID = ?")) {
                pstmt.setInt(1, playerID);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    printer.print(rs.getInt("personID"),
                            rs.getInt("teamID"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getBigDecimal("salary"));
                }
            }
        }
    }

    public void getTeamSponsorships(int teamID) throws SQLException {
        try (TablePrinter printer = new TablePrinter("sponsorID", "teamID", "startDate", "endDate", "type")) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM T_Sponsorship WHERE teamID = ?")) {
                pstmt.setInt(1, teamID);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    printer.print(rs.getInt("sponsorID"),
                            rs.getInt("teamID"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("type"));
                }
            }
        }
    }

    public void getPlayerSponsorships(int playerID) throws SQLException {
        try (TablePrinter printer = new TablePrinter("sponsorID", "personID", "startDate", "endDate", "type")) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM P_Sponsorship WHERE personID = ?")) {
                pstmt.setInt(1, playerID);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    printer.print(rs.getInt("sponsorID"),
                            rs.getInt("personID"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("type"));
                }
            }
        }
    }

    public void getLeagueSponsorships(int leagueID) throws SQLException {
        try (TablePrinter printer = new TablePrinter("sponsorID", "leagueID", "startDate", "endDate", "type")) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM L_Sponsorship WHERE leagueID = ?")) {
                pstmt.setInt(1, leagueID);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    printer.print(rs.getInt("sponsorID"),
                            rs.getInt("personID"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("type"));
                }
            }
        }
    }

    // Check if a record exists in a table
    public boolean recordExists(String tableName, String columnName, Object columnValue) throws SQLException {
        String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, columnValue);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public boolean recordExists(String tableName, String columnName1, Object columnValue1, String columnName2, Object columnValue2) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName1 + " = ? AND " + columnName2 + " = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, columnValue1);
            pstmt.setObject(2, columnValue2);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public boolean isManagerAvailable(int managerID) throws SQLException {
        String query = "SELECT * FROM StateOfManage WHERE managerID = ? AND endDate IS NULL";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, managerID);
            ResultSet rs = pstmt.executeQuery();
            return !rs.next();
        }
    }

    private String generateInsertQuery(String tableName, List<String> columns) {
        String columnNames = String.join(", ", columns);
        String placeholders = String.join(", ", Collections.nCopies(columns.size(), "?"));
        return "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + placeholders + ")";
    }

    private String generateUpdateQuery(String tableName, String primaryKeyColumn, List<String> columns) {
        String assignments = columns.stream().map(column -> column + " = ?").collect(Collectors.joining(", "));
        return "UPDATE " + tableName + " SET " + assignments + " WHERE " + primaryKeyColumn + " = ?";
    }

    public void deleteRecord(String tableName, String primaryKeyColumn, Object primaryKeyValue) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, primaryKeyValue);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No record found with " + primaryKeyColumn + " = " + primaryKeyValue + " in table " + tableName);
            } else {
                System.out.println("Record deleted successfully from table " + tableName + " with " + primaryKeyColumn + " = " + primaryKeyValue);
            }
        }
    }

}
