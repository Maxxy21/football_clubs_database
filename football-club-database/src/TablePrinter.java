import java.util.Arrays;

public class TablePrinter implements AutoCloseable {

    private final int nColumns;
    private final String hLine;
    private final int[] colWidths;

    public TablePrinter(String... columns) {
        nColumns = columns.length;
        if (nColumns == 0)
            throw new IllegalArgumentException("Tables with zero columns are not allowed.");

        colWidths = new int[nColumns];
        Arrays.fill(colWidths, 0);
        for (int i = 0; i < nColumns; i++) {
            colWidths[i] = Math.max(colWidths[i], columns[i].length());
            System.out.print(padRight(columns[i], colWidths[i]) + " | ");
        }
        System.out.println();

        hLine = repeat(Arrays.stream(colWidths).sum() + nColumns * 3);
        System.out.println(hLine);
    }

    public void print(Object... values) {
        if (values.length != nColumns)
            throw new IllegalArgumentException("Number of values inserted does not correspond to number of columns.");
        for (int i = 0; i < nColumns; i++) {
            String value = String.valueOf(values[i]);
            System.out.print(padRight(value, colWidths[i]) + " | ");
        }
        System.out.println();
    }

    @Override
    public void close() {
        System.out.println(hLine);
    }

    private static String repeat(int times) {
        return "-".repeat(Math.max(0, times));
    }

    private static String padRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
}
