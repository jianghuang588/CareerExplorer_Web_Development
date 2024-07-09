// URL to servlet:
let url = "http://localhost:40013/workers";

// What we will send over.
// Receive the data and send the data to the servlet in form of json.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "tableThree"
};

// JSON/Javascript object to collect all the form data.
let jsonDataFromServer = {

    PASSWORD: "",
    FIRSTNAME: "",
   
}

var PASSWORD = "";
var FIRSTNAME = "";


let app = angular.module("myApp", []);



app.controller("myController", function($scope) {
    myBookClub($scope);
    setTimeout(function(){
       
        // Getting the data from html page.
        $scope.PASSWORD = jsonDataFromServer.PASSWORD;
        // Debugging 
        console.log(" PASSWORD: " + $scope.PASSWORD);

        $scope.FIRSTNAME = jsonDataFromServer.FIRSTNAME;
        console.log(" FIRSTNAME: " + $scope.FIRSTNAME);

      
      

        $scope.$apply();
    }, 1000);
    
});

// send the data to the server.
// we will send the data from the login page and receive the information such as name,and id.
function sendDataToServer ($scope) {

    // Make the request object instance. 
    let req = new XMLHttpRequest();

    // Give the request object a reference to our method requestListener().
    req.addEventListener("load", requestListener);

    // open the connection to the url.
    req.open("POST", url);

    // set context type to json.
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    // Send the actual json , first convert to string.
    req.send(JSON.stringify(DataToServer));

    // Debugging 
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

// Receive the data data from server.
// we can request the data or user information from database.
function requestListener () {
    let jsonObject = JSON.parse (this.responseText);
    

    jsonDataFromServer.PASSWORD = jsonObject.PASSWORD;
    jsonDataFromServer.FIRSTNAME = jsonObject.FIRSTNAME;
 
   
    if (jsonDataFromServer == null) {
        println ("Something went wrong");
    }

    // Debugging     
    console.log(jsonDataFromServer);
    console.log (jsonObject);
}

// get UID from database.
// we use get uid by using session varibale.
function getUID () {
    uid = sessionStorage.getItem("UID");
    return uid;
}

// Send the data to the servelt to respond to html page.
// Sending and receiving information from the servlet to get the tableThree information.
function myBookClub($scope) {
    uid = getUID();
    DataToServer.infoClass1 = uid;
    // Debugging
    DataToServer.action = "tableThree";
    console.log(DataToServer);
    sendDataToServer();
}