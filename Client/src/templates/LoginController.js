var app = angular.module('ticketToRide');

app.controller('loginController', function ($rootScope, $scope, ClientAPI) {
    $scope.submit = function () {
        ClientAPI.login($scope.username, $scope.password);
    };
    $rootScope.$on('server:Login', function (event, parameters) {
    	if(parameters.description == "success") {
    		$state.go('gameLobby');
    	} else if (parameters.description == "alreadyloggedin") {
    		alert("You are currently logged in already!");
    	} else if(parameters.description == "invalid input") {
    		alert("Error: Invalid Input");
    	} else {
    		alert("Unexpected Server description...");
    	}
    });
});