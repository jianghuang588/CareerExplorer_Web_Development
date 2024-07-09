angular.module("myApp",[])
    .controller("myController", function($scope) {

    // After you click the button on update page, the button will direct to the page that you want to go.
    $scope.data=function() {
        window.location.href = "http://localhost:40013/data.html";
    };

    $scope.delete=function() {
        window.location.href = "http://localhost:40013/delete.html";
    };

    $scope.updateUser=function() {
        window.location.href = "http://localhost:40013/updateUser.html";
    };

 
});
