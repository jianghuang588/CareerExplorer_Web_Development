Instruction on how to start the program:
- make sure that H2 database is running
- java -cp h2*.jar org.h2.tools.Server -tcp -pg
- compile and execute workerDB.java
- database will be create after you compile and execute workerDB.java
- compile and execute WebServer.java 
- when you run the html page use localhost:40013/login.html 
- login.html is the main page

- 6 regular username: "michael","taylor","julia","nora","oliver","william"
- the regular user have the same password: "password"

- admin username: "admin" 
- password:"password" 

Table one 
- delete the account by enter the right primary key number
The primary key is 1-7
(1) michael
(2) taylor
(3) julia
(4) nora
(5) oliver
(6) william
(7) admin

Table two to six on the admin page 
- delete the information by enter 7
For table two to six, I only pull out the database information related to primary key 7 
