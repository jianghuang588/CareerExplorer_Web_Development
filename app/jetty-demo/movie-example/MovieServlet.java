// Handle the movie-find query

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


public class MovieServlet extends HttpServlet {

    public MovieServlet ()
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
	System.out.println ("MovieServlet: doPost()");
	handleRequest (req, resp);
    }
    

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
    {
	System.out.println ("MovieServlet: doGet()");
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



	    


	    // The bottom part, along with the all-important flush().
	    out.println ("</body>");
	    out.println ("</html>");
	    out.flush ();
	    
	   


	} catch (Exception e) {
	    e.printStackTrace();
	}
    


    }


}
