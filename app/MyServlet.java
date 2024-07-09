// Handle the query server-side

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.*;

// To receive the data from javascript
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
	   // This is the socket that connect to the database
        Connection conn = DriverManager.getConnection (
            "jdbc:h2:~/desktop/Desktop/myservers/databases/workers", 
        "sa", 
        ""
        );
        
	    statement = conn.createStatement();
	    System.out.println ("MyServlet: successful connection to H2 dbase");
	}
	catch (Exception e) {
	    // Bad news if we reach here.
	    e.printStackTrace ();
	}
    }
	 
    public void doPost (HttpServletRequest req, HttpServletResponse resp) 
    {
	// We'll print to terminal to know whether the browser used post or get.
	System.out.println ("MyServlet: doPost()");
	handleRequest (req, resp);
    }
    

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
    {
	System.out.println ("MyServlet: doGet()");
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

            // Confirm the username and password is valid from database.
            if (action.equals("login")){
                String uid = confirmUser(d.infoClass1, d.infoClass2);
                // Debug 
                System.out.println("uid: " + uid);
                outputJson = "{\"uid\":" + uid + "}";
            }

            // After click the profile button, pull out the data from database.
            // getProfile method pull out FIRSTNAME, LASTNAME, EMAIL, YEAR, CarrerChoice FROM worker which come from the database. 
            if (action.equals("profile")) {
                outputJson = getProfile(d.infoClass1);
            } 

            // After click the profile button, pull out the data from database.
            // getID  method pull out  LOCATION, SALARY, Major FROM salary WHERE  salaryID =" + UID which come from the database. 
            if (action.equals("information")) {
                outputJson = getID(d.infoClass1);
            }
            // After click the retire button, pull out the data from database.
            // getRetire method pull out Major, Age FROM retire WHERE  RetireID =" + UID  which come from the database.
            if (action.equals("retire")) {
                outputJson = getRetire(d.infoClass1);
            }

            // After click the graduation button, pull out the data from database.
            // getGraduation method pull out Major, GraduationTime FROM graduation WHERE  GraduatedID =" + UID which come from database.
            if (action.equals("graduation")) {
                outputJson = getGraduation(d.infoClass1);
            }

            // After click the tranning button, pull out the data from database.
            // getTranning method pull out LOCATION, Major,TranningTime FROM tranning WHERE  tranningID =" + UID which come from database.
            if (action.equals("tranning")) {
                outputJson = getTranning(d.infoClass1);
            }  

            // After click the salary button, pull out the data from database.
            // getSalary method pull out LOCATION,SALARY,Major FROM salary WHERE salary > 200000 which come from database.
            if (action.equals("salary")) {
                outputJson = getSalary(d.infoClass1);
            }

            // After click the pay button, pull out the data from database.
            // getLowestSalary method pull out LOCATION,SALARY,Major FROM salary WHERE salary <120000 which come from database.
            if (action.equals("pay")) {
                outputJson = getLowestSalary(d.infoClass1);
            }

            // After click the vacation button, pull out the data from database.
            // GetRetireYear method pull out  Major,Age FROM retire WHERE Age < 55 which come from database.
            if (action.equals("vacation")) {
                outputJson = GetRetireYear(d.infoClass1);
            }

            // After click the negative button, pull out the data from database.
            // GetNegativeRetireYear method pull out Major,Age FROM retire WHERE Age > 66 which come from database.
            if (action.equals("negative")) {
                outputJson = GetNegativeRetireYear(d.infoClass1);
            }

            // After click the data button, pull out the data from database.
            // getData method pull USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL,CarrerChoice,salaryID,YEAR,SEASON FROM worker WHERE UID =" + UID 
            // which come from database.
            if (action.equals("data")) {
                outputJson = getData(d.infoClass1);
            }

            // After click the delete button, pull out the data from database.
            // delete method pull out DELETE FROM worker WHERE UID = " + uid which come from database.
            if (action.equals("delete")) {
                outputJson = delete(d.infoClass1);
            }

            // After click the updates button, pull out the data from database.
            // getData method pull USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL,CarrerChoice,salaryID,YEAR,SEASON FROM worker WHERE UID =" + UID 
            // which come from database.
            if (action.equals("updates")) {
                outputJson = getData(d.infoClass1);
            }

            // we are output jonson because we are updating the userName after click the button. 
            if (action.equals("userName")){
                String userName = usernames(d.infoClass1, d.infoClass2);
                outputJson = "{\"userName\":" + userName + "}";
            }

            // we are output jonson because we are updating the passWord after click the button. 
            if (action.equals("passWord")){
                String passWord = passwords(d.infoClass1, d.infoClass2);
                outputJson = "{\"passWord\":" + passWord + "}";
            }

            // we are output jonson because we are updating the firstName after click the button. 
            if (action.equals("firstName")){
                String firstName = firstName(d.infoClass1, d.infoClass2);
                outputJson = "{\"firstName\":" + firstName + "}";
            }

            // we are output jonson because we are updating the lastName after click the button. 
            if (action.equals("lastName")){
                String lastName = lastName(d.infoClass1, d.infoClass2);
                outputJson = "{\"lastName\":" + lastName + "}";
            }

            // we are output jonson because we are updating the email after click the button. 
            if (action.equals("email")){
                String email = email(d.infoClass1, d.infoClass2);
                outputJson = "{\"email\":" + email + "}";
            }

            // we are output jonson because we are updating the carrer after click the button. 
            if (action.equals("carrer")){
                String carrer = carrer(d.infoClass1, d.infoClass2);
                outputJson = "{\"carrer\":" + carrer + "}";
            }

            // we are output jonson because we are updating the idNumber after click the button. 
            if (action.equals("idNumber")){
                String idNumber = idNumber(d.infoClass1, d.infoClass2);
                outputJson = "{\"idNumber\":" + idNumber + "}";
            }

            // we are output jonson because we are updating the year after click the button. 
            if (action.equals("year")){
                String year = year(d.infoClass1, d.infoClass2);
                outputJson = "{\"year\":" + year + "}";
            }

            // we are output jonson because we are updating the season after click the button. 
            if (action.equals("season")){
                String season = season(d.infoClass1, d.infoClass2);
                outputJson = "{\"season\":" + season + "}";
            }

            // After click the tableOne button, pull out the data from database.
            // getData method pull USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL,CarrerChoice,salaryID,YEAR,SEASON FROM worker WHERE UID =" + UID 
            // which come from database.
            if (action.equals("tableOne")) {
                outputJson = getData(d.infoClass1);
            }

            // After click the updateUserOne button, pull out the data from database.
            // getData method pull USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL,CarrerChoice,salaryID,YEAR,SEASON FROM worker WHERE UID =" + UID 
            // which come from database.
            if (action.equals("updateUserOne")) {
                outputJson = getData(d.infoClass1);
            }

            // After click the tableTwo button, pull out the data from database.
            // getTableTwo method pull salaryID, LOCATION, SALARY, Major FROM salary WHERE salaryID =" + ID which come from database
            if (action.equals("tableTwo")) {
                outputJson = getTableTwo(d.infoClass1);
            }

            // we are output jonson because we are updating the location after click the button. 
            if (action.equals("locations")){
                String locations = secondTableLocation(d.infoClass1, d.infoClass2);
                outputJson = "{\"locations\":" + locations + "}";
            }

            // we are output jonson because we are updating the salarys after click the button.
            if (action.equals("salarys")){
                String salarys = secondTableSalary(d.infoClass1, d.infoClass2);
                outputJson = "{\"salarys\":" + salarys + "}";
            }

            // we are output jonson because we are updating the major after click the button.
            if (action.equals("major")){
                String major = secondTableMajor(d.infoClass1, d.infoClass2);
                outputJson = "{\"major\":" + major + "}";
            }
                        
            // After click the tableThree button, pull out the data from database.
            // getTableThree method pull out Major, Age FROM retire WHERE RetireID =" + ID which come from database.
            if (action.equals("tableThree")) {
                outputJson = getTableThree(d.infoClass1);
            }

            // we are output jonson because we are updating the age after click the button.          
            if (action.equals("age")){
                String age = secondTableAge(d.infoClass1, d.infoClass2);
                outputJson = "{\"age\":" + age + "}";
            }

            // we are output jonson because we are updating the majors after click the button.
            if (action.equals("majors")){
                String majors = secondTableMajors(d.infoClass1, d.infoClass2);
                outputJson = "{\"majors\":" + majors + "}";
            }

            // After click the tableFour button, pull out the data from database.
            // getTableFour method pull out Major, GraduationTime FROM graduation WHERE GraduatedID =" + ID which come from database.
            if (action.equals("tableFour")) {
                outputJson = getTableFour(d.infoClass1);
            }

            // we are output jonson because we are updating the ageNumber after click the button.
            if (action.equals("ageNumber")){
                String ageNumber = FourTableAge(d.infoClass1, d.infoClass2);
                outputJson = "{\"ageNumber\":" + ageNumber + "}";
            }

            // we are output jonson because we are updating the majorsOfSchool after click the button.
            if (action.equals("majorsOfSchool")){
                String majorsOfSchool = FourTableMajor(d.infoClass1, d.infoClass2);
                outputJson = "{\"majorsOfSchool\":" + majorsOfSchool + "}";
            }

            // After click the tableFive button, pull out the data from database.
            // getTableFive  meethod pull SELECT LOCATION, Major, TranningTime  FROM tranning WHERE tranningID =" + ID which come from database
            if (action.equals("tableFive")) {
                outputJson = getTableFive(d.infoClass1);
            }

            // we are output jonson because we are updating the location after click the button.
            if (action.equals("area")){
                String area = getLocation(d.infoClass1, d.infoClass2);
                outputJson = "{\"area\":" + area + "}";
            }
            // we are output jonson because we are updating the major after click the button.
            if (action.equals("job")){
                String job = getJob(d.infoClass1, d.infoClass2);
                // update so return the data
                outputJson = "{\"job\":" + job + "}";
            }

            // we are output jonson because we are updating the tranningTime after click the button.
            if (action.equals("tranningTime")){
                String tranningTime = getTime(d.infoClass1, d.infoClass2);
                outputJson = "{\"tranningTime\":" + tranningTime + "}";
            }

            // delete method delete table one  
            if (action.equals("DeleteTableOne")) {
                outputJson = delete(d.infoClass1);
            }

            // delete method delete table two 
            if (action.equals("DeleteTableTwo")) {
                outputJson = deleteTableTwo(d.infoClass1);
            }

            // delete method delete table three
            if (action.equals("DeleteTableThree")) {
                outputJson = deleteTableThree(d.infoClass1);
            }

            // delete method delete table Four 
            if (action.equals("DeleteTableFour")) {
                outputJson = deleteTableFour(d.infoClass1);
            }

            // delete method delete table Five  
            if (action.equals("DeleteTableFive")) {
                outputJson = deleteTableFive(d.infoClass1);
            }

            // After click the BestSalary button, pull out the data from database.
            // getBestSalary method pull COUNTRY,SALARY FROM country where SALARY > 160000 which come from database
            if (action.equals("BestSalary")) {
                outputJson = getBestSalary(d.infoClass1);
            }

            // After click the Payment button, pull out the data from database.
            // GetLowestSalary method pull COUNTRY,SALARY FROM country where SALARY > 160000 which come from database
            if (action.equals("Payment")) {
                outputJson = GetLowestSalary(d.infoClass1);
            }

            // After click the HighPopulation button, pull out the data from database.
            // GetHighPopulation pull COUNTRY,POPULATION FROM country  where POPULATION> 5000000000 which come from database.
            if (action.equals("HighPopulation")) {
                outputJson = GetHighPopulation(d.infoClass1);
            }

            // After click the Population button, pull out the data from database.
            // GetLowPopulation pull COUNTRY,POPULATION FROM country  WHERE  POPULATION = '400000000'" which come from database
            if (action.equals("Population")) {
                outputJson = GetLowPopulation(d.infoClass1);
            }

            // After click the tableSix button, pull out the data from database.
            // GetTableSix pull COUNTRY, SALARY, POPULATION  FROM country WHERE CountryID =" + ID which come from database
            if (action.equals("tableSix")) {
                outputJson = GetTableSix(d.infoClass1);
            }

            // we are output jonson because we are updating the country after click the button.
            if (action.equals("country")){
                String country = getCountry(d.infoClass1, d.infoClass2);
                outputJson = "{\"country\":" + country + "}";
            }
         
            // we are output jonson because we are updating the salary after click the button.
            if (action.equals("money")){
                String money = getMoney(d.infoClass1, d.infoClass2);
                outputJson = "{\"money\":" + money + "}";
            }

            // we are output jonson because we are updating the POPULATION after click the button.
            if (action.equals("POPULATION")){
                String population = getPOPULATION(d.infoClass1, d.infoClass2);
                outputJson = "{\"population\":" + population + "}";
            }

            // Delete the table six data  
            if (action.equals("DeleteTableSix")) {
                outputJson = deleteTableSix(d.infoClass1);
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

    // Check if the user have the correct user name and password 
    String confirmUser (String username, String password)
    {
        try {
            String sql = "SELECT UID FROM worker WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
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

    // Get the information in database base on the username and password you enter.
    String getProfile(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT FIRSTNAME, LASTNAME, EMAIL, YEAR, CarrerChoice FROM worker WHERE UID =" + UID;
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
    
    // Get the  salary information in database base on the username and password you enter.
    String getID(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT LOCATION, SALARY, Major FROM salary WHERE  salaryID =" + UID  ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;

            
            while (rs.next()) {
                profile = "{\"LOCATION\": \"" + rs.getString(1) + "\", \"SALARY\": \"" + rs.getString(2) + "\", \"MAJOR\": \"" + rs.getString(3) +  "\"}";
                System.out.println("profile: " + profile);
                 System.out.println( profile);
              
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }
    // Get the retire information in database base on the username and password you enter.
    String getRetire(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT Major, Age FROM retire WHERE  RetireID =" + UID  ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;

            
            while (rs.next()) {
                profile = "{\"MAJOR\": \"" + rs.getString(1) + "\", \"AGE\": \"" + rs.getString(2) + "\"}";
                System.out.println("profile: " + profile);
             
              
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }
    // Get the graduation information in database base on the username and password you enter.
    String getGraduation(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT Major, GraduationTime FROM graduation WHERE  GraduatedID =" + UID  ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;

            
            while (rs.next()) {
                profile = "{\"MAJOR\": \"" + rs.getString(1) + "\", \"GRADUATION\": \"" + rs.getString(2) + "\"}";
                System.out.println("profile: " + profile);
              
              
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Get the graduation information in database base on the username and password you enter.
    String getTranning(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT LOCATION, Major,TranningTime FROM tranning WHERE  tranningID =" + UID  ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                profile = "{\"LOCATION\": \"" + rs.getString(1) + "\", \"MAJOR\": \"" + rs.getString(2) + "\", \"TRAINNING\": \"" + rs.getString(3) +  "\"}";
                System.out.println("profile: " + profile);
                  
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the best salary in the database.
    String getSalary(String uid) {
        try {
            String sql = "SELECT LOCATION,SALARY,Major FROM salary WHERE salary > 200000 " ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                profile = "{\"MAJOR\": \"" + rs.getString(1) + "\", \"SALARY\": \"" + rs.getString(2) + "\", \"CITY\": \"" + rs.getString(3) +  "\"}";
                System.out.println("profile: " + profile);
                  
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the lowest salary in the database.
    String getLowestSalary(String uid) {
        try {
            String sql = "SELECT LOCATION,SALARY,Major FROM salary WHERE salary <120000 " ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                profile = "{\"MAJOR\": \"" + rs.getString(1) + "\", \"SALARY\": \"" + rs.getString(2) + "\", \"CITY\": \"" + rs.getString(3) +  "\"}";
                System.out.println("profile: " + profile);          
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the best retire major in database.
    String GetRetireYear(String uid) {   
        try {
            String sql = "SELECT Major,Age FROM retire WHERE Age < 55 " ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                    
            while (rs.next()) {
                profile = "{\"MAJOR\": \"" + rs.getString(1) + "\", \"SALARY\": \"" + rs.getString(2) +  "\"}";
                System.out.println("profile: " + profile);          
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the worest retire major in database.
    String GetNegativeRetireYear(String uid) {    
        try {
            String sql = "SELECT Major,Age FROM retire WHERE Age > 66 " ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                    
            while (rs.next()) {
                profile = "{\"MAJOR\": \"" + rs.getString(1) + "\", \"SALARY\": \"" + rs.getString(2) +  "\"}";
                System.out.println("profile: " + profile);          
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Get all the data in the worker table.
    String getData(String uid) {
        int UID = Integer.parseInt(uid);
        try {
            String sql = "SELECT USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL,CarrerChoice,salaryID,YEAR,SEASON FROM worker WHERE UID =" + UID;
            //String sql = "SELECT USERNAME from worker";
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) + "\", \"FIRSTNAME\": \"" + rs.getString(3) + "\", \"LASTNAME\": \"" + rs.getString(4) + "\", \"EMAIL\": \"" + rs.getString(5)+ "\", \"CarrerChoice\": \"" + rs.getString(6)+ "\", \"salaryID\": \"" + rs.getString(7)+ "\", \"YEAR\": \"" + rs.getString(8)+ "\", \"SEASON\": \"" + rs.getString(9) + "\"}";
                //profile = "{\"USERNAME\": \"" + rs.getString(1) + "\"}";
                System.out.println("profile: " + profile);

            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Delete the table base on the primary key you enter 
    String delete (String uid) {

        try {
           
            String sql = "DELETE FROM worker WHERE UID = " + uid;
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

    // Update the current username in worker table on the database.
    String usernames (String uid, String username) {
        try {
            String sql = "UPDATE worker SET USERNAME = '" + username + "' WHERE UID = '" + uid + "'";
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

    // Update the current passwords in worker table on the database.
    String passwords (String uid, String password) {
        try {
            String sql = "UPDATE worker SET PASSWORD = '" + password + "' WHERE UID = '" + uid + "'";
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

    // Update the current firstName in worker table on the database.
    String firstName (String uid, String firstName) {
        try {
            String sql = "UPDATE worker SET FIRSTNAME = '" + firstName + "' WHERE UID = '" + uid + "'";
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

    // Update the current lastName in worker table on the database.
    String lastName (String uid, String lastName) {
        try {
            String sql = "UPDATE worker SET LASTNAME = '" + lastName + "' WHERE UID = '" + uid + "'";
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

    // Update the current email in worker table on the database.
    String email (String uid, String email) {
        try {
            String sql = "UPDATE worker SET EMAIL = '" + email + "' WHERE UID = '" + uid + "'";
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

    // Update the current carrer in worker table on the database.
    String carrer (String uid, String carrer) {
        try {
            String sql = "UPDATE worker SET CarrerChoice = '" + carrer + "' WHERE UID = '" + uid + "'";
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

    // Update the current salaryID in worker table on the database.
    String idNumber (String uid, String id) {
        try {
            String sql = "UPDATE worker SET salaryID = '" + id + "' WHERE UID = '" + uid + "'";
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

    // Update the current year in worker table on the database.
    String year (String uid, String year) {
        try {
            String sql = "UPDATE worker SET YEAR = '" + year + "' WHERE UID = '" + uid + "'";
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

    // Update the current season in worker table on the database.
    String season (String uid, String season) {
        try {
            String sql = "UPDATE worker SET SEASON = '" + season + "' WHERE UID = '" + uid + "'";
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


    // Get the all the data from salary table.
    String getTableTwo(String salaryId) {
        int ID = Integer.parseInt(salaryId);
        try {
            String sql = "SELECT salaryID, LOCATION, SALARY, Major FROM salary WHERE salaryID =" + ID;
            //String sql = "SELECT USERNAME from worker";
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) + "\", \"FIRSTNAME\": \"" + rs.getString(3) + "\", \"LASTNAME\": \"" + rs.getString(4) + "\"}";
                //profile = "{\"USERNAME\": \"" + rs.getString(1) + "\"}";
                System.out.println("profile: " + profile);

            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Update the current location in salary table on the database.
    String secondTableLocation (String id, String location) {
        try {
            String sql = "UPDATE salary SET LOCATION = '" + location + "' WHERE salaryID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the current salary in salary table on the database.
    String secondTableSalary (String id, String salary) {
        try {
            String sql = "UPDATE salary SET SALARY = '" + salary + "' WHERE salaryID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the current Major in salary table on the database.
    String secondTableMajor (String id, String major) {
        try {
            String sql = "UPDATE salary SET Major = '" + major + "' WHERE salaryID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Get all the data in retire table base on the primary key
    String getTableThree(String retireID) {
        int ID = Integer.parseInt(retireID);
        try {
            String sql = "SELECT Major, Age FROM retire WHERE RetireID =" + ID;
            //String sql = "SELECT USERNAME from worker";
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"FIRSTNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2)  + "\"}";
                //profile = "{\"USERNAME\": \"" + rs.getString(1) + "\"}";
                System.out.println("profile: " + profile);

            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }
    
    // Update the age on retire table.
    String secondTableAge (String id, String age) {
        try {
            String sql = "UPDATE retire SET Age = '" + age + "' WHERE RetireID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the Major on retire table.
    String secondTableMajors (String id, String major) {
        try {
            String sql = "UPDATE retire SET Major = '" + major + "' WHERE RetireID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Get the data from graduation table.
    String getTableFour(String graduationID) {
        int ID = Integer.parseInt(graduationID);
        try {
            String sql = "SELECT Major, GraduationTime FROM graduation WHERE GraduatedID =" + ID;
            //String sql = "SELECT USERNAME from worker";
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"FIRSTNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2)  + "\"}";
                //profile = "{\"USERNAME\": \"" + rs.getString(1) + "\"}";
                System.out.println("profile: " + profile);

            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Update the major on graduation table.
    String FourTableAge (String id, String major) {
        try {
            String sql = "UPDATE graduation SET Major = '" + major + "' WHERE GraduatedID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the GraduationTime on graduation table.
    String FourTableMajor (String id, String time) {
        try {
            String sql = "UPDATE graduation SET GraduationTime = '" + time + "' WHERE GraduatedID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // get the data from tranning table on the database.
    String getTableFive(String tranning) {
        int ID = Integer.parseInt(tranning);
        try {
            String sql = "SELECT LOCATION, Major, TranningTime  FROM tranning WHERE tranningID =" + ID;
            //String sql = "SELECT USERNAME from worker";
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) + "\", \"FIRSTNAME\": \"" + rs.getString(3) + "\"}";
                //profile = "{\"USERNAME\": \"" + rs.getString(1) + "\"}";
                System.out.println("profile: " + profile);

            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Update the lcocation from tranning table on the database.
    String getLocation (String id, String location) {
        try {
            String sql = "UPDATE tranning SET LOCATION = '" + location + "' WHERE tranningID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the Major from tranning table on the database.
    String getJob (String id, String major) {
        try {
            String sql = "UPDATE tranning SET Major = '" + major + "' WHERE tranningID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the TranningTime from tranning table on the database.
    String getTime (String id, String time) {
        try {
            String sql = "UPDATE tranning SET TranningTime = '" + time + "' WHERE tranningID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Delete the table base on the primary key you enter.
    String deleteTableTwo (String id) {

        try {    
            String sql = "DELETE FROM salary WHERE salaryID = " + id;
            int success = statement.executeUpdate (sql);

            if (success == 1) {
        
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Delete the table base on the primary key you enter.
    String deleteTableThree (String id) {

        try {    
            String sql = "DELETE FROM retire WHERE RetireID = " + id;
            int success = statement.executeUpdate (sql);

            if (success == 1) {
        
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Delete the table base on the primary key you enter.
    String deleteTableFour (String id) {

        try {    
            String sql = "DELETE FROM graduation WHERE GraduatedID = " + id;
            int success = statement.executeUpdate (sql);

            if (success == 1) {
        
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
    // Delete the table base on the primary key you enter.
    String deleteTableFive (String id) {
        try {    
            String sql = "DELETE FROM tranning WHERE tranningID = " + id;
            int success = statement.executeUpdate (sql);

            if (success == 1) {
        
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    

    // Find the country that has the best paying salary.
    String getBestSalary(String salaryID) {
        try {
            String sql = "SELECT COUNTRY,SALARY FROM country where SALARY > 160000 " ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                //joson
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) +   "\"}";
                System.out.println("profile: " + profile);
                  
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the country that has the lowest paying salary.
    String GetLowestSalary(String salaryID) {
        try {
            String sql = "SELECT COUNTRY,SALARY FROM country  where SALARY< 110000" ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) +   "\"}";
                System.out.println("profile: " + profile);
                  
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the country that has the highest population.
    String GetHighPopulation(String salaryID) {
        try {
            String sql = "SELECT COUNTRY,POPULATION FROM country  where POPULATION> 5000000000" ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) +   "\"}";
                System.out.println("profile: " + profile);
                  
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Find the country that has the lowest population
    String GetLowPopulation(String salaryID) {
        try {
            String sql = "SELECT COUNTRY,POPULATION FROM country  WHERE  POPULATION = '400000000'" ;
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
                        
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) +   "\"}";
                System.out.println("profile: " + profile);
                  
            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Get the country table data from database.
    String GetTableSix(String countryID) {
        int ID = Integer.parseInt(countryID);
        try {
            String sql = "SELECT COUNTRY, SALARY, POPULATION  FROM country WHERE CountryID =" + ID;
            //String sql = "SELECT USERNAME from worker";
            ResultSet rs = statement.executeQuery (sql);
            String profile = null;
            
            while (rs.next()) {
                profile = "{\"USERNAME\": \"" + rs.getString(1) + "\", \"PASSWORD\": \"" + rs.getString(2) + "\", \"FIRSTNAME\": \"" + rs.getString(3) + "\"}";
                //profile = "{\"USERNAME\": \"" + rs.getString(1) + "\"}";
                System.out.println("profile: " + profile);

            }
            return profile;
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        
    }

    // Update the country on country table. 
    String getCountry (String id, String country) {
        try {
            String sql = "UPDATE country SET COUNTRY = '" + country + "' WHERE CountryID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the salary on country table. 
    String getMoney (String id, String salary) {
        try {
            String sql = "UPDATE country SET SALARY = '" + salary + "' WHERE CountryID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Update the population on country table. 
    String getPOPULATION (String id, String population) {
        try {
            String sql = "UPDATE country SET POPULATION = '" + population + "' WHERE CountryID = '" + id + "'";
            int success = statement.executeUpdate (sql);
            if (success == 1) {
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    // Delete country table base on primary key.
    String deleteTableSix (String id) {

        try {    
            String sql = "DELETE FROM country WHERE CountryID = " + id;
            int success = statement.executeUpdate (sql);

            if (success == 1) {
        
                return id;
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
}
