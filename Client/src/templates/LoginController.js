var app = angular.module('ticketToRide');

app.controller('loginController', function($scope) {
    $scope.submit = function() {
        console.log("Logging in");
        alert("Logging in");
    };
});