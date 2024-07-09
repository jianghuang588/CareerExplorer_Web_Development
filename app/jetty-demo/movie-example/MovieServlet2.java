// Handle the movie-find query

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


public class MovieServlet2 extends HttpServlet {

    public MovieServlet2 ()
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
	System.out.println ("MovieServlet2: doGet()");
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
	    

	   	// Extract the name that was typed into the actor's name textfield:
	   String actorName = req.getParameter ("actorname");
	 
	   String actorName2 = req.getParameter ("actorname2");
   		//The actual results after pulling from the database
	   ArrayList<String> movieTitles = getMovieTitles (actorName);
	   ArrayList<String> movieTitles2 = getMovieTitles (actorName2);


 	   for(int i=0; i<movieTitles.size();i++){
	    	if(movieTitles2.contains(movieTitles.get(i))){
	    		out.println ("<ol>");
	    		
	    			out.println("<li> " + movieTitles.get(i) + "</li>");
					out.println ("</ol>");
	    }

	    	
	 
	    out.println ("</body>");
	    out.println ("</html>");
	    out.flush ();
	
	}

		}catch (Exception e) {
	    e.printStackTrace();
	}

    }


    ArrayList<String> getMovieTitles (String tableName)
    {
	try {
	    ArrayList<String> movieTitles = new ArrayList<>();

	    // Load the program that knows how to talk to the DB.
            Class.forName ("org.h2.Driver");
            Connection conn = DriverManager.getConnection ("jdbc:h2:~/desktop/Desktop/myservers/databases/movies", "sa", "");
	    // "sa" is a pre-defined user for this DB, with no password.

	    // This part and below is standard JDBC code.
	    Statement st = conn.createStatement();

	    // The SQL statement:
		String sql = "SELECT * FROM " + tableName;
	    // Send the SQL string over the (socket) connection 
	    ResultSet rs = st.executeQuery (sql);

	    // If we get here, there are results, returned as rows of a table.
	    while (rs.next()) {
	    String row = "Row: ";
	    for (int i=1; i<=numColumns; i++) {
		String s = rs.getString (i);      
		// One could get an int column into an int variable.
		row += " " + s;
	    }

            conn.close();
	    return row;
	}
	catch (Exception e) {
	    e.printStackTrace ();
	    return null;
	}
    }


}
