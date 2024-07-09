// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: null
};

let jsonDataFromServer = {
    name: null,
    email: null,
    year: null
}

let jsonCountFromServer = null

angular.module("myApp",[])
    .controller("myController", function($scope) {
    $scope.data = [{}];
    setTimeout(function(){
        getMembers($scope);
    }, 1000);
});

function sendBCToServer ($scope) {
    let req = new XMLHttpRequest();
    req.addEventListener("load", countRequestListener);
    req.open("POST", url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.send(JSON.stringify(DataToServer));
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

function sendDataToServer ($scope) {
    let req = new XMLHttpRequest();
    req.addEventListener("load", requestListener);
    req.open("POST", url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.send(JSON.stringify(DataToServer));
    console.log("Sent to server: json=" + JSON.stringify(DataToServer));
}

function countRequestListener () {
    let jsonObject = JSON.parse (this.responseText);
    jsonCountFromServer = jsonObject.count;
    console.log("here");
    console.log(jsonCountFromServer);
}

function requestListener () {
    let jsonObject = JSON.parse (this.responseText);
    jsonDataFromServer.name = jsonObject.name;
    println(jsonDataFromServer.name);
    jsonDataFromServer.email = jsonObject.email;
    println(jsonDataFromServer.email);
    jsonDataFromServer.year = jsonObject.year;
    println("in book club since " + jsonDataFromServer.year);
    println("<br>");
    console.log(jsonDataFromServer);
}

function getBCNO () {
    bcno = sessionStorage.getItem("BCNO");
    return bcno;
}

function getMembers($scope) {
    //TODO: implement a method that gets the name, email and year of all users in book club, by first getting the count and then getting these fields for each individual members
}

function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}