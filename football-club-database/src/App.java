/*

A Database for a Space Agency
Introduction to Databases project

Gioele De Vitti, Student ID 17693
gdevitti@unibz.it

*/

import java.util.Scanner;

public class App {

    static {
        try {
            // Initialize driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver for PostgreSQL not found.");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--- Space Agency Database App ---");
        MenuTerminal.instance().show();
    }
}