This project file contains html and js files for all the following pages:
-login: regular user login using username and password
-signup: if no existing account, create username and password
-home: links to profile and bookclub page
-profile: gives short bio for user
-edit profile: option to update any of the fields of the user profile
-admin login: admin specifically using username and password
-admin: allows admin to create new user and delete users
-book club: links to current and next book page
-current book: gives description of current book, and other member
 progress
-next book: submit suggestion for next book
-history: shows reading history of book club
-members: shows name, email, and year joined of all members of book club

As well as java files:
- MakeBookClubDB
- MyServer
- MyServlet

To run this project: 
- Ensure that your h2 database is running
- Compile and run MakeBookClubDB.java
- Compile and run MyServer.java
- visit localhost:40013/login.html in your browser

Needs to be completed:
- the userCount() function in MyServlet.java. Return count of users in book club
- getMembers() function in members.js. Print out name, email, and year joined for all members
