var app = angular.module('ticketToRide');

app.controller('forgotController', function ($rootScope, $scope, ClientAPI, ModelFacade) {
    $scope.submit = function () {
        ClientAPI.forgotUserNameOrPassword();
    };
});