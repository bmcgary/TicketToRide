var app = angular.module('ticketToRide');

app.controller('forgotController', function ($rootScope, $scope, ClientAPI, ModelFacade) {
    $scope.submit = function () {
        ClientAPI.forgotUserNameOrPassword();
    };


    /*
    //Example of listener
	$rootScope.$on('model:CreateGame', function (event, model) {
		alert("Created game id: " + model.getGameId());
	});

	$rootScope.$on('model:SwitchGame', function (event, model) {
		alert("Current game id: " + model.getGameId());
	});
    */
});