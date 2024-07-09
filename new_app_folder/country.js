angular.module("myApp",[])
    .controller("myController", function($scope) {

    // After you click the button on country page, the button will direct to the page that you want to go.
    $scope.BestSalary=function() {
        window.location.href = "http://localhost:40013/BestSalary.html";
    };
    $scope.Payment=function() {
        window.location.href = "http://localhost:40013/Payment.html";
    };
    $scope.HighPopulation=function() {
        window.location.href = "http://localhost:40013/HighPopulation.html";
    };
    $scope.Population=function() {
        window.location.href = "http://localhost:40013/Population.html";
    };


});
