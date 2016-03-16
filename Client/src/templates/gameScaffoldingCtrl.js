var app = angular.module('ticketToRide');

app.controller('gameScaffoldingCtrl', function ($rootScope, $scope, ClientAPI, $spMenu) {
/*	$scope.showMenu = false;
    $scope.toggleMenu = function(){
        $scope.showMenu = !$scope.showMenu;
		$spMenu.toggle();
    }
	*/

	
	$scope.currentTurn = 'yourTurn';
		
	$scope.toggleTurn = function()
	{
		if($scope.currentTurn === 'yourTurn')
			$scope.currentTurn = 'notYourTurn';
		else
			$scope.currentTurn = 'yourTurn';
	}

	$scope.changeGame = function(game)
	{
		console.log("Change game to this game Id: " + game);
	}

	/*$rootScope.on('model:SwitchGame',function(event, params)
	{

	});*/
//----------------------- Main menu data --------------------------------------
$scope.thisPlayer = {
	playerName:'player 4',
	playerColor:'green',
	points:6,
	trainsLeft:7,
	playerId:4
};
$scope.opponents = [{
	playerName:'player 1',
	playerColor:'blue',
	points:10,
	trainsLeft:15,
	playerId:0
},
{
	playerName:'player 2',
	playerColor:'black',
	points:1,
	trainsLeft:1,
	playerId:1
},
{
	playerName:'player 3',
	playerColor:'yellow',
	points:5,
	trainsLeft:5,
	playerId:2
},
{
	playerName:'player 4',
	playerColor:'orange',
	points:6,
	trainsLeft:7,
	playerId:3
}];


//----------------------- Right tabed section's model data --------------------
$scope.cardsVisible = ['black','yellow', 'blue', 'green', 'wild'];

//----------------------- Bottom tabed section's model data -------------------
$scope.destinations = [
	{cityName1:'CITY 1',cityName2: 'CITY 2',isComplete: true,points:10},
	{cityName1:'CITY 3',cityName2: 'CITY 4',isComplete: false,points:10},
	{cityName1:'CITY 5',cityName2: 'CITY 6',isComplete: true,points:10},
	{cityName1:'CITY 7',cityName2: 'CITY 8',isComplete: true,points:10},
	{cityName1:'CITY 9',cityName2: 'CITY 10',isComplete: true,points:10},
	{cityName1:'CITY 11',cityName2: 'CITY 12',isComplete: true,points:10},
	{cityName1:'CITY 13',cityName2: 'CITY 14',isComplete: true,points:10},
	{cityName1:'CITY 15',cityName2: 'CITY 16',isComplete: true,points:10},
	{cityName1:'CITY 17',cityName2: 'CITY 18',isComplete: true,points:10},
	{cityName1:'CITY 19',cityName2: 'CITY 20',isComplete: true,points:10},
	{cityName1:'CITY 21',cityName2: 'CITY 22',isComplete: true,points:10},
	{cityName1:'CITY 23',cityName2: 'CITY 24',isComplete: true,points:10},
	{cityName1:'CITY 25',cityName2: 'CITY 26',isComplete: true,points:10},
	{cityName1:'CITY 27',cityName2: 'CITY 28',isComplete: true,points:10},
	{cityName1:'CITY 29',cityName2: 'CITY 30',isComplete: true,points:10},
	{cityName1:'CITY 31',cityName2: 'CITY 32',isComplete: true,points:10}];

$scope.playersTrainCards = 
{
	'blue':0,
	'black':1,
	'green':2,
	'yellow':3,
	'orange':4,
	'purple':5,
	'red':6,
	'white':7,
	'wild':8
}

//------------------------------ Stuff in the nav bar -------------------------
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
  }];
});
