public class CommitMenu extends Menu {

    private final Menu nextMenu;

    public CommitMenu() {
        this(null);
    }

    public CommitMenu(Menu nextMenu) {
        this.nextMenu = nextMenu;
    }

    @Override
    public State show() throws Exception {
        String question = "\nWould you like to commit or rollback the current transaction?\n" +
                "[1] Commit.\n" +
                "[2] Rollback.\n" +
                "[b] Back the main menu.\n" +
                "[q] Quit the program.";

        State state = processCommand(question, (cmd) -> {
            switch (cmd) {
                case "1":
                    db.commit();
                    break;
                case "2":
                    db.rollback();
                    break;
                default:
                    return State.Invalid;
            }
            return State.Valid;
        });
        if (state == State.Valid && nextMenu != null)
            return nextMenu.show();
        return state;
    }

}
