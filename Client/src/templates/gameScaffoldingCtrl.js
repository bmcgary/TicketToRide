var app = angular.module('ticketToRide');

app.controller('gameScaffoldingCtrl', function ($rootScope, $scope, ClientAPI, $spMenu, $uibModal, $uibModalStack, ModelFacade) {
/*	$scope.showMenu = false;
    $scope.toggleMenu = function(){
        $scope.showMenu = !$scope.showMenu;
		$spMenu.toggle();
    }
	*/
	$scope.currentTurn = 'yourTurn';
	$scope.allPlayers = [];

	var tempModel = ModelFacade.getGameInView();
	if(tempModel.getOpponentsSize() > 0)
	{
		if(tempModel.isFirstRound())
		{
			if(tempModel.getTemporaryStorageOfDestinationCardsToBeSelectedFrom().length == 0)
			{
				//were are waiting to start
				waitingToStartModalSetUp(tempModel);
			}
			else if(tempModel.getTurnIndex() == tempModel.getPlayerId())
			{
				//its my turn. show selectdest modal
				console.log("show selectdestmodal");
			}
		}
	}
	else
	{
		console.log("Attempted to load model but it wasnt there")
	}
	
		
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

    $rootScope.$on('model:PrivateClientModelInformation', function (event, parameters) {

//------------ check what state the model is in and act acordingly. Show modals, disable and enable stuff etc

		/*var ppl = [];
		for (var index = 0; index < parameters.getOpponentsSize(); index++)
		{
			ppl.push({'name': parameters.getOpponentName(index), 'color': parameters.getOpponentColor(index)})
		}
		*/
		console.log("gameScaffolding PrivateClientModelInformation"	);
		
		/*waitingToStartModalModal();*/console.log(parameters);
    });


    $rootScope.$on('model:UpdateUserGames', function (event, parameters) {
		console.log("In gameScaffolding updateUserGames");
		waitingToStartModalSetUp(parameters);
    });

    $rootScope.$on('model:StartGame', function (event, parameters) {
		//close the waitingToStartModal
		$uibModalStack.dismissAll();
    });

    $rootScope.$on('model:SetGameInView', function (event, parameters) {
		//parameters is the entire model container
        $scope.currentGameId = parameters.getGameId();
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
		if(parameters.getTurnIndex() == parameters.getPlayerId())
		{
			$scope.currentTurn = 'yourTurn';
			if(parameters.isFirstRound())
			{
				$rootScope.$broadcast('model:GetDestinations',parameters);
				//$rootScope.emit('model:GetDestinations',parameters);
			}
		}
		else
		{
			$scope.currentTurn = 'notYourTurn';
		}

    });


	$rootScope.$on('TESTER',function(event, params)
	{
		alert("in ctrl");
	});

	function waitingToStartModalSetUp(parameters)
	{
		var iAmTheCreator = false;
		$scope.allPlayers.length = 0;
		for (var index = 0; index < parameters.getOpponentsSize(); index++)
		{
			if(index == 0 && $rootScope.userName == parameters.getOpponentName(index))
				iAmTheCreator = true;
			$scope.allPlayers.push({'name': parameters.getOpponentName(index), 'color': parameters.getOpponentColor(index)})
		}
		
		$scope.currentGameId = parameters.getGameId();
		//TODO check if the game has started. if not show this, else nothing
			waitingToStartModalModal(iAmTheCreator);
	}
//--------------- Over all info thats helpful to have -------------------------
$scope.currentGameId = -1; //ModelContainer.getGameId() //I assume ModelContainer is what i am passed in the broadcast???

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
	function waitingToStartModalModal(iAmTheCreator)
	{
		if(!$uibModalStack.getTop()) //only show if the modal doenst already exist
		{
			var modalInstance = $uibModal.open({
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
						 return $scope.allPlayers;
					   },
					   iAmTheCreator : function()
					   {
						 return iAmTheCreator;
   					   }
				  }
				});

			modalInstance.result.then(
				function ()  //they selected stuff
				{
			  		//console.log(selectedItems); //from here ship it out via the ClientAPI
					ClientAPI.startGame($scope.currentGameId);
				});//dont need a function for canceling since that isn't allowed
		}
	}

});

// Waiting To Start Game modal's controller ------------------------------------------------------------------------
app.controller('waitingToStartModalCtrl', function ($scope, $uibModalInstance, ppl, iAmTheCreator) {

  $scope.alert = {showAlert: false, message: "", type:""};	
  $scope.ppl = ppl;
  $scope.showStart = iAmTheCreator;
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

