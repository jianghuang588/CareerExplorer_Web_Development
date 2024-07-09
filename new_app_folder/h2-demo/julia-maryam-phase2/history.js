// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: null
};

let jsonDataFromServer = {
    title: null,
    meeting: null,
    rating: null
}

let jsonCountFromServer = 0;

angular.module("myApp",[])
    .controller("myController", function($scope) {
    $scope.data = [{}];
    setTimeout(function(){
        getHistory($scope);
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
    jsonDataFromServer.title = jsonObject.title;
    println(jsonDataFromServer.title);
    jsonDataFromServer.meeting = jsonObject.meeting;
    println("date of meeting: " + jsonDataFromServer.meeting);
    jsonDataFromServer.rating = jsonObject.rating;
    println("rating: " + jsonDataFromServer.rating);
    println("<br>");
    console.log(jsonDataFromServer);
}

function getBCNO () {
    bcno = sessionStorage.getItem("BCNO");
    return bcno;
}

function getHistory($scope) {
    bcno = getBCNO();
    DataToServer.infoClass1 = bcno;
    DataToServer.action = "userCount";
    sendBCToServer($scope);
    setTimeout(function(){
        count = jsonCountFromServer;
        console.log(count);
        DataToServer.action = "historyInfo";
        for (i=1; i<= count; i++) {
            DataToServer.infoClass2 = i;
            sendDataToServer($scope);
        };
    }, 1000);
    
}

function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}