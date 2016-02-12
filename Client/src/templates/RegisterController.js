var app = angular.module('ticketToRide');

app.controller('registerController', function($scope) {
    $scope.submit = function() {
        console.log("Registering");
        alert("Registering");
	} 
});
                       