// URL to servlet:
let url = "http://localhost:40013/workers";

// What we will send over.
// Receive the data and send the data to the servlet in form of json.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "information"
};

// JSON/Javascript object to collect all the form data.
let jsonDataFromServer = {
    LOCATION: "",
    SALARY: "",
    MAJOR: ""
    
}

var LOCATION = "";
var SALARY = "";
var MAJOR = "";


let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    myBookClub($scope);
    setTimeout(function(){


        // Getting the data from html page.
        $scope.LOCATION = jsonDataFromServer.LOCATION;
        // Debugging 
        console.log("LOCATION: " + $scope.LOCATION);

        $scope.SALARY = jsonDataFromServer.SALARY;
        console.log(" SALARY: " + $scope.SALARY);

        $scope.MAJOR = jsonDataFromServer.MAJOR;
        console.log(" MAJOR: " + $scope.MAJOR);

    

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
    jsonDataFromServer.LOCATION = jsonObject.LOCATION;
    jsonDataFromServer.SALARY = jsonObject.SALARY;
    jsonDataFromServer.MAJOR = jsonObject.MAJOR;

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
// Sending and receiving information from the servlet to get the information .
function myBookClub($scope) {
    uid = getUID();
    DataToServer.infoClass1 = uid;
    DataToServer.action = "information";

    // Debugging 
    console.log(DataToServer);
    sendDataToServer();
}