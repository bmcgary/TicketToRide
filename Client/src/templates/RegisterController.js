var app = angular.module('ticketToRide');

app.controller('registerController', function ($rootScope, $scope, $state, ClientAPI, ModelFacade) {

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

        if($scope.email.length > 254)
        {
            alert("Error: Email exceeds the 254 characters");
            badCredentials = true;
        }

        if(!badCredentials)
        {
            ClientAPI.register($scope.username, $scope.password, $scope.email);
        }
    };
    $rootScope.$on('server:Register', function (event, parameters) {
    	if(parameters.description == "success")
    	{
            $state.go('gameLobby');
    	}
    	else if(parameters.description == "alreadyregistered") {
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
                       