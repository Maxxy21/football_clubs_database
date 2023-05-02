public class TablePrinter implements AutoCloseable {

    private final int nColumns;
    private final String hLine;

    public TablePrinter(String... columns) {
        nColumns = columns.length;
        if (nColumns == 0)
            throw new IllegalArgumentException("Tables with zero columns are not allowed.");

        int width = 0;
        for (String c : columns) {
            String s = c + " | ";
            width += s.length();
            System.out.print(s);
        }
        System.out.println();

        hLine = repeat("-", width);
        System.out.println(hLine);
    }

    public void print(Object... values) {
        if (values.length != nColumns)
            throw new IllegalArgumentException("Number of values inserted does not correspond to number of columns.");
        for (Object v : values)
            System.out.print(v + " | ");
        System.out.println();
    }

    @Override
    public void close() {
        System.out.println(hLine);
    }

    private static String repeat(String str, int times) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < times; i++)
            builder.append(str);

        return builder.toString();
    }

}
