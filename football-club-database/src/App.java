/*

A Database for a Space Agency
Introduction to Databases project

Gioele De Vitti, Student ID 17693
gdevitti@unibz.it

*/

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("--- Space Agency Database App ---");
        Class.forName("org.postgresql.Driver");
        MenuTerminal.instance().show();
    }


}
