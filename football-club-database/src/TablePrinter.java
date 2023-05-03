import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

public class TablePrinter implements AutoCloseable {

    private final String hLine;

    /**
     * Constructs a TablePrinter object that prints the contents of the given ResultSet in a tabular format.
     * The table consists of column names, horizontal lines, and data rows. The method also automatically
     * calculates the appropriate column widths based on the length of the column names and the data
     * within the columns.
     *
     * @param resultSet The ResultSet containing the data to be printed in a tabular format
     * @throws SQLException If a database access error occurs
     */
    public TablePrinter(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int nColumns = metaData.getColumnCount();

        int[] colWidths = new int[nColumns];
        Arrays.fill(colWidths, 0);
        for (int i = 1; i <= nColumns; i++) {
            colWidths[i - 1] = Math.max(colWidths[i - 1], metaData.getColumnName(i).length());
            System.out.print(padRight(metaData.getColumnName(i), colWidths[i - 1]) + " | ");
        }
        System.out.println();

        hLine = repeat(Arrays.stream(colWidths).sum() + nColumns * 3);
        System.out.println(hLine);

        // Print rows
        while (resultSet.next()) {
            for (int i = 1; i <= nColumns; i++) {
                String value = resultSet.getString(i);
                System.out.print(padRight(value, colWidths[i - 1]) + " | ");
            }
            System.out.println();
        }

        System.out.println(hLine);
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
