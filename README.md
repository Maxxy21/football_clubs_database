# Football Clubs Database

The Football Clubs Database is a Java application that allows you to manage data related to football teams, players, contracts, sponsors, and more. It provides a user-friendly interface to interact with the database and perform various operations.

## Requirements

- Java Development Kit (JDK) 8 or later
- PostgreSQL database server
- PostgreSQL JDBC driver

## Setup

1. Clone the project repository from GitHub:
git clone https://github.com/Maxxy21/football-clubs-database.git


2. Ensure that you have the PostgreSQL JDBC driver (JAR file) in your classpath. You can download the JDBC driver from the [PostgreSQL JDBC Driver website](https://jdbc.postgresql.org/download.html).

3. Set up your PostgreSQL database server with a database for the application.

4. Update the database connection details in the `Database` class:

- Open the `Database.java` file located in the `src` folder.
- Modify the `connect()` method to provide the correct database name, username, and password.

5. Build the application:
   javac -d bin -sourcepath src src/Runner.java

6. Run the application:


## Usage

The application provides a command-line interface with various menu options. Here's a brief overview of the available features:

- Insert data: You can insert teams, players, contracts, sponsors, and more using the provided menu options.

- Query data: Retrieve information from the database, such as team players, player contracts, sponsorships, and more.

- Manage data: Update or delete existing records in the database using the appropriate menu options.

- Perform transactions: The application supports transactions, allowing you to commit or rollback changes as needed.

## Contributing

Contributions to the Football Clubs Database project are welcome! If you encounter any issues or have suggestions for improvements, feel free to open an issue or submit a pull request on the GitHub repository.

## License

This project is licensed under the [MIT License](LICENSE).

