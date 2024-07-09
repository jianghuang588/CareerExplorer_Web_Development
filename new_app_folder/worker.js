angular.module("myApp",[])
    .controller("myController", function($scope) {

    // After you click the button on worker page, the button will direct to the page that you want to go.

    $scope.salary=function() {
        window.location.href = "http://localhost:40013/salary.html";
    };
    $scope.pay=function() {
        window.location.href = "http://localhost:40013/pay.html";
    };
    $scope.vacation=function() {
        window.location.href = "http://localhost:40013/vacation.html";
    };
    $scope.negative=function() {
        window.location.href = "http://localhost:40013/negative.html";
    };

});
