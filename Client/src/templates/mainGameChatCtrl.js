var app = angular.module('ticketToRide');

app.controller('mainGameChatCtrl', function ($scope, ClientAPI) {

	$scope.display = function()
	{
		console.log("game chat button has been pressed");
	}
});
