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
    public void insertCoachingStaff(int coachingStaffID, String role) throws SQLException {
        boolean exists = recordExists("CoachingStaff", "coachingStaffID", coachingStaffID);
        if (exists) {
            System.out.println("Coaching staff with ID " + coachingStaffID + " already exists.");
        } else {
            String query = "INSERT INTO CoachingStaff (coachingStaffID, role) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, coachingStaffID);
                pstmt.setString(2, role);
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

    public void insertTSponsorship(int tSponsorshipID, Date startDate, Date endDate, String type) throws SQLException {
        boolean exists = recordExists("T-Sponsorship", "tSponsorshipID", tSponsorshipID);
        if (exists) {
            System.out.println("T-Sponsorship with ID " + tSponsorshipID + " already exists.");
        } else {
            String query = "INSERT INTO T_Sponsorship (tSponsorshipID, startDate, endDate, type) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, tSponsorshipID);
                pstmt.setDate(2, startDate);
                pstmt.setDate(3, endDate);
                pstmt.setString(4, type);
                pstmt.executeUpdate();
            }
        }
    }



    public void insertPSponsorship(int pSponsorshipID, Date startDate, Date endDate, String type) throws SQLException {
        boolean exists = recordExists("P-Sponsorship", "pSponsorshipID", pSponsorshipID);
        if (exists) {
            System.out.println("P-Sponsorship with ID " + pSponsorshipID + " already exists.");
        } else {
            String query = "INSERT INTO P_Sponsorship (pSponsorshipID, startDate, endDate, type) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, pSponsorshipID);
                pstmt.setDate(2, startDate);
                pstmt.setDate(3, endDate);
                pstmt.setString(4, type);
                pstmt.executeUpdate();
            }
        }
    }

    public void insertManager(int managerID, String firstName, String middleName, String lastName, Date dob, String nationality, int yearsOfExperience) throws SQLException {
        insertPerson(managerID, firstName, middleName, lastName, dob, nationality);
        String query = "INSERT INTO Manager (managerID, yearsOfExperience) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, managerID);
            pstmt.setInt(2, yearsOfExperience);
            pstmt.executeUpdate();
        }
    }

    public void insertCoachingStaff(int coachingStaffID, String firstName, String middleName, String lastName, Date dob, String nationality, String role) throws SQLException {
        insertPerson(coachingStaffID, firstName, middleName, lastName, dob, nationality);
        String query = "INSERT INTO CoachingStaff (coachingStaffID, role) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, coachingStaffID);
            pstmt.setString(2, role);
            pstmt.executeUpdate();
        }
    }

    public void insertPlayer(int playerID, String firstName, String middleName, String lastName, Date dob, String nationality, boolean startingXI, int appearances) throws SQLException {
        insertPerson(playerID, firstName, middleName, lastName, dob, nationality);
        String query = "INSERT INTO Player (playerID, startingXI, appearances) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            pstmt.setBoolean(2, startingXI);
            pstmt.setInt(3, appearances);
            pstmt.executeUpdate();
        }
    }

    public void insertContract(int contractID, int personID, int teamID, Date startDate, Date endDate, double salary, Integer jerseyNumber, String position) throws SQLException {
        boolean exists = recordExists("Contract", "contractID", contractID);
        if (exists) {
            System.out.println("Contract with ID " + contractID + " already exists.");
        } else {
            String query = "INSERT INTO Contract (contractID, personID, teamID, startDate, endDate, salary, jerseyNumber, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, contractID);
                pstmt.setInt(2, personID);
                pstmt.setInt(3, teamID);
                pstmt.setDate(4, startDate);
                pstmt.setDate(5, endDate);
                pstmt.setDouble(6, salary);

                if (jerseyNumber == null) {
                    pstmt.setNull(7, Types.INTEGER);
                } else {
                    pstmt.setInt(7, jerseyNumber);
                }

                if (position == null) {
                    pstmt.setNull(8, Types.VARCHAR);
                } else {
                    pstmt.setString(8, position);
                }

                pstmt.executeUpdate();
            }
        }
    }

    public void transferManager(int managerID, int newTeamID, double salary, Date startDate, Date endDate) throws SQLException {
        // First, archive the manager's current contract
        String sql = "UPDATE Contract SET endDate = CURRENT_DATE WHERE personID = ? AND endDate IS NULL";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerID);
            ps.executeUpdate();
        }

        // Then, insert a new contract for the new team with new start and end dates
        sql = "INSERT INTO Contract (personID, teamID, salary, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerID);
            ps.setInt(2, newTeamID);
            ps.setDouble(3, salary);
            ps.setDate(4, startDate);
            ps.setDate(5, endDate);
            ps.executeUpdate();
        }
    }


    // Insertion of CaptainHistory
    public void insertCaptainHistory(int playerID, String role, Date startDate, Date endDate) throws SQLException {
        String query = "INSERT INTO CaptainHistory (playerID, startDate,  endDate) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);
            pstmt.executeUpdate();
        }
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


    public void archivePlayer(int playerID) throws SQLException {
        // First, archive the player's contracts
        String sql = "UPDATE Contract SET endDate = CURRENT_DATE WHERE playerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerID);
            ps.executeUpdate();
        }

        // Then, archive the player
        sql = "UPDATE Player SET endDate = CURRENT_DATE WHERE playerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerID);
            ps.executeUpdate();
        }
    }

    // Method to transfer a player to a new team and update their contracts
    public void transferPlayer(int playerID, int newTeamID, int jerseyNumber, String position, double salary) throws SQLException {
        // First, archive the player's current contracts
        String sql = "UPDATE Contract SET endDate = CURRENT_DATE WHERE playerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerID);
            ps.executeUpdate();
        }

        // Then, insert a new contract for the new team with a new start date
        sql = "INSERT INTO Contract (playerID, teamID, startDate, jerseyNumber, position, salary) VALUES (?, ?, CURRENT_DATE, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerID);
            ps.setInt(2, newTeamID);
            ps.setInt(3, jerseyNumber);
            ps.setString(4, position);
            ps.setDouble(5, salary);
            ps.executeUpdate();
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

    public void deleteContract(int contractID) throws SQLException {
        deleteRecord("Contract", "contractID", contractID);
    }

    public void deleteCaptainHistory(Date startDate) throws SQLException {
        deleteRecord("CaptainHistory", "startDate", startDate);
    }

    public void deleteSponsor(int sponsorID) throws SQLException {
        deleteRecord("Sponsor", "sponsorID", sponsorID);
    }

    public void deleteTSponsorship(int tSponsorshipID) throws SQLException {
        deleteRecord("T_Sponsorship", "tSponsorshipID", tSponsorshipID);
    }

    public void deletePSponsorship(int pSponsorshipID) throws SQLException {
        deleteRecord("P_Sponsorship", "pSponsorshipID", pSponsorshipID);
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
        String query = "SELECT P.playerID, pr.firstName, pr.middleName, pr.lastName, t.name as teamName, C.position " +
                "FROM Contract C " +
                "JOIN Player P ON C.personID = P.playerID " +
                "JOIN Person pr ON P.playerID = pr.personID " +
                "JOIN Team T ON C.teamID = T.teamID " +
                "WHERE P.playerID = ?";

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
                "FROM Contract C JOIN Player P ON C.personID = P.playerID JOIN Person pr ON P.playerID = pr.personID " +
                "WHERE C.teamID = ?")) {
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
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT C.*, pr.firstName, pr.middleName, pr.lastName " +
                "FROM Contract C JOIN Player P ON C.personID = P.playerID JOIN Person pr ON P.playerID = pr.personID " +
                "WHERE C.personID = ?")) {
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
     * @param tableName        The name of the table to delete the record from
     * @param primaryKeyColumn The name of the primary key column
     * @param primaryKeyValue  The primary key value of the record to delete
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

    public void deleteRecord(String tableName, String primaryKeyColumn1, Object primaryKeyValue1, String primaryKeyColumn2, Object primaryKeyValue2) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn1 + " = ? AND " + primaryKeyColumn2 + " = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, primaryKeyValue1);
            pstmt.setObject(2, primaryKeyValue2);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No record found with " + primaryKeyColumn1 + " = " + primaryKeyValue1 + " and " + primaryKeyColumn2 + " = " + primaryKeyValue2 + " in table " + tableName);
            } else {
                System.out.println("Record deleted successfully from table " + tableName + " with " + primaryKeyColumn1 + " = " + primaryKeyValue1 + " and " + primaryKeyColumn2 + " = " + primaryKeyValue2);
            }
        }
    }

}
