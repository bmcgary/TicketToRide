var app = angular.module('ticketToRide');

app.controller('gameScaffoldingCtrl', function ($scope, ClientAPI, $spMenu) {
	$scope.showMenu = false;
    $scope.toggleMenu = function(){
        $scope.showMenu = !$scope.showMenu;
		$spMenu.toggle();
    }

	$scope.allGames = [{name:"game1"},{name:'game2'},{name:'game3'}];
});
