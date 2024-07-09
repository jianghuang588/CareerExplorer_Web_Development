let app = angular.module("myApp", []);


app.controller("myController", function($scope) {
    $scope.submit=function () {
        sessionStorage.setItem("UID", null);
        window.location.href = "http://localhost:40013/login.html";
    };
});
