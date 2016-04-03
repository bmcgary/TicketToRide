var app = angular.module('ticketToRide');

app.controller('gameScaffoldingCtrl', function ($scope, ClientAPI, $spMenu) {
	$scope.showMenu = false;
    $scope.toggleMenu = function(){
        $scope.showMenu = !$scope.showMenu;
		$spMenu.toggle();
    }

	$scope.changeGame = function(game)
	{
		console.log("Change game to this game Id: " + game);
	}

$scope.games = [

	{
    name: "GAME AWESOME",
    link: "#/game",
	gameId: 1,
	action:$scope.changeGame
  }, {
    name: "Joey's Game",
    link: "#/game",
	gameId: 2,
	action:$scope.changeGame
  },{
    name: "Noobs only",
    link: "#/game",
	gameId: 3,
	action:$scope.changeGame
  },{
    name: "Only people that dont suck only",
    link: "#/game",
	gameId: 4,
	action:$scope.changeGame
  }/*{
    name: "States",
    link: "#",
    subtree: [{
      name: "state 1",
      link: "state1",
      subtree: [{name: "state 1",
      link: "state1"}]
    }, {
      name: "state 2",
      link: "state2"
    }]
  }, {
    name: "No states",
    link: "#/game",
    subtree: [{
      name: "no state connected",
      link: "#"
    }]
  }, {
    name: "divider",
    link: "#/game"

  },*/ ];
});
