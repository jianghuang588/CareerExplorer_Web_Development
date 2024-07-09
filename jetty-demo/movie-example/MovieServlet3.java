// Handle the movie-find query

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


public class MovieServlet3 extends HttpServlet {

    public MovieServlet3 ()
    {
	// We aren't doing anything here, but in the future we'll
	// want to open the database here to see if a connection
	// is possible. If not, there's something wrong. Also, 
	// for a high-volume system, this is the place to create
	// a connection-pool.
    }
	 
    public void doPost (HttpServletRequest req, HttpServletResponse resp) 
    {
	// We'll print to terminal to know whether the browser used post or get.
	System.out.println ("MovieServlet2: doPost()");
	handleRequest (req, resp);
    }
    

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
    {
	System.out.println ("MovieServlet3: doGet()");
	handleRequest (req, resp);
    }


    

    public void handleRequest (HttpServletRequest req, HttpServletResponse resp)
    {
	try {
	    resp.setContentType ("text/html");
	    resp.setCharacterEncoding ("UTF-8");

	    PrintWriter out = resp.getWriter();
	    // The top part of the HTML page:
	    out.println ("<html>");
	    out.println ("<head><title> Movie Results </title></head>");
	    out.println ("<body bgcolor=\"#DDDDFF\">");
	    out.println ("<h2>Movie Finder Results</h2>");
	    



	 
	    out.println ("</body>");
	    out.println ("</html>");
	    out.flush ();
	    
	   
	



		}catch (Exception e) {
	    e.printStackTrace();
	}

    }


    ArrayList<String> getMovieTitles (String actorname)
    {
    	//code is coming from TestMovies.java
	try {
	    ArrayList<String> movieTitles = new ArrayList<>();

	    // Load the program that knows how to talk to the DB.
        Class.forName ("org.h2.Driver");
        Connection conn = DriverManager.getConnection ("jdbc:h2:~/desktop/Desktop/myservers/databases/movies", "sa", "");
	    // "sa" is a pre-defined user for this DB, with no password.

	    // This part and below is standard JDBC code.
	    Statement st = conn.createStatement();

	    // The SQL statement:
	    //String sql = "SELECT TITLE FROM MOVIES,ACTORS"
		//+ " WHERE ACTORS.MOVIEID=MOVIES.MOVIEID"
		//+ " AND ACTORS.ACTOR='" + actorname +"'";
		String sql = "SELECT * FROM GENRES";


	    // Send the SQL string over the (socket) connection 
	    ResultSet rs = st.executeQuery (sql);

	    // If we get here, there are results, returned as rows of a table.
	    while (rs.next()) {
		// This is how we extract an actual element from a row.
		//String title = rs.getString ("TITLE");
	    int id = rs.getInt ("MOVIEID");
		String genre = rs.getString ("GENRE");

		//System.out.println ("In MovieServlet: title=" + title);
		System.out.println ("id=" + id + " genre=" + genre);


	    }

            conn.close();
	    return movieTitles;
	}
	catch (Exception e) {
	    e.printStackTrace ();
	    return null;
	}
    }


}
