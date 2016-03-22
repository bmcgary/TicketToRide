var app = angular.module('ticketToRide');

app.controller('registerController', function ($rootScope, $scope, $state, ClientAPI, ModelFacade) {
    var username = "";

    $scope.submit = function () {
        if($scope.username.length > 25 || $scope.username.length < 4 ||
        $scope.password.length > 25 || $scope.password.length < 4 ||
        $scope.email.length > 25 || $scope.email.length < 4) {
            alert("Error: Invalid input constraints.");
        } else {
            this.username = $scope.username;
            ClientAPI.register($scope.username, $scope.password, $scope.email);
        }
    };
    $rootScope.$on('server:Register', function (event, parameters) {
    	if(parameters.description == "success") {
            ModelFacade.setUsername(this.username);
            $state.go('gameLobby');
    	} else if(parameters.description == "alreadyregistered") {
    		alert("You are already a member!");
    	} else if (parameters.description == "alreadyloggedin") {
    		alert("You are currently logged in already!");
    	} else if(parameters.description == "invalid input") {
    		alert("Error: Invalid Input");
    	} else {
    		alert("Unexpected Server description: " + parameters.description);
    	}
    });
});
                       