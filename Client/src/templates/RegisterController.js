var app = angular.module('ticketToRide');

app.controller('registerController', function ($scope, ClientAPI) {
    $scope.submit = function () {
        ClientAPI.register($scope.username, $scope.password, $scope.email);
    };
});
                       