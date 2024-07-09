import java.sql.*;

public class workerDB{

	static Statement statement;

 	public static void main (String[] argv){

 		try {
	    
        	Class.forName ("org.h2.Driver");
            Connection conn = DriverManager.getConnection (
   	        "jdbc:h2:~/desktop/Desktop/myservers/databases/workers", 
		"sa", 
		""
            );

        statement = conn.createStatement ();

       	// Create the method for each table.
        makeWorkerTable();
        printTable ("worker",10);

        makeSalaryForTheCarrer();
        printTable ("salary",4);

        makeRetireYearForTheCarrer();
        printTable ("retire",3);

        makeGraduationTime();
        printTable ("graduation",3);

        makeTranningTable();
        printTable ("tranning",4);

        makeBestWorkingCountry();
        printTable ("country",4);


            conn.close();
        } catch (Exception e) {
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


	static void makeWorkerTable()
	throws SQLException{
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS worker";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE worker (UID INT PRIMARY KEY, USERNAME VARCHAR(28), PASSWORD VARCHAR(12), FIRSTNAME VARCHAR(27), LASTNAME VARCHAR(25), EMAIL VARCHAR(50), CarrerChoice VARCHAR(100), salaryID INT, YEAR INT(6), SEASON VARCHAR(12))";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO worker VALUES (1, 'michael', 'password', 'michael', 'jiang', 'michael@gmail.com', 'doctor', 12345678, 2010, 'spring')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO worker VALUES (2, 'taylor', 'password', 'taylor', 'smith', 'taylor@gmail.com', 'lawyer', 13966678, 2011, 'summer')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO worker VALUES (3, 'julia', 'password', 'julia', 'brown', 'julia@gmail.com', 'teacher', 12131231, 2012, 'fall')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO worker VALUES (4, 'nora', 'password',  'nora', 'davis', 'nora@gmail.com', 'nurse', 66666666, 2013, 'summer')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO worker VALUES (5, 'oliver', 'password', 'oliver', 'miller', 'oliver@gmail.com', 'economics', 88888888, 2014, 'SecondSpring')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO worker VALUES (6, 'william', 'password', 'william', 'john', 'william@gmail.com', 'engineer', 13145201,2015, 'SecondSummer')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO worker VALUES (7, 'admin', 'password', 'admin', 'tom', 'admin@gmail.com', 'scientist', 13166201, 2016, 'ThirdSummer')";
	statement.executeUpdate (sql);
	}
	
	static void makeSalaryForTheCarrer()
	throws SQLException{
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS salary";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE salary (salaryID INT PRIMARY KEY, LOCATION VARCHAR(25), SALARY VARCHAR(12), Major VARCHAR(25) )";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO salary VALUES (1,'GeorgeWashington', 220000 , 'doctor' )";
	statement.executeUpdate (sql);
	
	sql = "INSERT INTO salary VALUES (2, 'NewYork', 180000, 'lawyer')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO salary VALUES (3, 'Albany', 150000, 'teacher')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO salary VALUES (4, 'Buffalo', 100000, 'nurse')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO salary VALUES (5, 'California', 170000, 'economics')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO salary VALUES (6, 'Alaska', 150000, 'engineer')";
	statement.executeUpdate (sql);
	sql = "INSERT INTO salary VALUES (7, 'Florida', 150000, 'scientist')";
	statement.executeUpdate (sql);


	}

	static void makeRetireYearForTheCarrer()
	throws SQLException{
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS retire";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE retire (RetireID INT PRIMARY KEY, Major VARCHAR(25), Age VARCHAR(12))";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO retire VALUES (1, 'doctor', 70 )";
	statement.executeUpdate (sql);
	sql = "INSERT INTO retire VALUES (2, 'lawyer', 65)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO retire VALUES (3, 'teacher', 60)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO retire VALUES (4, 'nurse', 58)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO retire VALUES (5, 'economics', 50)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO retire VALUES (6,  'engineer', 62)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO retire VALUES (7,  'scientist', 61)";
	statement.executeUpdate (sql);
	}


	static void makeGraduationTime()
	throws SQLException{
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS graduation";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE graduation (GraduatedID INT PRIMARY KEY, Major VARCHAR(25), GraduationTime VARCHAR(12))";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO graduation VALUES (1, 'doctor', 8 )";
	statement.executeUpdate (sql);
	sql = "INSERT INTO graduation VALUES (2, 'lawyer', 6)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO graduation VALUES (3, 'teacher', 5)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO graduation VALUES (4, 'nurse', 4)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO graduation VALUES (5, 'economics', 4)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO graduation VALUES (6,  'engineer', 5)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO graduation VALUES (7,  'scientist', 5)";
	statement.executeUpdate (sql);
	}

	static void makeTranningTable()
	throws SQLException{
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS tranning";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE tranning (tranningID INT PRIMARY KEY,LOCATION VARCHAR(25) , Major VARCHAR(25), TranningTime VARCHAR(25) )";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO tranning VALUES (1,'GeorgeWashington','doctor', 8 )";
	statement.executeUpdate (sql);
	
	sql = "INSERT INTO tranning VALUES (2,'NewYork', 'lawyer', 4 )";
	statement.executeUpdate (sql);
	sql = "INSERT INTO tranning VALUES (3,'Albany', 'teacher', 4)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO tranning VALUES (4,'Buffalo', 'nurse', 2)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO tranning VALUES (5,'California','economics', 2)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO tranning VALUES (6,'Alaska', 'engineer', 3)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO tranning VALUES (7,'Florida', 'scientist', 3)";
	statement.executeUpdate (sql);
	}

	static void makeBestWorkingCountry()
	throws SQLException{
	// Get rid of any existing table by this name.
	String sql = "DROP TABLE IF EXISTS country";
	statement.executeUpdate (sql);

	// Now make a fresh (but empty) table.
	sql = "CREATE TABLE country (CountryID INT PRIMARY KEY,COUNTRY VARCHAR(25) , SALARY VARCHAR(25), POPULATION VARCHAR(25) )";
	statement.executeUpdate (sql);

	// Insert rows one by one.
	sql = "INSERT INTO country VALUES (1,'Canada','100000', 110000000 )";
	statement.executeUpdate (sql);
	
	sql = "INSERT INTO country VALUES (2,'United State', '120000', 400000000 )";
	statement.executeUpdate (sql);
	sql = "INSERT INTO country VALUES (3,'China', '130000', 2000000000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO country VALUES (4,'Japan', '110000', 3000000000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO country VALUES (5,'France','140000', 6000000000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO country VALUES (6,'England', '170000', 700000000)";
	statement.executeUpdate (sql);
	sql = "INSERT INTO country VALUES (7,'Australia', '150000', 3000000000)";
	statement.executeUpdate (sql);
	}

}

	






