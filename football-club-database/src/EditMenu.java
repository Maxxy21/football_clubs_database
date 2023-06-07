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
                "[4] Transfer.\n" +
                "[b] Back to the main menu.\n" +
                "[q] Quit the program.";

        return processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    return InsertMenu.instance().show();
                case "2":
                    return DeleteMenu.instance().show();
                case "3":
                    return UpdateMenu.instance().show();
                case "4":
                    return TransferMenu.instance().show(); // Added this line
                default:
                    return State.Invalid;
            }
        });
    }
}
