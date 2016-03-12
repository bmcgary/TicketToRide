var app = angular.module('ticketToRide');

app.controller('registerController', function ($rootScope, $scope, $state, ClientAPI) {
    $scope.submit = function () {
        ClientAPI.register($scope.username, $scope.password, $scope.email);
    };
    $rootScope.$on('server:Register', function (event, parameters) {
    	if(parameters.description == "success") {
    		$state.go('gameLobby');
    	} else if(parameters.description == "alreadyregistered") {
    		alert("You are already a member!");
    	} else if (parameters.description == "alreadyloggedin") {
    		alert("You are currently logged in already!");
    	} else if(parameters.description == "invalid input") {
    		alert("Error: Invalid Input");
    	} else {
    		alert("Unexpected Server description...");
    	}
    });
});
                       