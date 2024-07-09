// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "current"
};

let jsonDataFromServer = {
    currentBook: "",
    author: "",
    description: "",
    date: "",
    loc: ""
}

let jsonCountFromServer = null

let jsonProgressFromServer = {
    name: "",
    page: "",
}


let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    currentBook($scope);
    setTimeout(function(){
        $scope.bookname = jsonDataFromServer.currentBook;
        $scope.author = jsonDataFromServer.author;
        $scope.description = jsonDataFromServer.description;
        $scope.date = jsonDataFromServer.date;
        $scope.loc = jsonDataFromServer.loc;
        $scope.$apply();
    }, 1000);
    getMemberProgress($scope);
    
});

function sendDataToServer ($scope) {
    let req = new XMLHttpRequest();
    req.addEventListener("load", requestListener);
    req.open("POST", url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.send(JSON.stringify(DataToServer));
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

function sendBCToServer ($scope) {
    let req = new XMLHttpRequest();
    req.addEventListener("load", countRequestListener);
    req.open("POST", url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.send(JSON.stringify(DataToServer));
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

function sendProgToServer ($scope) {
    let req = new XMLHttpRequest();
    req.addEventListener("load", progressRequestListener);
    req.open("POST", url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.send(JSON.stringify(DataToServer));
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

function countRequestListener () {
    let jsonObject = JSON.parse (this.responseText);
    jsonCountFromServer = jsonObject.count;
    console.log(jsonCountFromServer);
}

function progressRequestListener () {
    let jsonObject = JSON.parse (this.responseText);
    jsonProgressFromServer.name = jsonObject.name;
    jsonProgressFromServer.page = jsonObject.page;
    println(jsonProgressFromServer.name + " is on page " + jsonProgressFromServer.page);
    println("<br>");
    console.log(jsonProgressFromServer);
    if (jsonProgressFromServer == null) {
        println ("Something went wrong");
    }    
}

function requestListener () {
    let jsonObject = JSON.parse (this.responseText);
    jsonDataFromServer.currentBook = jsonObject.currentBook;
    jsonDataFromServer.author = jsonObject.author;
    jsonDataFromServer.description = jsonObject.description;
    jsonDataFromServer.date = jsonObject.date;
    jsonDataFromServer.loc = jsonObject.loc;
    if (jsonDataFromServer == null) {
        println ("Something went wrong");
    }    
    console.log(jsonDataFromServer);
    console.log (jsonObject);
}

function getBCNO () {
    bcno = sessionStorage.getItem("BCNO");
    return bcno;
}

function currentBook($scope) {
    bcno = getBCNO();
    DataToServer.infoClass1 = bcno;
    console.log(DataToServer);
    sendDataToServer();
}

function getMemberProgress($scope) {
    bcno = getBCNO();
    DataToServer.infoClass1 = bcno;
    DataToServer.action = "userCount";
    sendBCToServer($scope);
    setTimeout(function(){
        count = jsonCountFromServer;
        console.log(count);
        DataToServer.action = "progressInfo";
        for (i=1; i<= count; i++) {
            DataToServer.infoClass2 = i;
            sendProgToServer($scope);
        };
    }, 1000);
    
}

function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}