let app = angular.module("myApp", []);

app.controller("myController", function($scope) {
	// Click the summit button to return to main page.
    $scope.submit=function () {
        sessionStorage.setItem("UID", null);
        window.location.href = "http://localhost:40013/login.html";
    };
});
