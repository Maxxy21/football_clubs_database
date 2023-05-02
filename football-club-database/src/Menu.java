import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public abstract class Menu {

    private static final Scanner sc = new Scanner(System.in);
    protected static final Database db = new Database();

    public abstract State show() throws Exception;

    /**
     * @return True if the user wants to quit the program, false otherwise.
     */
    protected static State processCommand(String question, Interpreter interpreter) {
        State state = State.Valid;

        System.out.println(question);
        do {
            String cmd = sc.nextLine();
            if (cmd.equals("q"))
                return State.Quit;
            else if (cmd.equals("b"))
                return State.Back;
            try {
                state = interpreter.resolve(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (state == State.Invalid)
                System.out.println("Invalid command.");
        } while (state == State.Invalid);
        return state;
    }

    protected static String nextString() {
        return sc.nextLine();
    }

    protected static Integer nextInteger() {
        Integer value = null;
        try {
            value = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ignored) {
        }

        return value;
    }

    protected double nextDouble() {
        double value = 0.0;
        try {
            value = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException ignored) {
        }
        return value;
    }

    protected BigDecimal nextBigDecimal() {
        BigDecimal d = null;

        try {
            d = new BigDecimal(sc.nextLine());
        } catch (NumberFormatException ignored) {
        }

        return d;
    }

    protected boolean nextBoolean() {
        boolean value = false;
        try {
            value = Boolean.parseBoolean(sc.nextLine());
        } catch (NumberFormatException ignored) {
        }
        return value;
    }

    protected Date nextDate() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = new Date(df.parse(sc.nextLine()).toInstant().toEpochMilli());
        } catch (ParseException ignored) {
        }

        return date;
    }

}
