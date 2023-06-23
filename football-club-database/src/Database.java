import java.sql.*;
import java.sql.Date;


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
    public void insertTeam(int teamID, String name, String city, int foundationYear, String[] kitColors) throws SQLException {
        boolean exists = recordExists("Team", "teamID", teamID);
        if (exists) {
            System.out.println("Team with ID " + teamID + " already exists.");
        } else {
            conn.setAutoCommit(false);
            try {
                String query = "INSERT INTO Team (teamID, name, city, foundationYear) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, teamID);
                    pstmt.setString(2, name);
                    pstmt.setString(3, city);
                    pstmt.setInt(4, foundationYear);
                    pstmt.executeUpdate();
                }

                // Insert kit colors and establish relationship with the team in HasKitColor table
                for (String color : kitColors) {
                    exists = recordExists("KitColors", "color", color);
                    if (exists) {
                        System.out.println("Kit color " + color + " already exists.");
                    } else {
                        query = "INSERT INTO KitColors (color) VALUES (?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                            pstmt.setString(1, color);
                            pstmt.executeUpdate();
                        }
                    }
                    query = "INSERT INTO HasKitColor (teamID, color) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setInt(1, teamID);
                        pstmt.setString(2, color);
                        pstmt.executeUpdate();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.printf("Could not disconnect properly from the database.%n%s%n", e.getMessage());
            } finally {
                conn.setAutoCommit(true);
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

    public void insertTSponsorship(int tSponsorshipID, int sponsorID, Date startDate, Date endDate, String type, int teamID) throws SQLException {
        try {
            conn.setAutoCommit(false);

            boolean exists = recordExists("T_Sponsorship", "tSponsorshipID", tSponsorshipID);
            if (exists) {
                System.out.println("T_Sponsorship with ID " + tSponsorshipID + " already exists.");
            } else {
                String query = "INSERT INTO T_Sponsorship (tSponsorshipID, sponsorID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?)";
                insertSponsorship(tSponsorshipID, sponsorID, startDate, endDate, type, query);

                query = "INSERT INTO HasTSponsorship (teamID, tSponsorshipID, sponsor) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, teamID);
                    pstmt.setInt(2, tSponsorshipID);
                    pstmt.setInt(3, sponsorID);
                    pstmt.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            System.out.printf("Could not disconnect properly from the database.%n%s%n", e.getMessage());
        } finally {
            assert conn != null;
            conn.setAutoCommit(true);
        }
    }

    private void insertSponsorship(int sponsorshipID, int sponsorID, Date startDate, Date endDate, String type, String query) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, sponsorshipID);
            pstmt.setInt(2, sponsorID);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setString(5, type);
            pstmt.executeUpdate();
        }
    }

    public void insertPSponsorship(int pSponsorshipID, int sponsorID, Date startDate, Date endDate, String type, int playerID) throws SQLException {
        try {
            conn.setAutoCommit(false);

            boolean exists = recordExists("P_Sponsorship", "pSponsorshipID", pSponsorshipID);
            if (exists) {
                System.out.println("P_Sponsorship with ID " + pSponsorshipID + " already exists.");
            } else {
                String query = "INSERT INTO P_Sponsorship (pSponsorshipID, sponsorID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?)";
                insertSponsorship(pSponsorshipID, sponsorID, startDate, endDate, type, query);

                query = "INSERT INTO HasPSponsorship (playerID, pSponsorshipID, sponsor) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, playerID);
                    pstmt.setInt(2, pSponsorshipID);
                    pstmt.setInt(3, sponsorID);
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            System.out.printf("Could not disconnect properly from the database.%n%s%n", e.getMessage());
        } finally {
            assert conn != null;
            conn.setAutoCommit(true);
        }
    }

    public void insertCoachingStaff(int coachingStaffID, String role, int yearsOfExperience) throws SQLException {
        boolean exists = recordExists("CoachingStaff", "coachingStaffID", coachingStaffID);
        if (exists) {
            System.out.println("Coaching staff with ID " + coachingStaffID + " already exists.");
        } else {
            String query = "INSERT INTO CoachingStaff (coachingStaffID, role, yearsOfExperience) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, coachingStaffID);
                pstmt.setString(2, role);
                pstmt.setInt(3, yearsOfExperience);
                pstmt.executeUpdate();
            }
        }
    }


    public void insertPlayerContract(int playerID, int teamID, Date startDate, Date endDate, double salary, int jerseyNumber, String position) throws SQLException {
        String query = "INSERT INTO PlayerContract (playerID, teamID, startDate, endDate, salary, jerseyNumber, position) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            pstmt.setInt(2, teamID);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setDouble(5, salary);
            pstmt.setInt(6, jerseyNumber);
            pstmt.setString(7, position);
            pstmt.executeUpdate();
        }
    }


    // Insertion of Coaching Staff Contract
    public void insertCoachingStaffContract(int coachingStaffID, int teamID, Date startDate, Date endDate, double salary) throws SQLException {
        String query = "INSERT INTO CoachingStaffContract (coachingStaffID, teamID, startDate, endDate, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, coachingStaffID);
            pstmt.setInt(2, teamID);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setDouble(5, salary);
            pstmt.executeUpdate();
        }
    }


    // Insertion of CaptainHistory
    public void insertCaptainHistory(int playerID, Date startDate, Date endDate, int teamID) throws SQLException {
        boolean exists = recordExists("CaptainHistory", playerID, startDate, endDate, teamID);
        if (exists) {
            System.out.println("Captain History for player ID " + playerID + " already exists.");
        } else {
            String query = "INSERT INTO CaptainHistory (playerID, startDate, endDate, teamID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, playerID);
                pstmt.setDate(2, startDate);
                pstmt.setDate(3, endDate);
                pstmt.setInt(4, teamID);
                pstmt.executeUpdate();
            }
        }
    }

    public void insertKitColor(String color, int teamID) throws SQLException {
        boolean exists = recordExists("KitColor", "color", color);
        conn.setAutoCommit(false);
        try {
            if (exists) {
                System.out.println("KitColor " + color + " already exists.");
            } else {
                String query = "INSERT INTO KitColor (color) VALUES (?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, color);
                    pstmt.executeUpdate();
                }
            }
            // After color has been inserted, establish relationship with team in HasKitColor table
            String query = "INSERT INTO HasKitColor (color, team) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, color);
                pstmt.setInt(2, teamID);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.printf("Could not disconnect properly from the database.%n%s%n", e.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void insertTrains(int coachingStaffID, int playerID) throws SQLException {
        String query = "INSERT INTO Trains (coachingStaffID, playerID) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, coachingStaffID);
            pstmt.setInt(2, playerID);
            pstmt.executeUpdate();
        }
    }


    public void transferPlayer(int playerID, int newTeamID, int jerseyNumber, String position, double salary, Date startDate, Date endDate) throws SQLException {
        // Archive the player's current contracts
        String query = "UPDATE PlayerContract SET endDate = CURRENT_DATE WHERE playerID = ? AND endDate IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            pstmt.executeUpdate();
        }

        // Then, insert a new contract for the new team with the specified start and end dates
        query = "INSERT INTO PlayerContract (playerID, teamID, startDate, endDate, jerseyNumber, position, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            pstmt.setInt(2, newTeamID);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setInt(5, jerseyNumber);
            pstmt.setString(6, position);
            pstmt.setDouble(7, salary);
            pstmt.executeUpdate();
        }
    }

    public void updatePerson(int personID, String firstName, String middleName, String lastName, Date dob, String nationality) throws SQLException {
        String query = "UPDATE Person SET firstName = COALESCE(?, firstName), middleName = COALESCE(?, middleName)," +
                " lastName = COALESCE(?, lastName), dob = COALESCE(?, dob), nationality = COALESCE(?, nationality) WHERE personID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, firstName);
            pstmt.setObject(2, middleName);
            pstmt.setObject(3, lastName);
            pstmt.setObject(4, dob);
            pstmt.setObject(5, nationality);
            pstmt.setInt(6, personID);

            pstmt.executeUpdate();
        }
    }

    public void updatePlayerContract(int contractID, Integer playerID, Integer teamID, Date startDate, Date endDate, Double salary, Integer jerseyNumber, String position) throws SQLException {
        String query = "UPDATE PlayerContract SET playerID = COALESCE(?, playerID), teamID = COALESCE(?, teamID)," +
                " startDate = COALESCE(?, startDate), endDate = COALESCE(?, endDate), salary = COALESCE(?, salary)," +
                " jerseyNumber = COALESCE(?, jerseyNumber), position = COALESCE(?, position) WHERE contractID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, playerID);
            pstmt.setObject(2, teamID);
            pstmt.setObject(3, startDate);
            pstmt.setObject(4, endDate);
            pstmt.setObject(5, salary);
            pstmt.setObject(6, jerseyNumber);
            pstmt.setObject(7, position);
            pstmt.setInt(8, contractID);

            pstmt.executeUpdate();
        }
    }

    public void updateCoachingStaffContract(int contractID, Integer coachingStaffID, Integer teamID, Date startDate, Date endDate, Double salary) throws SQLException {
        String query = "UPDATE CoachingStaffContract SET coachingStaffID = COALESCE(?, coachingStaffID), teamID = COALESCE(?, teamID)," +
                " startDate = COALESCE(?, startDate), endDate = COALESCE(?, endDate), salary = COALESCE(?, salary) WHERE contractID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, coachingStaffID);
            pstmt.setObject(2, teamID);
            pstmt.setObject(3, startDate);
            pstmt.setObject(4, endDate);
            pstmt.setObject(5, salary);
            pstmt.setInt(6, contractID);

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

    public void updateCoachingStaff(int coachingStaffID, String role, Integer yearsOfExperience) throws SQLException {
        String query = "UPDATE CoachingStaff SET role = COALESCE(?, role), yearsOfExperience = COALESCE(?, yearsOfExperience) WHERE coachingStaffID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, role);
            pstmt.setObject(2, yearsOfExperience);
            pstmt.setInt(3, coachingStaffID);

            pstmt.executeUpdate();
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


    public void deletePlayerContract(int contractID) throws SQLException {
        deleteRecord("PlayerContract", "playerContractID", contractID);
    }

    public void deleteCoachingStaffContract(int contractID) throws SQLException {
        deleteRecord("CoachingStaffContract", "contractID", contractID);
    }

    public void deleteCaptainHistory(Date startDate) throws SQLException {
        deleteRecord("CaptainHistory", "startDate", startDate);
    }

    public void deleteSponsor(int sponsorID) throws SQLException {
        deleteRecord("Sponsor", "sponsorID", sponsorID);
    }

    public void deleteTSponsorship(int tSponsorshipID) throws SQLException {
        deleteRecord("T-Sponsorship", "tSponsorshipID", tSponsorshipID);
    }

    public void deletePSponsorship(int pSponsorshipID) throws SQLException {
        deleteRecord("P-Sponsorship", "pSponsorshipID", pSponsorshipID);
    }


    /**
     * =================================================================== --
     * Retrieval Methods--
     * ================================================================
     */

    /**
     * Retrieves and prints the position of a player given their player ID.
     *
     * @param playerID The ID of the player whose position will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getPlayerPosition(int playerID) throws SQLException {
        String query = "SELECT P.playerID, pr.firstName, pr.middleName, pr.lastName, t.name as teamName, PC.position " +
                "FROM PlayerContract PC " +
                "JOIN Player P ON PC.playerID = P.playerID " +
                "JOIN Person pr ON P.playerID = pr.personID " +
                "JOIN Team T ON PC.teamID = T.teamID " +
                "WHERE P.playerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                TablePrinter tp = new TablePrinter(rs);
                tp.close();
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
                "FROM PlayerContract PC JOIN Player P ON PC.playerID = P.playerID JOIN Person pr ON P.playerID = pr.personID " +
                "WHERE PC.teamID = ?")) {
            pstmt.setInt(1, teamID);
            try (ResultSet rs = pstmt.executeQuery()) {
                TablePrinter tp = new TablePrinter(rs);
                tp.close();
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
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT PC.*, pr.firstName, pr.middleName, pr.lastName " +
                "FROM PlayerContract PC JOIN Player P ON PC.playerID= P.playerID JOIN Person pr ON P.playerID = pr.personID " +
                "WHERE PC.playerID= ?")) {
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
                "SELECT ts.*, sp.name as sponsorName " +
                        "FROM HasTSponsorship hs JOIN T_Sponsorship ts ON hs.tSponsorshipID = ts.tSponsorshipID " +
                        "JOIN Sponsor sp ON ts.sponsor = sp.sponsorID " +
                        "WHERE hs.teamID = ?")) {
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
                "SELECT ps.*, sp.name as sponsorName, pr.firstName, pr.middleName, pr.lastName " +
                        "FROM HasPSponsorship hsp JOIN P_Sponsorship ps ON hsp.pSponsorshipID = ps.pSponsorshipID " +
                        "JOIN Sponsor sp ON ps.sponsor = sp.sponsorID " +
                        "JOIN Person pr ON hsp.playerID = pr.personID " +
                        "WHERE hsp.playerID = ?")) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the list of teams that a player has played for given their player ID.
     *
     * @param playerID The ID of the player whose teams will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getPlayerTeams(int playerID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT T.* " +
                        "FROM PlayerContract PC JOIN Team T ON PC.teamID = T.teamID " +
                        "WHERE PC.playerID = ?")) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                new TablePrinter(rs);
            }
        }
    }

    /**
     * Retrieves and prints the list of teams that a manager has managed given their manager ID.
     *
     * @param managerID The ID of the manager whose teams will be retrieved
     * @throws SQLException If a database access error occurs
     */
    public void getManagerTeams(int managerID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT T.* " +
                        "FROM CoachingStaffContract CC JOIN Team T ON CC.teamID = T.teamID " +
                        "WHERE CC.coachingStaffID = ? AND CC.role = 'manager'")) {
            pstmt.setInt(1, managerID);
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

    //check if a record exists for CaptainHistory table
    public boolean recordExists(String table, int playerID, Date startDate, Date endDate, int teamID) throws SQLException {
        String query = "SELECT * FROM " + table + " WHERE playerID = ? AND startDate = ? AND endDate = ? AND teamID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerID);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);
            pstmt.setInt(4, teamID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
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

    public boolean isUnderContract(int personID, String contractTable) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + contractTable + " WHERE coachingStaffID = ? OR playerID = ? AND endDate >= CURRENT_DATE";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, personID);
            pstmt.setInt(2, personID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }


}
