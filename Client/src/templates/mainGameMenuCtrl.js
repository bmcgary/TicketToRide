var app = angular.module('ticketToRide');

app.controller('mainGameMenuCtrl', function ($scope, ClientAPI) {

	$scope.display = function()
	{
		console.log("game menu button has been pressed");
	}
});
