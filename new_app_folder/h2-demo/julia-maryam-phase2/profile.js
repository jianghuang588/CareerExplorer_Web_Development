// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "profile"
};

let jsonDataFromServer = {
    firstName: "",
    lastname: "",
    email: "",
    year: "",
    favBook: ""    
}

var firstName = "";
var lastName = "";
var email = "";
var year = "";
var favBook = "";

let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    myBookClub($scope);
    setTimeout(function(){
        $scope.firstName = jsonDataFromServer.firstName;
        console.log("first name: " + $scope.firstName);
        //$scope.firstName = "Julia";
        $scope.lastName = jsonDataFromServer.lastname;
        $scope.email = jsonDataFromServer.email;
        $scope.year = jsonDataFromServer.year;
        $scope.favBook = jsonDataFromServer.favBook;
        $scope.$apply();
    }, 1000);
    
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
    jsonDataFromServer.firstName = jsonObject.firstName;
    jsonDataFromServer.lastName = jsonObject.lastName;
    jsonDataFromServer.email = jsonObject.email;
    jsonDataFromServer.year = jsonObject.year;
    jsonDataFromServer.favBook = jsonObject.favBook;
    if (jsonDataFromServer == null) {
        println ("Something went wrong");
    }    
    console.log(jsonDataFromServer);
    console.log (jsonObject);
}

function getUID () {
    uid = sessionStorage.getItem("UID");
    return uid;
}

function myBookClub($scope) {
    uid = getUID();
    DataToServer.infoClass1 = uid;
    DataToServer.action = "profile";
    console.log(DataToServer);
    sendDataToServer();
}