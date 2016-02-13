var app = angular.module('ticketToRide');

app.controller('loginController', function ($scope, ClientAPI) {
    $scope.submit = function () {
        ClientAPI.login("username", "password");
    };
});