var app = angular.module('ticketToRide');

app.controller('loginController', function ($rootScope, $scope, ClientAPI, ModelFacade) {
    var username = "";

    $scope.submit = function () {
        if($scope.username.length > 25 || $scope.username.length < 4 ||
        $scope.password.length > 25 || $scope.password.length < 4) {
            alert("Error: Invalid input constraints.");
        } else {
            this.username = $scope.username;
            ClientAPI.login($scope.username, $scope.password);
        }
    };
    $rootScope.$on('server:Login', function (event, parameters) {
    	if(parameters.description == "success") {
            ModelFacade.setUsername(this.username);
    		$state.go('gameLobby');
    	} else if (parameters.description == "alreadyloggedin") {
    		alert("You are currently logged in already!");
            $state.go('gameLobby');
    	} else if(parameters.description == "invalid input") {
    		alert("Error: Invalid Input");
    	} else {
    		alert("Unexpected Server description: " + parameters.description);
    	}
    });
});