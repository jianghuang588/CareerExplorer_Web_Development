import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.util.thread.*;
import org.eclipse.jetty.http.*;
import org.eclipse.jetty.server.handler.*;

public class WebServer {

    public static void main (String[] argv ) 
    {
	try {
	    // CHANGE TO YOUR PORT#:
	    Server server = new Server (40013);   

	    // CAN SKIP READING FROM HERE
	    ResourceHandler rHandler = new ResourceHandler();
	    rHandler.setResourceBase(".");                     // Current dir.
	    ContextHandler cHandler = new ContextHandler("/"); // Generic URL
	    cHandler.setHandler(rHandler);
	    // Now the servlets: in this case just one.
	    ServletContextHandler sHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            // TO HERE (It's all boilerplate).

	    // We want the form URL /customer to go to CustomerServlet
	    sHandler.addServlet(new ServletHolder(new MyServlet()),"/workers");
	 
	 
	    // Put all of these "handlers" into the server.
	    ContextHandlerCollection contexts = new ContextHandlerCollection();
	    contexts.setHandlers(new Handler[] { cHandler, sHandler });
	    server.setHandler(contexts);
	    // TO HERE.
	    
	    // Start the server.
	    server.start();
	    System.out.println ("Webserver started, ready for browser connections");
	    server.join();
	}
	catch (Exception e) {
	    // We really don't want to reach here.
	    e.printStackTrace();
	}
    }

}
