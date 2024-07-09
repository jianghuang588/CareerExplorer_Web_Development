// URL to servlet:
let url = "http://localhost:40013/bookclub";

// What we will send over.
let DataToServer = {
    infoClass1: null,
    infoClass2: null,
    action: "suggestion"
};

let jsonDataFromServer = {
    bcno: null
}

angular.module("myApp",[])
    .controller("myController", function($scope) {
    $scope.submit=function () {
       DataToServer.infoClass1 = getBCNO();
       DataToServer.infoClass2 = $scope.suggestion;
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
    jsonDataFromServer.bcno = jsonObject.bcno;
    if (jsonDataFromServer.bcno == null) {
        println ("something went wrong");
    } else {
        println ("submitted!")
    }
    console.log (jsonObject);
}

function println (outputStr) {
    document.getElementById("output").innerHTML += outputStr + "<br>";
}

function getBCNO () {
    bcno = sessionStorage.getItem("BCNO");
    return bcno;
}
