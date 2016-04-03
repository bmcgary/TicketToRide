var app = angular.module('ticketToRide');

app.controller('loginController', function ($rootScope, $scope, ClientAPI) {
    $scope.submit = function () {
        ClientAPI.login($scope.username, $scope.password);
    };
    $rootScope.$on('server:Login', function (event, parameters) {
    	alert("Listener reached");
    });
});