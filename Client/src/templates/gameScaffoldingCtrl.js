var app = angular.module('ticketToRide');

app.controller('gameScaffoldingCtrl', function ($rootScope, $scope, ClientAPI, $spMenu, $uibModal) {
/*	$scope.showMenu = false;
    $scope.toggleMenu = function(){
        $scope.showMenu = !$scope.showMenu;
		$spMenu.toggle();
    }
	*/

	var modalInstance;
	
	$scope.currentTurn = 'yourTurn';
		
	$scope.toggleTurn = function()
	{
		/*if($scope.currentTurn === 'yourTurn')
			$scope.currentTurn = 'notYourTurn';
		else
			$scope.currentTurn = 'yourTurn';*/

		ClientAPI.startGame($scope.currentGameId);
	}

	$scope.changeGame = function(game)
	{
		console.log("Change game to this game Id: " + game);
	}

//--------------------- Main game Broadcast listeners -----------------------------------
// There are other listeners that are in specific controllers. EG: when clicking buy a destination 
// it will fire off a call to the server from that click. The return broadcast will be listended for 
// in the buy destination modol controller. The listed main game listeners are here.
/*
	"SendClientModelInformation": 
    "BuyRoute": 
    "DrawTrainCard": 
    "NotifyDestinationRouteCompleted": 
    "GetDestinations": 
    "SelectDestinations": 
    "GameEnded": 
*/

    $rootScope.$on('model:UpdateUserGames', function (event, parameters) {
		//TODO how to do i know if the game has already started?? 
		var ppl = [];
		for (var index = 0; index < parameters.getOpponentsSize(); index++)
		{
			ppl.push({'name': parameters.getOpponentName(index), 'color': parameters.getOpponentColor(index)})
		}
		
		waitingToStartModalModal(ppl, true);
    });

    $rootScope.$on('model:StartGame', function (event, parameters) {
        //future
		waitingToStartModalModal(ppl, false);
    });

    $rootScope.$on('model:SendChat', function (event, parameters) {
        //future

    });


    $rootScope.$on('model:BuyRoute', function (event, parameters) {
        //do logic
		
    });


    $rootScope.$on('model:DrawTrainCard', function (event, parameters) {
		//do logic
		alert("HERE");
    });


    $rootScope.$on('model:GetDestinations', function (event, parameters) {
		console.log(parameters);
		//This is mostly ceremonial having this here. 
		//This is actually caught in the mainGameRightTabsCtrl and handled there
    });


    $rootScope.$on('model:SelectDestinations', function (event, parameters) {
		//do logic

    });

    $rootScope.$on('model:TurnStartedNotification', function (event, parameters) {
		//check if it is this players turn. if so set 
		/*	$scope.currentTurn === 'yourTurn'
		ELSE
			$scope.currentTurn = 'yourTurn';*/

    });


	$rootScope.$on('TESTER',function(event, params)
	{
		alert("in ctrl");
	});
//--------------- Over all info thats helpful to have -------------------------
$scope.currentGameId = 1; //ModelContainer.getGameId() //I assume ModelContainer is what i am passed in the broadcast???

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

//---------------------------------- Waiting to start Modal
	function waitingToStartModalModal(ppl, open)
	{
		if(open)
		{
			modalInstance = $uibModal.open({
				  animation: true,
				  templateUrl: 'waitingToStartGameModal.html',
				  controller: 'waitingToStartModalCtrl',
				  backdrop : 'static',
				  keyboard: false,
				  //size: size,
				  resolve: 
				  {
					   ppl: function () 
	 				   {
						 return ppl;
					   }
				  }
				});

			modalInstance.result.then(
				function ()  //they selected stuff
				{
					modalInstance.dismiss('ok');
			  		//console.log(selectedItems); //from here ship it out via the ClientAPI
					ClientAPI.startGame($scope.currentGameId);
				});//dont need a function for canceling since that isn't allowed
		}
		else
		{
			modalInstance.dismiss('ok');
		}
	}

});

// Waiting To Start Game modal's controller ------------------------------------------------------------------------
app.controller('waitingToStartModalCtrl', function ($scope, $uibModalInstance, ppl) {

  $scope.alert = {showAlert: false, message: "", type:""};	
  $scope.ppl = ppl;

/*
		parameters.model.player.playerName / playerColor
		parameters.model.opponents[i].playerName / playerColor
*/
  $scope.ok = function () {

	if($scope.ppl.length > 1)
    	$uibModalInstance.close();
	else
		showAlert("You need at least two players before you can begin", 'danger');
  };


	function showAlert(message, type)
	{
		$scope.alert.showAlert = true;
		$scope.alert.message = message;
		$scope.alert.type = type;
	}

});

