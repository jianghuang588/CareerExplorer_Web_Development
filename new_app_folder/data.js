// URL to servlet:
let url = "http://localhost:40013/workers";

// What we will send over.
// Receive the data and send the data to the servlet.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "data"
};

// JSON/Javascript object to collect all the form data.
let jsonDataFromServer = {
    USERNAME: "",
    PASSWORD: "",
    FIRSTNAME: "",
    LASTNAME: "",
    EMAIL: "",
    CarrerChoice: "",
    salaryID: "",
    YEAR: "",
    SEASON: "",
}


var USERNAME = "";
var PASSWORD = "";
var FIRSTNAME = "";
var LASTNAME = "";
var EMAIL = "";
var CarrerChoice = "";
var salaryID = "";
var YEAR = "";
var SEASON = "";



let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    myBookClub($scope);
    setTimeout(function(){

        // Getting the data from html page.
        $scope.USERNAME = jsonDataFromServer.USERNAME;

        // Debugging
        console.log("USERNAME: " + $scope.USERNAME);
        
        $scope.PASSWORD = jsonDataFromServer.PASSWORD;
        console.log(" PASSWORD: " + $scope.PASSWORD);

        $scope.FIRSTNAME = jsonDataFromServer.FIRSTNAME;
        console.log(" FIRSTNAME: " + $scope.FIRSTNAME);

        $scope.LASTNAME = jsonDataFromServer.LASTNAME;
        console.log("LASTNAME: " + $scope.LASTNAME);
        //$scope.firstName = "Julia";
        $scope.EMAIL = jsonDataFromServer.EMAIL;
        console.log(" EMAIL: " + $scope.EMAIL);

        $scope.CarrerChoice = jsonDataFromServer.CarrerChoice;
        console.log(" CarrerChoice: " + $scope.CarrerChoice);

        $scope.salaryID = jsonDataFromServer.salaryID;
        console.log("salaryID: " + $scope.salaryID);
        //$scope.firstName = "Julia";
        $scope.YEAR = jsonDataFromServer.YEAR;
        console.log(" YEAR: " + $scope.YEAR);

        $scope.SEASON = jsonDataFromServer.SEASON;
        console.log(" SEASON: " + $scope.SEASON);

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

    // Debbuging 
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

// Receive the data data from server.
// we can request the data or user information from database.
function requestListener () {
    let jsonObject = JSON.parse (this.responseText);
    
    jsonDataFromServer.USERNAME = jsonObject.USERNAME;
    jsonDataFromServer.PASSWORD = jsonObject.PASSWORD;
    jsonDataFromServer.FIRSTNAME = jsonObject.FIRSTNAME;
    jsonDataFromServer.LASTNAME = jsonObject.LASTNAME;
    jsonDataFromServer.EMAIL = jsonObject.EMAIL;
    jsonDataFromServer.CarrerChoice = jsonObject.CarrerChoice;
    jsonDataFromServer.salaryID = jsonObject.salaryID;
    jsonDataFromServer.YEAR = jsonObject.YEAR;
    jsonDataFromServer.SEASON = jsonObject.SEASON;
    

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
// Sending and receiving information from the servlet to get the data information.
function myBookClub($scope) {
    uid = getUID();
    DataToServer.infoClass1 = uid;
    DataToServer.action = "data";
    // Debugging
    console.log(DataToServer);
    sendDataToServer();
}