var app = angular.module('ticketToRide');

app.controller('mainGameCanvasCtrl', function ($scope, ClientAPI) {

	$scope.display = function()
	{
		console.log("Game canvas button has been pressed");
	}
});
