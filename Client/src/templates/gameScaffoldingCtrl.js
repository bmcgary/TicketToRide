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
	var waitingModalInstance;

//--------------- Over all info thats helpful to have -------------------------
$scope.currentGameId = -1; 
$scope.topNavMessage = "Waiting to Start the Game";

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
$scope.selectDestOnRight = false;

//----------------------- Bottom tabed section's model data -------------------
$scope.destinations = [	/*{cityName1:'CITY 1',cityName2: 'CITY 2',isComplete: true,points:10},
	{cityName1:'CITY 3',cityName2: 'CITY 4',isComplete: false,points:10}*/];

$scope.playersTrainCards = 
{
	'blue':0,
	'black':0,
	'green':0,
	'yellow':0,
	'orange':0,
	'purple':0,
	'red':0,
	'white':0,
	'wild':0
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


//------------------------------ Methods and jank -------------------------------------
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

		//ClientAPI.startGame($scope.currentGameId);
		$scope.playersTrainCards['blue']++;		
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
		fillViewFromModel(parameters);
    });

    $rootScope.$on('model:PublicClientModelInformation', function (event, parameters) {
		fillViewFromModel(parameters);
    });

    $rootScope.$on('model:UpdateUserGames', function (event, parameters) {
		console.log("In gameScaffolding updateUserGames");
		waitingToStartModalSetUp(parameters);
    });

    $rootScope.$on('model:StartGame', function (event, parameters) {
		//close the waitingToStartModal
//		$uibModalStack.dismissAll();
		if(typeof(waitingModalInstance) !== 'undefined')
			waitingModalInstance.dismiss("cancel");
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
		fillViewFromModel(parameters); 
    });

    $rootScope.$on('model:AvailableTrainCardsNotification', function (event, parameters) {
		fillViewFromModel(parameters); 
    });


    $rootScope.$on('model:GetDestinations', function (event, parameters) {
		console.log(parameters);
		//This is mostly ceremonial having this here. 
		//This is actually caught in the mainGameRightTabsCtrl and handled there
    });


    $rootScope.$on('model:SelectDestinations', function (event, parameters) {
		//parameters should be a modelContainer.
		fillViewFromModel(parameters); 
    });

    $rootScope.$on('model:TurnStartedNotification', function (event, parameters) {
		//check if it is this players turn. if so set 
		/*	$scope.currentTurn === 'yourTurn'
		ELSE
			$scope.currentTurn = 'yourTurn';*/
		$scope.currentGameId = parameters.getGameId();
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

		fillViewFromModel(parameters);

    });

	//----------------------- Load view from model------------------------------------
	var fillViewFromModel = function (modelContainer)
	{
		$scope.selectDestOnRight = false;

		$scope.secondTrainCardRound = modelContainer.playerMustDrawAgain();
	
		$scope.topNavMessage = "Waiting for " + modelContainer.getPlayerNameById(modelContainer.getTurnIndex()) + " to take their turn";

		//------------- Destinations in your hand
		$scope.destinations.length = 0;
		for(var i = 0; i < modelContainer.getPlayersDestinationSize(); i++)
		{
			var obj = {cityName1: modelContainer.getPlayersDestinationCityName1(i),
					   cityName2: modelContainer.getPlayersDestinationCityName2(i),
					   isComplete: modelContainer.getPlayersDestinationIsComplete(i),
					   points: modelContainer.getPlayersDestinationPoints(i)};
			$scope.destinations.push(obj);
		}

		//-------------- Color train cards in your hand
		for (color in $scope.playersTrainCards)
		{
			$scope.playersTrainCards[color] = modelContainer.getTrainCardsByColor(color);
		}

		//------------- Color train cards to draw from
		$scope.cardsVisible.length = 0;
		for(var i = 0; i < 5; i++)
		{
			var color = modelContainer.getCardVisibleAt(i)
			if(typeof(color) != 'undefined')
			{
				color = color.toLowerCase();
				$scope.cardsVisible.push(color == 'none' ? 'wild' : color); 
			}
		}

		//-------------- Players section on the left
		$scope.thisPlayer = {
			playerName:modelContainer.getPlayerName(),
			playerColor:modelContainer.getPlayerColor().toLowerCase(),
			points:modelContainer.getPlayerPoints(),
			trainsLeft:modelContainer.getPlayerTrainsLeft(),
			playerId:modelContainer.getPlayerId()
		};

		$scope.opponents.length = 0;
		for(var i = 0; i < modelContainer.getOpponentsSize(); i++)
		{
			var obj = {
				playerName:modelContainer.getOpponentName(i),
				playerColor:modelContainer.getOpponentColor(i),
				points:modelContainer.getOpponentPoints(i),
				trainsLeft:modelContainer.getOpponentTrainsLeft(i),
				playerId:modelContainer.getOpponentPlayerId(i)
			}
			$scope.opponents.push(obj);
		}

	}

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
		if(!$uibModalStack.getTop()) //only show if the modal doenst already exist
		{
			waitingModalInstance = waitingToStartModalModal(iAmTheCreator);
		}
	}

//---------------------------------- Waiting to start Modal
	function waitingToStartModalModal(iAmTheCreator)
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

		return modalInstance;

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

