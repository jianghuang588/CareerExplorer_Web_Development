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

let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    uid = getUID();
    DataToServer.infoClass1 = uid;
    $scope.username=function () {
        DataToServer.infoClass2 = $scope.user.username;
        DataToServer.action = "username";
        sendDataToServer();
    };
    $scope.firstName=function () {
        DataToServer.infoClass2 = $scope.user.firstName;
        DataToServer.action = "firstName";
        sendDataToServer();
    };
    $scope.lastName=function () {
        DataToServer.infoClass2 = $scope.user.lastName;
        DataToServer.action = "lastName";
        sendDataToServer();
    };
    $scope.email=function () {
        DataToServer.infoClass2 = $scope.user.email;
        DataToServer.action = "email";
        sendDataToServer();
    };
    $scope.favBook=function () {
        DataToServer.infoClass2 = $scope.user.favBook;
        DataToServer.action = "favBook";
        sendDataToServer();
    };
});


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
        println ("something went wrong");
    } else {
        println ("updated");
    }
    console.log (jsonObject);
}

function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}

function getUID () {
    uid = sessionStorage.getItem("UID");
    return uid;
}