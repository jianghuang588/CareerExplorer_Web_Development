// This program creates the example FLIGHT database (db) from scratch.
// If the FLIGHT db already exists, it is first deleted, and then created
// afresh. The assumption before running the program is that the
// H2 database server is already running. To do so, execute
//       java -cp h2*.jar org.h2.tools.Server -tcp -pg
// in the directory where the h2.jar is located.

// java.sql has many objects like Statement, Connection, ResultSet
// that will be useful for accessing the database from java.

import java.sql.*;

public class MakeFlightDB {

    static Statement statement;         

    public static void main (String[] argv)
    {
        try {
	    // The first step is to load the driver and use it to open
	    // a connection to the H2 server (that should be running).
            Class.forName ("org.h2.Driver");
            Connection conn = DriverManager.getConnection (
   	        "jdbc:h2:~/Desktop/myservers/databases/flight", 
		"sa", 
		""
            );

	    // If the connection worked, we'll reach here (otherwise an 
	    // exception is thrown.

	    // Now make a statement, which is the object used to issue
	    // queries.
	    statement = conn.createStatement ();
	    
	    // The passenger table:
	    makePassengerTable ();
	    printTable ("PASSENGER", 4);

	    // The flight table:
	    makeFlightTable ();
	    printTable ("FLIGHT", 4);

	    // The airport table:
	    makeAirportTable ();
	    printTable ("AIRPORT", 3);

	    // The crew table:
	    makeCrewTable ();
	    printTable ("CREW", 2);

	    // The employee table:
	    makeEmployeeTable ();
	    printTable ("EMPLOYEE", 5);

	    // Close the connection, and we're done.
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void printTable (String tableName, int numColumns)
	throws SQLException
    {
	// Build the SELECT query string:
	String sql = "SELECT * FROM " + tableName;

	// Execute at the database, which returns rows that are
	// placed into the ResultSet object.
	ResultSet rs = statement.executeQuery (sql);

	// Now extract the results from ResultSet
	System.out.println ("\nRows from " + tableName + ":");
	while (rs.next()) {
	    String row = "Row: ";
	    for (int i=1; i<=numColumns; i++) {
		String s = rs.getString (i);      
		// One could get an int column into an int variable.
		row += " " + s;
	    }
	    System.out.println (row);
	}
    }


    static void makePassengerTable ()
	throws SQLException
    {
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS PASSENGER";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE PASSENGER (PID INT PRIMARY KEY, NAME VARCHAR(25), FLIGHT_ID INT, MILES INT)";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO PASSENGER VALUES (1, 'Louis', 17,2000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (2, 'Ella', 63,45000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (3, 'Miles', 15,1600)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (4, 'Billie', 15,7700)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (5, 'Duke', 12,55000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (6, 'Charlie', 11,200)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (7, 'Bix', 17,64500)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO PASSENGER VALUES (8, 'Sarah', 12,1570)";
	statement.executeUpdate (sql);
    }


    static void makeFlightTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS FLIGHT";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE FLIGHT (FLIGHT_ID INT PRIMARY KEY, FLIGHT_NUM VARCHAR(10), START_APT VARCHAR(3), END_APT VARCHAR(3))";
	statement.executeUpdate (sql);

	sql = "INSERT INTO FLIGHT VALUES (11, 'F616', 'DCA','LGA')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO FLIGHT VALUES (12, 'F71', 'LGA','DCA')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO FLIGHT VALUES (15, 'F335', 'DCA','JFK')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO FLIGHT VALUES (17, 'F338', 'JFK','DCA')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO FLIGHT VALUES (63, 'F15', 'DCA','JFK')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO FLIGHT VALUES (22, 'F27', 'DCA','BOS')";
	statement.executeUpdate (sql);
    }


    static void makeAirportTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS AIRPORT";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE AIRPORT (APT VARCHAR(3), NAME VARCHAR(20), CITY VARCHAR(20))";
	statement.executeUpdate (sql);

	sql = "INSERT INTO AIRPORT VALUES ('DCA', 'National','Washington')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO AIRPORT VALUES ('LGA', 'La Guardia','New York')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO AIRPORT VALUES ('JFK', 'Kennedy','New York')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO AIRPORT VALUES ('BOS', 'Logan','Boston')";
	statement.executeUpdate (sql);
    }


    static void makeCrewTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS CREW";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE CREW (EMP_ID VARCHAR(11), FLIGHT_ID INT)";
	statement.executeUpdate (sql);

	sql = "INSERT INTO CREW VALUES ('011-44-2233', 11)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('313-62-7711', 11)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('442-11-3313', 12)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('722-55-1139', 15)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('011-44-2223', 63)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('011-44-2223', 17)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('313-62-7711', 17)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO CREW VALUES ('442-11-3313', 63)";
	statement.executeUpdate (sql);
    }


    static void makeEmployeeTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS EMPLOYEE";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE EMPLOYEE (NAME VARCHAR(20), EMP_ID VARCHAR(11), POSITION VARCHAR(20), SALARY INT, MGR_ID VARCHAR(11))";
	statement.executeUpdate (sql);

	sql = "INSERT INTO EMPLOYEE VALUES ('Thelonius', '011-44-2233', 'Co-Pilot', 133000, '313-62-7711')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO EMPLOYEE VALUES ('Dinah', '313-62-7711', 'Pilot', 159000, '334-56-9876')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO EMPLOYEE VALUES ('Django', '442-11-3313', 'Cabin staff', 45000, '313-62-7711')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO EMPLOYEE VALUES ('Benny', '722-55-1139', 'Engineer', 86000, '334-56-9876')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO EMPLOYEE VALUES ('Marian', '221-44-8883', 'AT Controller', 53000, '722-55-1139')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO EMPLOYEE VALUES ('Sonny', '119-72-3131', 'Sales', 42000, '')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO EMPLOYEE VALUES ('Quincy', '334-56-9876', 'CEO', 42960000, '334-56-9876')";
	statement.executeUpdate (sql);
    }
    
}
