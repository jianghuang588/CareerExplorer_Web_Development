angular.module("myApp",[])
    .controller("myController", function($scope) {
    $scope.profile=function() {
        window.location.href = "http://localhost:40013/profile.html";
    };
    $scope.bookclub=function() {
        window.location.href = "http://localhost:40013/bookclub.html";
    };
    $scope.editprofile=function() {
        window.location.href = "http://localhost:40013/editprofile.html";
    };
});
