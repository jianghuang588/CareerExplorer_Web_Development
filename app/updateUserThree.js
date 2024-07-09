// URL to servlet:
let url = "http://localhost:40013/workers";

// What we will send over.
// Receive the data and send the data to the servlet in form of json.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: null
};

// JSON/Javascript object to collect all the form data.
let jsonDataFromServer = {
    uid: null
}


let app = angular.module("myApp", []);

app.controller("myController", function($scope) {
  uid = getUID();
  DataToServer.infoClass1 = uid;
  
  // Define and use the scope variable. 
  // Getting the information on html page.
$scope.age=function () {
    DataToServer.infoClass2 = $scope.worker.locations;
    DataToServer.action = "age";
    sendDataToServer();
  };

$scope.majors=function () {
    DataToServer.infoClass2 = $scope.worker.salarys;
    DataToServer.action = "majors";
    sendDataToServer();
  };



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

// send the data to the server.
// we will send the data from the login page and receive the information such as name,and id.
function requestListener () {
    let jsonObject = JSON.parse (this.responseText);
       
    if (jsonDataFromServer == null) {
        println ("Something went wrong");
    }  else {
        println ("submitted!");
    }  
    console.log(jsonDataFromServer);
    console.log (jsonObject);
}

// Show the output on the browser.
function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}

// Set the UID in the database so we can update the data from database.
function getUID () {
    uid = sessionStorage.getItem("UID");
    return uid;
}