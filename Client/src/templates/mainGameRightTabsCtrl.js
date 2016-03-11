var app = angular.module('ticketToRide');

app.controller('mainGameRightTabsCtrl', function ($scope, ClientAPI) {

	$scope.display = function()
	{
		console.log("Tabs portions button has been pressed");
	}
});
