var app = angular.module('ticketToRide');

app.controller('forgotController', function ($scope, ClientAPI) {
    $scope.submit = function () {
        ClientAPI.forgotUserNameOrPassword();
    };
});