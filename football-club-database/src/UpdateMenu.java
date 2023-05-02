import java.sql.Date;
import java.sql.SQLException;

public class UpdateMenu extends Menu {

    private static UpdateMenu INSTANCE;

    public static UpdateMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new UpdateMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nWhich table would you like to update?\n" +
                "[1] Player.\n" +
                "[2] Manager of a team.\n" +
                "[3] Sponsor.\n" +
                "[4] Player's Position.\n" +
                "[5] Assign a new captain.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    updatePlayer();
                    break;
                case "2":
                    updateManagerOfTeam();
                    break;
                case "3":
                    updateSponsor();
                    break;
                case "4":
                    updatePlayerPosition();
                    break;
                case "5":
                    assignNewCaptain();
                    break;
                default:
                    return State.Invalid;
            }
            return State.Valid;
        });

    }

    private static void updateSponsor() throws SQLException {
        System.out.print("Enter Sponsor ID: ");
        int sponsorID = nextInteger();

        System.out.print("Enter the new name (leave empty if not changing):");
        String name = nextString();
        name = name.isEmpty() ? null : name;

        System.out.print("Enter the new industry (leave empty if not changing):");
        String industry = nextString();
        industry = industry.isEmpty() ? null : industry;

        System.out.print("Enter the new foundation year (leave empty if not changing):");
        String foundationYearStr = nextString();
        Integer foundationYear = foundationYearStr.isEmpty() ? null : Integer.parseInt(foundationYearStr);

        db.updateSponsor(sponsorID, name, industry, foundationYear);
        System.out.println("Sponsor updated successfully.");
    }

    private static void updatePlayer() throws SQLException {
        System.out.print("Enter Player ID: ");
        int playerID = nextInteger();

        System.out.print("Enter the new starting XI status (leave empty if not changing):");
        String startingXIStr = nextString();
        Boolean startingXI = startingXIStr.isEmpty() ? null : Boolean.parseBoolean(startingXIStr);

        System.out.print("Enter the new number of appearances (leave empty if not changing):");
        String appearancesStr = nextString();
        Integer appearances = appearancesStr.isEmpty() ? null : Integer.parseInt(appearancesStr);

        db.updatePlayer(playerID, startingXI, appearances);
        System.out.println("Player updated successfully.");
    }

    private static void updateManager() throws SQLException {
        System.out.print("Enter Manager ID: ");
        int managerID = nextInteger();

        System.out.print("Enter the new years of experience (leave empty if not changing):");
        String yearsOfExperienceStr = nextString();
        Integer yearsOfExperience = yearsOfExperienceStr.isEmpty() ? null : Integer.parseInt(yearsOfExperienceStr);

        db.updateManager(managerID, yearsOfExperience);
        System.out.println("Manager updated successfully.");
    }

    private static void updatePlayerPosition() throws SQLException {
        System.out.print("Enter player ID: ");
        int playerID = nextInteger();

        System.out.print("Enter old position ID: ");
        int oldPositionID = nextInteger();

        System.out.print("Enter new position ID: ");
        int newPositionID = nextInteger();

        db.updatePlayerPosition(playerID, oldPositionID, newPositionID);
        System.out.println("Player position relationship updated successfully.");
    }


    private static void updateManagerOfTeam() throws SQLException {
        System.out.print("Enter the teamID: ");
        int teamID = nextInteger();

        System.out.print("Enter the new managerID: ");
        int newManagerID = nextInteger();

        if (!db.recordExists("Team", "teamID", teamID)) {
            System.out.println("Team with ID " + teamID + " does not exist.");
            return;
        }

        if (!db.recordExists("Manager", "managerID", newManagerID)) {
            System.out.println("Manager with ID " + newManagerID + " does not exist.");
            return;
        }

        if (!db.isManagerAvailable(newManagerID)) {
            System.out.println("Manager with ID " + newManagerID + " is already managing another team.");
            return;
        }

        System.out.print("Enter the start date of the new manager (yyyy-MM-dd): ");
        String startDateStr = nextString();
        Date startDate = Date.valueOf(startDateStr);

        db.updateManagerOfTeam(newManagerID, teamID, startDate);
        System.out.println("Manager of team updated successfully.");
    }

    private static void assignNewCaptain() throws SQLException {
        System.out.print("Enter team ID: ");
        int teamID = nextInteger();

        System.out.print("Enter new captain ID: ");
        int captainID = nextInteger();

        System.out.print("Enter captain since (yyyy-MM-dd): ");
        Date captainSince = Date.valueOf(nextString());

        System.out.print("Enter seniority: ");
        int seniority = nextInteger();

        db.updateInsertCaptain(captainID, captainSince, seniority, teamID);
        System.out.println("Captain updated successfully.");
    }


}
