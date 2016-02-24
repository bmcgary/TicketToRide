var app = angular.module('ticketToRide');

app.controller('gameScaffoldingCtrl', function ($scope, ClientAPI, $spMenu) {
	$scope.showMenu = false;
    $scope.toggleMenu = function(){
        $scope.showMenu = !$scope.showMenu;
		$spMenu.toggle();
    }
});
