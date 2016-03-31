var app = angular.module('ticketToRide');

app.controller('loginController', function ($rootScope, $scope, $state, ClientAPI, ModelFacade) {

    $scope.submit = function () {
        var badCredentials = false;

        if($scope.username.length > 25)
        {
            alert("Error: Username exceeds the 25 characters");
            badCredentials = true;
        }
        else if($scope.username.length < 4)
        {
            alert("Error: Username must be at least 4 characters");
            badCredentials = true;
        }

        if($scope.password.length > 25)
        {
            alert("Error: Password exceeds the 25 characters");
            badCredentials = true;
        }
        else if($scope.password.length < 4)
        {
            alert("Error: Password must be at least 4 characters");
            badCredentials = true;
        }

        if(!badCredentials)
        {
            ClientAPI.login($scope.username, $scope.password);
        }
    };

    $rootScope.$on('server:Login', function (event, parameters) {
    	if(parameters.description == "success")
    	{
			$rootScope.userName = $scope.username;
    		$state.go('gameLobby');
    	}
    	else if (parameters.description == "alreadyloggedin") {
    		alert("You are currently logged in already!");
            $state.go('gameLobby');
    	} else if(parameters.description == "invalid input") {
    		alert("Error: Invalid Input");
    	} else {
    		alert("Unexpected Server description: " + parameters.description);
    	}
    });
});
