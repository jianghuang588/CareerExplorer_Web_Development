// Handle the query server-side

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.*;


class DataInfo {
    String infoClass1;
    String infoClass2;
    String action;
}


public class MyServlet extends HttpServlet {

    static Connection conn;
    static Statement statement;

    public MyServlet ()
    {
	// NOTE: this is where we set up a connection to the dbase.
	// There's a single connection for the life of this servlet,
	// which is opened when the MyBCServlet class instance
	// is first created.
	try {
            Class.forName ("org.h2.Driver");
	    conn = DriverManager.getConnection (
		"jdbc:h2:~/Desktop/myservers/databases/bookclub", 
                "sa", 
	        ""
            );
	    statement = conn.createStatement();
	    System.out.println ("MyBCServlet: successful connection to H2 dbase");
	}
	catch (Exception e) {
	    // Bad news if we reach here.
	    e.printStackTrace ();
	}
    }
	 
    public void doPost (HttpServletRequest req, HttpServletResponse resp) 
    {
	// We'll print to terminal to know whether the browser used post or get.
	System.out.println ("MyBCServlet: doPost()");
	handleRequest (req, resp);
    }
    

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
    {
	System.out.println ("MyBCServlet: doGet()");
	handleRequest (req, resp);
    }
    


    public void handleRequest (HttpServletRequest req, HttpServletResponse resp)
    {
        try {
            // We are going to extract the string line by line
            StringBuffer sbuf = null;
            BufferedReader bufReader = null;
            String inputStr = null;

            bufReader = req.getReader ();
            sbuf = new StringBuffer ();
            while ((inputStr = bufReader.readLine()) != null) {
                sbuf.append (inputStr);
            }

            // What's in the buffer is the entire JSON string.
            String jStr = sbuf.toString();
            System.out.println("Received: " + jStr);


            // Parse out the username and password from JSON:
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            DataInfo d = gson.fromJson (jStr, DataInfo.class);
            System.out.println ("Received: dataInfo=" + d);
            String action = d.action;

            // Set the content type:
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Writer writer = resp.getWriter ();
            String outputJson = "";

            if (action.equals("login")){
                String uid = confirmUser(d.infoClass1, d.infoClass2);
                System.out.println("uid: " + uid);

                outputJson = "{\"uid\":" + uid + "}";
            }
            
            if (action.equals("signup")){
                String uid = signup(d.infoClass1, d.infoClass2);
                System.out.println("uid: " + uid);
                
                outputJson = "{\"uid\":" + uid + "}";
            }

            if (action.equals("profile")) {
                outputJson = getProfile(d.infoClass1);
            }
            
            if (action.equals("delete")){
                String uid = delete(d.infoClass1);
                
                outputJson = "{\"uid\":" + uid + "}";
                
            }

            if (action.equals("bookclub")){
                outputJson = getBookclub(d.infoClass1);

            }
            
            if (action.equals("username")){
                String uid = username(d.infoClass1, d.infoClass2);
                
                outputJson = "{\"uid\":" + uid + "}";
            }
            
            if (action.equals("firstName")){
                String uid = firstName(d.infoClass1, d.infoClass2);
                
                outputJson = "{\"uid\":" + uid + "}";
            }
            
            if (action.equals("lastName")){
                String uid = lastName(d.infoClass1, d.infoClass2);
                
                outputJson = "{\"uid\":" + uid + "}";
            }
            
            if (action.equals("email")){
                String uid = email(d.infoClass1, d.infoClass2);
                
                outputJson = "{\"uid\":" + uid + "}";
            }
            
            if (action.equals("favBook")){
                String uid = favBook(d.infoClass1, d.infoClass2);
                
                outputJson = "{\"uid\":" + uid + "}";
            }


            if (action.equals("current")) {
                outputJson = getBook(d.infoClass1);

            }

            if (action.equals("suggestion")) {
                String bcno = suggest(d.infoClass1, d.infoClass2);
                
                outputJson = "{\"bcno\":" + bcno + "}";
            }
            
            if (action.equals("userCount")) {
                int count = userCount(d.infoClass1);
                
                outputJson = "{\"count\":" + count + "}";
            }

            if (action.equals("memberInfo")) {
                
                outputJson = memberInfo(d.infoClass1, d.infoClass2);
            }
            
            
            if (action.equals("historyInfo")) {
                outputJson = historyInfo(d.infoClass1, d.infoClass2);
            }
            
            if (action.equals("progressInfo")) {
                outputJson = progressInfo(d.infoClass1, d.infoClass2);
            }
            
            // Write it out and, most importantly, flush():
            writer.write(outputJson);
            writer.flush();

            // Debugging:
            System.out.println (outputJson);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    String confirmUser (String username, String password)
    {
        try {
            String sql = "SELECT UID FROM USER WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
            ResultSet rs = statement.executeQuery (sql);
            String uid = null;

            while (rs.next()) {
              uid= rs.getString(1);
              System.out.println ("uid: " + uid);
            }
            return uid;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String getBCNO (String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT BCNO FROM USER WHERE UID =" + UID;
            ResultSet rs = statement.executeQuery (sql);
            String BCNO = null;
            
            while (rs.next()) {
                BCNO = rs.getString(1);
                System.out.println ("BCNO: " + BCNO);
            }
            return BCNO;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String getProfile(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT FIRSTNAME, LASTNAME, EMAIL, YEAR, FAVORITEBOOK FROM USER WHERE UID =" + UID;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"firstName\": \"" + rs.getString(1) + "\", \"lastName\": \"" + rs.getString(2) + "\", \"email\": \"" + rs.getString(3) + "\", \"year\": \"" + rs.getString(4) + "\", \"favBook\": \"" + rs.getString(5) + "\"}";
                System.out.println("profile: " + profile);
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }
    
    String signup (String username, String password) {
        try {
            String sql = "SELECT MAX(UID) FROM USER";
            ResultSet rs = statement.executeQuery (sql);
            String maxid = "";
            
            while (rs.next()) {
                maxid = rs.getString(1);
                System.out.println("maxid: " + maxid);
            }
            
            int maxInt = Integer.parseInt(maxid);
            int nextInt = maxInt + 1;
            sql = "INSERT INTO USER VALUES(" + nextInt + ", '" + username + "', '" + password + "', null, null, null, null, null, null, null)";
            int success = statement.executeUpdate(sql);
            System.out.println("success: " + success);
            if (success == 1){
                String uid = Integer.toString(nextInt);
                return uid;
            } else {
                return null;
            }
            
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String delete (String uid) {
        try {
            String sql = "DELETE FROM USER WHERE UID = " + uid;
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return uid;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String getBookclub (String uid) {
        String bcno = getBCNO(uid);
        try {
            String sql = "SELECT BCNAME FROM BOOKCLUB WHERE BCNO = "  + bcno;
            ResultSet rs = statement.executeQuery (sql);
            String bcname = "";
            
            while (rs.next()) {
                bcname = rs.getString(1);
                System.out.println("bcname: " + bcname);
            }
            
            String json = "{\"bcno\": \"" + bcno + "\", \"bcname\": \"" + bcname + "\"}";
            System.out.println(json);
            return json;
            
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String getBook (String bcno) {
        try {
            String sql = "SELECT CURRENTBOOK FROM BOOKCLUB WHERE BCNO = " + bcno;
            ResultSet rs = statement.executeQuery (sql);
            String current = "";
            
            while (rs.next()) {
                current = rs.getString(1);
                System.out.println("current book: " + current);
            }
            
            sql = "SELECT AUTHOR, DESCRIPTION, MEETINGDATE, MEETINGLOC FROM CURRENTBOOK WHERE NAME = '" + current + "'";
            rs = statement.executeQuery (sql);
            String output = null;
            
            while (rs.next()) {
                output = "{\"currentBook\": \"" + current + "\", \"author\": \"" + rs.getString(1) + "\", \"description\": \"" + rs.getString(2) + "\", \"date\": \"" + rs.getString(3) + "\", \"loc\": \"" + rs.getString(4) +  "\"}";
                System.out.println("output: " + output);
            }
            return output;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String username (String uid, String username) {
        try {
            String sql = "UPDATE USER SET USERNAME = '" + username + "' WHERE UID = '" + uid + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return uid;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String firstName (String uid, String firstName) {
        try {
            String sql = "UPDATE USER SET FIRSTNAME = '" + firstName + "' WHERE UID = '" + uid + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return uid;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String lastName (String uid, String lastName) {
        try {
            String sql = "UPDATE USER SET LASTNAME = '" + lastName + "' WHERE UID = '" + uid + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return uid;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String email (String uid, String email) {
        try {
            String sql = "UPDATE USER SET EMAIL = '" + email + "' WHERE UID = '" + uid + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return uid;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String favBook (String uid, String favBook) {
        try {
           String sql = "UPDATE USER SET FAVORITEBOOK = '" + favBook + "' WHERE UID = '" + uid + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return uid;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String suggest (String bcno, String suggestion) {
        try {
            String sql = "INSERT INTO NEXTBOOK VALUES ('" + suggestion + "', 0, 0)";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return bcno;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    int userCount (String bcno) {
        //TODO: return the count of users in a given book club
    }

    String memberInfo (String bcno, String num) {
        try {
            String sql = "SELECT FIRSTNAME, LASTNAME, EMAIL, YEAR FROM USER WHERE BCNO = " + bcno;
            ResultSet rs = statement.executeQuery (sql);
            String output = null;
            int member = Integer.parseInt(num);
            int count = 1;
            
            while (rs.next()) {
                if (count==member) {
                    output = "{\"name\": \"" + rs.getString(1) + " " + rs.getString(2) + "\", \"email\": \"" + rs.getString(3) + "\", \"year\": \"" + rs.getString(4) +  "\"}";
                    System.out.println(output);
                    return output;
                }
                count++;
            }
            return output;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String historyInfo (String bcno, String num) {
        try {
            String sql = "SELECT BOOKNAME, MEETINGDATE, RATING FROM HISTORY WHERE BCNO = " + bcno;
            ResultSet rs = statement.executeQuery (sql);
            String output = null;
            int member = Integer.parseInt(num);
            int count = 1;
            
            while (rs.next()) {
                if (count==member) {
                    output = "{\"title\": \"" + rs.getString(1) + "\", \"meeting\": \"" + rs.getString(2) + "\", \"rating\": \"" + rs.getString(3) + "\"}";
                    System.out.println(output);
                    return output;
                }
                count++;
            }
            return output;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    
    String progressInfo (String bcno, String num) {
        try {
            String sql = "SELECT FIRSTNAME, LASTNAME, BOOKPROGRESS FROM USER WHERE BCNO = " + bcno;
            ResultSet rs = statement.executeQuery (sql);
            String output = null;
            int member = Integer.parseInt(num);
            int count = 1;
            
            while (rs.next()) {
                if (count==member) {
                    output = "{\"name\": \"" + rs.getString(1) + " " + rs.getString(2) + "\", \"page\": \"" + rs.getString(3) + "\"}";
                    System.out.println(output);
                    return output;
                }
                count++;
            }
            return output;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    

}
