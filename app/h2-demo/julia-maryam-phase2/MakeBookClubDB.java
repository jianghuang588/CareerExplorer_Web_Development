// This program creates the example FLIGHT database (db) from scratch.
// If the FLIGHT db already exists, it is first deleted, and then created
// afresh. The assumption before running the program is that the
// H2 database server is already running. To do so, execute
//       java -cp h2*.jar org.h2.tools.Server -tcp -pg
// in the directory where the h2.jar is located.

// java.sql has many objects like Statement, Connection, ResultSet
// that will be useful for accessing the database from java.

import java.sql.*;

public class MakeBookClubDB {

    static Statement statement;         

    public static void main (String[] argv)
    {
        try {
	    // The first step is to load the driver and use it to open
	    // a connection to the H2 server (that should be running).
            Class.forName ("org.h2.Driver");
            Connection conn = DriverManager.getConnection (
   	        "jdbc:h2:~/Desktop/myservers/databases/bookclub", 
		"sa", 
		""
            );

	    // If the connection worked, we'll reach here (otherwise an 
	    // exception is thrown.

	    // Now make a statement, which is the object used to issue
	    // queries.
	    statement = conn.createStatement ();
	    
	    // The user table:
	    makeUserTable ();
	    printTable ("USER", 3);

	    // The bookclub table:
	    makeBookClubTable ();
	    printTable ("BOOKCLUB", 1);

	    // The current book table:
	    makeCurrentBookTable ();
	    printTable ("CURRENTBOOK", 1);

	    // The history table:
	    makeHistoryTable ();
	    printTable ("HISTORY", 2);
        
        // The next book table:
        makeNextBookTable ();
        printTable ("NEXTBOOK", 3);


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


    static void makeUserTable ()
	throws SQLException
    {
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS USER";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE USER (UID INT PRIMARY KEY, USERNAME VARCHAR(25), PASSWORD VARCHAR(12), FIRSTNAME VARCHAR(25), LASTNAME VARCHAR(25), EMAIL VARCHAR(50), FAVORITEBOOK VARCHAR(100), BCNO INT, YEAR INT(4), BOOKPROGRESS INT(4))";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO USER VALUES (1, 'pablojones', 12345678, 'pablo', 'jones', 'pablo@gateway.edu', 'the three musketeers', 1, 2018, 0)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO USER VALUES (2, 'jilljones', 12345678, 'jill', 'jones', 'jill@gateway.edu', 'fight club', 1, 2017, 100)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO USER VALUES (3, 'lilith', 12345678, 'lilith', 'st. peete', 'stpeete@gateway.edu', 'prideandprejudice', 1, 2017, 200)";
	statement.executeUpdate (sql);
    sql = "INSERT INTO USER VALUES (4, 'admin', '12345678', 'admin', 'st. jones', 'admin@admin.com', 'the instruction manual', 1, 2018, 0)";
	statement.executeUpdate (sql);
    }


    static void makeBookClubTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS BOOKCLUB";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE BOOKCLUB (BCNO INT PRIMARY KEY, BCNAME VARCHAR(100), CURRENTBOOK VARCHAR(100), YEARSTARTED INT(4))";
	statement.executeUpdate (sql);

	sql = "INSERT INTO BOOKCLUB VALUES (1, 'gateway bookclub', 'eat pray love', 2017)";
	statement.executeUpdate (sql);
    }


    static void makeCurrentBookTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS CURRENTBOOK";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE CURRENTBOOK (NAME VARCHAR(100) PRIMARY KEY, AUTHOR VARCHAR(50), DESCRIPTION VARCHAR(300), TOTALPAGES INT(4), MEETINGDATE VARCHAR(10), MEETINGLOC VARCHAR(50))";
	statement.executeUpdate (sql);

	sql = "INSERT INTO CURRENTBOOK VALUES ('eat pray love', 'elizabeth gilbert', 'upon discovering shes fallen out of love with her husband, a woman goes on a journey to find out who she really loves -- herself.', 352, 07/02/19, 'SEH room 4675')";
    statement.executeUpdate (sql);
    }


    static void makeHistoryTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS HISTORY";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE HISTORY (BOOKNAME VARCHAR(100) PRIMARY KEY, AUTHOR VARCHAR(100), BCNO INT, MEETINGDATE VARCHAR(10), RATING INT)";
	statement.executeUpdate (sql);

	sql = "INSERT INTO HISTORY VALUES ('the help', 'kathryn stockett', 1, '04/20/2019', 5)";
	statement.executeUpdate (sql);
    sql = "INSERT INTO HISTORY VALUES ('a little life', 'hanya yanagihara', 1, '05/23/2019', 5)";
	statement.executeUpdate (sql);
    }


    static void makeNextBookTable ()
	throws SQLException
    {
	String sql = "DROP TABLE IF EXISTS NEXTBOOK";
	statement.executeUpdate (sql);
	sql = "CREATE TABLE NEXTBOOK (BOOKNAME VARCHAR(100) PRIMARY KEY, YESVOTES INT, NOVOTES INT)";
	statement.executeUpdate (sql);

	sql = "INSERT INTO NEXTBOOK VALUES ('the kite runner', 1, 1)";
	statement.executeUpdate (sql);
    sql = "INSERT INTO NEXTBOOK VALUES ('the wedding planners daughter', 0, 1)";
	statement.executeUpdate (sql);
    sql = "INSERT INTO NEXTBOOK VALUES ('the little red book', 0, 0)";
	statement.executeUpdate (sql);
	
    
    }
}