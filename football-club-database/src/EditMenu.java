/*
Lets the user edit the following tables:
- Mission, being planned by at least 1 Organization and to which at least 1 research team contributes
- Spacecraft, being sent for 1 Mission
- ImageSet, being captured for 1 Spacecraft
- Organization
- ResearchTeam, contributing to at least 1 Mission
- TAccess
- Contribute
 */
public class EditMenu extends Menu {

    private static EditMenu INSTANCE;

    public static EditMenu instance() {
        if (INSTANCE == null)
            INSTANCE = new EditMenu();
        return INSTANCE;
    }

    @Override
    public State show() {
        String question = "\nWhich operation would you like to perform?\n" +
                "[1] Insert.\n" +
                "[2] Delete.\n" +
                "[3] Update.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    return InsertMenu.instance().show();
                case "2":
                    return DeleteMenu.instance().show();
                case "3":
                    return UpdateMenu.instance().show();
                default:
                    return State.Invalid;
            }
        });
    }

}
