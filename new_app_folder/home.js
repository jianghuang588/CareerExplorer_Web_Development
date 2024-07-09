angular.module("myApp",[])
    .controller("myController", function($scope) {

    // After you click the button on home page, the button will direct to the page that you want to go.
    $scope.profile=function() {
        window.location.href = "http://localhost:40013/profile.html";
    };

    $scope.information=function() {
        window.location.href = "http://localhost:40013/information.html";
    };

    $scope.retire=function() {
        window.location.href = "http://localhost:40013/retire.html";
    };

     $scope.graduation=function() {
        window.location.href = "http://localhost:40013/graduation.html";
    };
    
    $scope.tranning=function() {
        window.location.href = "http://localhost:40013/tranning.html";
    };
    
});
