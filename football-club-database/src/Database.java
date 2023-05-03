import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Database {

    private static final int TIMEOUT = 10;

    private Connection conn;

    /**
     * Establishes a connection to the specified database using the provided
     * username and password.
     *
     * @param database The name of the database to connect to
     * @param username The username
     * @param password The password
     * @return A Connection object representing the established connection
     * @throws SQLException If a database access error occurs
     */
    public Connection connect(String database, String username, String password) throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/" + database;
        conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * Closes the current database connection.
     *
     * @throws SQLException If a database access error occurs
     */
    public void disconnect() throws SQLException {
        if (conn != null)
            conn.close();
    }

    /**
     * Checks if the current database connection is active.
     *
     * @return true if the connection is active, false otherwise
     */
    public boolean isConnected() {
        boolean active = false;

        try {
            active = conn.isValid(TIMEOUT);
        } catch (SQLException ignored) {
        }

        return active;
    }

    /**
     * Commits the current transaction.
     *
     * @throws SQLException If a database access error occurs
     */
    public void commit() throws SQLException {
        conn.commit();
    }

    /**
     * Rolls back the current transaction.
     *
     * @throws SQLException If a database access error occurs
     */
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

    /**
     * Updates the position of a player based on their player ID and the new position provided.
     * If the player already has a position, prompts the user to confirm whether they want to update it.
     * If the user confirms, updates the player's position accordingly.
     *
     * @param playerID    the ID of the player whose position will be updated
     * @param newPosition the new position for the player
     * @throws SQLException if a database error occurs
     */
    public void updatePlayerPosition(int playerID, String newPosition) throws SQLException {
        String query = "SELECT pos.type " +
                "FROM Position pos " +
                "JOIN Plays pl ON pos.positionID = pl.positionID " +
                "WHERE pl.playerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String currentPosition = rs.getString("type");
                    System.out.println("The player already has a position: " + currentPosition);
                    System.out.println("Do you want to update the position? (yes/no)");

                    Scanner scanner = new Scanner(System.in);
                    String userInput = scanner.nextLine();

                    if (userInput.equalsIgnoreCase("yes")) {
                        String updateQuery = "UPDATE Plays SET positionID = " +
                                "(SELECT positionID FROM Position WHERE type = ?) WHERE playerID = ?";
                        try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                            updatePstmt.setString(1, newPosition);
                            updatePstmt.setInt(2, playerID);
                            int rowsAffected = updatePstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Player position updated successfully.");
                            } else {
                                System.out.println("Error updating player position.");
                            }
                        }
                    } else {
                        System.out.println("Player position not updated.");
                    }
                } else {
                    System.out.println("The player does not have a position.");
                }
            }
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
     * Updates or inserts a historicized entity in the specified table.
     *
     * @param tableName        The name of the table to update or insert the record into
     * @param primaryKeyColumn The name of the primary key column
     * @param primaryKeyValue  The primary key value of the record to update or insert
     * @param columns          The list of column names to update or insert
     * @param values           The list of corresponding values to update or insert
     * @throws SQLException If a database access error occurs
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
     * Retrieves and prints the position of a player given their player ID.
     *
     * @param playerID The ID of the player whose position will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getPlayerPosition(int playerID) throws SQLException {
        String query = "SELECT P.playerID, pr.firstName, pr.middleName, pr.lastName, t.name as teamName, pos.type " +
                "FROM Position pos " +
                "JOIN Plays pl ON pos.positionID = pl.positionID " +
                "JOIN Player P ON pl.playerID = P.playerID " +
                "JOIN Person pr ON P.playerID = pr.personID " +
                "JOIN StateOfPlaysFor S ON P.playerID = S.playerID " +
                "JOIN Team t ON S.teamID = t.teamID " +
                "WHERE pl.playerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the list of players for a given team ID.
     *
     * @param teamID The ID of the team whose players will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getTeamPlayers(int teamID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT P.playerID, pr.firstName, pr.middleName, pr.lastName " +
                "FROM StateOfPlaysFor S JOIN Player P ON S.playerID = P.playerID JOIN Person pr ON P.playerID = pr.personID " +
                "WHERE S.teamID = ?")) {
            pstmt.setInt(1, teamID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the contract information for a player given their player ID.
     *
     * @param playerID The ID of the player whose contract information will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getPlayerContract(int playerID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT S.*, pr.firstName, pr.middleName, pr.lastName " +
                "FROM StateOfContract S JOIN Player P ON S.personID = P.playerID JOIN Person pr ON P.playerID = pr.personID " +
                "WHERE S.personID = ?")) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the list of sponsorships for a given team ID.
     *
     * @param teamID The ID of the team whose sponsorships will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getTeamSponsorships(int teamID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT t.*, sp.name as sponsorName " +
                        "FROM T_Sponsorship t JOIN Sponsor sp ON t.sponsorID = sp.sponsorID " +
                        "WHERE t.teamID = ?")) {
            pstmt.setInt(1, teamID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the list of sponsorships for a given player ID.
     *
     * @param playerID The ID of the player whose sponsorships will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getPlayerSponsorships(int playerID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT p.*, sp.name as sponsorName, pr.firstName, pr.middleName, pr.lastName " +
                        "FROM P_Sponsorship p JOIN Sponsor sp ON p.sponsorID = sp.sponsorID " +
                        "JOIN Person pr ON p.personID = pr.personID " +
                        "WHERE p.personID = ?")) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the list of sponsorships for a given league ID.
     *
     * @param leagueID The ID of the league whose sponsorships will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getLeagueSponsorships(int leagueID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT L.sponsorID, S.name as sponsorName, L.leagueID, L.startDate, L.endDate, L.type " +
                "FROM L_Sponsorship L " +
                "INNER JOIN Sponsor S ON L.sponsorID = S.sponsorID WHERE L.leagueID = ?")) {
            pstmt.setInt(1, leagueID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
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

    /**
     * Deletes a record from the specified table given the primary key column and value.
     *
     * @param tableName The name of the table to delete the record from
     * @param primaryKeyColumn The name of the primary key column
     * @param primaryKeyValue The primary key value of the record to delete
     * @throws SQLException If a database access error occurs
     */
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
