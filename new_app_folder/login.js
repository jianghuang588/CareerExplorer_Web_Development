// URL to servlet:
let url = "http://localhost:40013/workers";

// What we will send over.
// Receive the data and send the data to the servlet.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: null
};

let jsonDataFromServer = {
    uid: null
}

// Check the validation of username and password. 
angular.module("myApp",[])
    .controller("myController", function($scope) {
    $scope.submit=function () {
        if (checkInput($scope)) {
            DataToServer.infoClass1 = $scope.user.username;
            DataToServer.infoClass2 = $scope.user.password;
            DataToServer.action = "login";
            sendDataToServer();
        } else {
             println ("invalid input");
        }  
    };
});

// Check the validation of password.
function checkInput ($scope) {
    if ($scope.myForm.$valid) {
        if (($scope.user.password.length > 6) &&($scope.user.password.length <12)) {
            return true;
        } else {
        println ("password not of valid length");
        }
    }
    return false;
}

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
    jsonDataFromServer.uid = jsonObject.uid;
    if (jsonDataFromServer.uid == null) {
        println ("Incorrect username and password try again");
    
    } else {
        setUID(jsonDataFromServer.uid);
        window.location.href = "http://localhost:40013/home.html";
    }

    // Debugging  
    console.log (jsonObject);
}

// print the result on browser. 
function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}

// Set up the UID so the browser know if you enter the valid information.
function setUID (UID) {
    userID = UID.toString();
    sessionStorage.setItem("UID", userID);
}