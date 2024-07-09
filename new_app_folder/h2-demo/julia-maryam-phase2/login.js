// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: null
};

let jsonDataFromServer = {
    uid: null
}

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
    $scope.signup=function () {
        window.location.href = "http://localhost:40013/signup.html";
    }
});

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
function sendDataToServer ($scope) {
    let req = new XMLHttpRequest();
    req.addEventListener("load", requestListener);
    req.open("POST", url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.send(JSON.stringify(DataToServer));
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

function requestListener () {
    let jsonObject = JSON.parse (this.responseText);
    jsonDataFromServer.uid = jsonObject.uid;
    if (jsonDataFromServer.uid == null) {
        println ("Incorrect username and password try again");
    } else {
        setUID(jsonDataFromServer.uid);
        window.location.href = "http://localhost:40013/home.html";
    }
    console.log (jsonObject);
}

function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}

function setUID (UID) {
    userID = UID.toString();
    sessionStorage.setItem("UID", userID);
}