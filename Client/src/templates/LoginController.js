var app = angular.module('ticketToRide');

app.controller('loginController', function($scope, ATP) {
    $scope.submit = function() {
        ATP.send("ATP SERVER");
    };
});