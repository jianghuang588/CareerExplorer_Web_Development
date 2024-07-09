// URL to servlet:
let url = "http://localhost:40013/workers";

// What we will send over.
// Receive the data and send the data to the servlet.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: null
};

// JSON/Javascript object to collect all the form data.
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

// Check the input of password
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

// send the data to the server
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

    //Debugging 
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

// Receive the data data from server.
function requestListener () {

    // Extract the receive JSON into a tempory object.
    let jsonObject = JSON.parse (this.responseText);

    // Pull out from servlet json into our json.
    jsonDataFromServer.uid = jsonObject.uid;
    
    // Check the validation of the password.
    if (jsonDataFromServer.uid == null) {
        println ("Incorrect username and password try again");
    } else {
        setUID(jsonDataFromServer.uid);
        window.location.href = "http://localhost:40013/admin.html";
    }
    console.log (jsonObject);
}

// print the error message on the html page 
function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}

// set UID 
function setUID (UID) {
    userID = UID.toString();
    sessionStorage.setItem("UID", userID);
}