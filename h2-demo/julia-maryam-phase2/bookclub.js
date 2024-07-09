// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "bookclub"
};

let jsonDataFromServer = {
    bcno: "",
    bcname: "", 
}


let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    myBookClub($scope);
    setTimeout(function(){
        $scope.bcname = jsonDataFromServer.bcname;
        $scope.$apply();
        setBCNO(jsonDataFromServer.bcno);
    }, 1000);
    $scope.current=function() {
        window.location.href = "http://localhost:40013/currentbook.html";
    };
    $scope.next=function() {
        window.location.href = "http://localhost:40013/nextbook.html";
    };
    $scope.members=function() {
        window.location.href = "http://localhost:40013/members.html";
    };
    $scope.history=function() {
        window.location.href = "http://localhost:40013/history.html";
    }
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
    jsonDataFromServer.bcno = jsonObject.bcno;
    jsonDataFromServer.bcname = jsonObject.bcname;
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
    console.log(DataToServer);
    sendDataToServer();
}

function setBCNO(bcno) {
    BCNO = bcno.toString();
    sessionStorage.setItem("BCNO", BCNO);
}