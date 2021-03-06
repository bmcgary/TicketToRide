var app = angular.module('ticketToRide');

app.controller('mainGameRightTabsCtrl', function ($scope, $rootScope, ClientAPI, $uibModal, ModelFacade) {

	setUpHeight();
		
	//Set the height size to the size of the view it is contained in.
	// that way the train cards are scaled to fit in their correct sport correctly
	function setUpHeight()
	{
		var myEl = document.getElementById("rightTabs");
    	var height = myEl.offsetHeight;

		$scope.heightAmount = (height / 7) + "px";
	}

	//All clicking comes here. the State pattern is used to figure out where to go next
	$scope.cardSelected = function(input)
	{
		states[$scope.currentTurn]['cardSelected'](input);
	}

	//shoule a specific card be disabled or not?
	$scope.disableCard = function(input)
	{
		return states[$scope.currentTurn]['disableCard'](input);
	}

    $rootScope.$on('model:SetGameInView', function (event, parameters) {
		//parameters is the entire model container
        $scope.currentGameId = parameters.getGameId();
    });

    $rootScope.$on('model:GetDestinations', function (event, parameters) 
	{	
		//TEMP For testing purposes
		/*var imagesArray = [{selected:false, url:'ttr-route-boston-miami.jpg'},
					{selected:false, url:'ttr-route-calgary-phoenix.jpg'},
					{selected:false, url:'ttr-route-calgary-saltLakeCity.jpg'}];*/
		var imagesArray = [];
		var firstRound = parameters.isFirstRound();
		parameters = parameters.getTemporaryStorageOfDestinationCardsToBeSelectedFrom();
		for(index in parameters)
		{

			imagesArray.push({selected:false, 
				url:'ttr-route-' + camelize(parameters[index].city1) + '-' + camelize(parameters[index].city2) + '.jpg', 
				city1:parameters[index].city1,
				city2:parameters[index].city2,
				points: parameters[index].points});
		}

		
		//-----------------------------------
		states[$scope.currentTurn]['getDestinationCardsCallBack'](firstRound, imagesArray);


			function camelize(str) {//The server sends back city names with spaces but the files are camelcased. So a converstion is needed
			  return str.replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, function(match, index) {
				if (+match === 0) return ""; // or if (/\s+/.test(match)) for white spaces
				return index == 0 ? match.toLowerCase() : match.toUpperCase();
			  });
			}
    });


	//---------------------------- States used ------------------------------------------

	var notYourTurnState = 
	{
		'disableCard':function(input){return true;},
		'cardSelected':function(input){return;},
		'getDestinationCardsCallBack':function(){return;}
	}
	var yourTurnState = 
	{
		//What cards should be disabled???
		'disableCard':function(input){
			switch(input)
			{
				case 'destination':
					//if player has already drawn a card the option to draw a destination card is no longer an option
					return $scope.secondTrainCardRound;
					break;
				case 5:
					return false;
					break;
				default:
					return !checkEligibility(input);
			}

		},
		//A card was selected. What to do next
		'cardSelected':function(input){
			if(checkEligibility(input))
			{
				switch(input)
				{
					case 'destination':
						//pop up a modol showing the destination cards that could be selected
						ClientAPI.getDestinations($scope.currentGameId);
						break;				
					default:
						switch(input)//This is for debugging. once everything works this switch can be removed
						{ 
							case 0:
								console.log("Train deck 1st card selected");
								break;
							case 1:
								console.log("Train deck 2nd card selected");
								break;			
							case 2:
								console.log("Train deck 3rd card selected");
								break;			
							case 3:
								console.log("Train deck 4th card selected");
								break;			
							case 4:
								console.log("Train deck 5th card selected");
								break;
							case 5:
								console.log("Train deck card selected");
								break;
						}
						ClientAPI.drawTrainCard($scope.currentGameId,input);
				}
			}
			else
				console.log("Card Disabled");
		},
		//what to do once the destination cards have come back from the server
		'getDestinationCardsCallBack':function(firstRound, imagesArray){

				//fix the imagesArray to look like that in case is isn't already
				if(firstRound)
					openDestinationModal(2, imagesArray);
				else
				{
					//show the thing on the right
					$rootScope.selectDestOnRight = true;
					$scope.selectDestOnRight = true;
					$scope.availableDestsToPickFrom = imagesArray;
				}
			}
	}

	var states = {'notYourTurn':notYourTurnState, 'yourTurn':yourTurnState};

	$scope.selectDestSubmitPressed = function()
	{
		var selectedIndexes = [];
		
		for(index in $scope.availableDestsToPickFrom)
		{
			if($scope.availableDestsToPickFrom[index]['selected'])
			{
				selectedIndexes.push(parseInt(index));
			}
		}

		if(selectedIndexes.length >= 1)//this is only in game selection so its always 1
		{
			$rootScope.selectDestOnRight = false;
			$scope.selectDestOnRight = false;
    		ClientAPI.selectDestinations($scope.currentGameId,selectedIndexes);
		}
		else
			alert("You need to chose at least 1 destination card", 'danger');
	}

	$scope.destCardSelected = function(index)
	{
		$scope.availableDestsToPickFrom[index]['selected'] = !$scope.availableDestsToPickFrom[index]['selected'];
	}

	function checkEligibility(input)
	{
		if(input == 'destination')
		{
			return !$scope.secondTrainCardRound;
		}
		if($scope.cardsVisible[input] === 'wild' && $scope.secondTrainCardRound)
			return false;	
		return true;
	}

//-------------------- this gets ran when the file is initially loaded -----------------
	var tempModel = ModelFacade.getGameInView();
	if(tempModel.getOpponentsSize() > 0)
	{
		if(tempModel.isFirstRound())
		{
			if(tempModel.getTemporaryStorageOfDestinationCardsToBeSelectedFrom().length == 0)
			{
				//taken care of in the gameScaffoldingCtrl.js
			}
			else if(tempModel.getTurnIndex() == tempModel.getPlayerId())
			{
				//its my turn. show selectdest modal
				console.log("show selectdestmodal");
				$scope.currentGameId = ModelFacade.getGameInView().getGameId();
				$rootScope.$broadcast('model:GetDestinations', ModelFacade.getGameInView());
			}
		}
	}
	else
	{
		console.log("Attempted to load model but it wasnt there")
	}

//---------------------------Destination modal -------------------------------------------------------
	function openDestinationModal(amountOfdestsToPick, imagesArray)
	{
		var modalInstance = $uibModal.open({
			  animation: true,
			  templateUrl: 'destinationModal.html',
			  controller: 'destinationModalCtrl',
			  backdrop : 'static',
			  keyboard: false,
			  //size: size,
			  resolve: 
			  {
				   amountOfdestsToPick: function () 
 				   {
				     return amountOfdestsToPick;
				   },
				   availableDestsToPickFrom: function()
				   {
					 return imagesArray
   				   }
			  }
			});

		modalInstance.result.then(
			function (selectedItems)  //they selected stuff
			{
				
		  		console.log(selectedItems); //from here ship it out via the ClientAPI
				ClientAPI.selectDestinations($scope.currentGameId,selectedItems);
			});//dont need a function for canceling since that isn't allowed
	}

});

// Destination modal's controller ------------------------------------------------------------------------
app.controller('destinationModalCtrl', function ($scope, $uibModalInstance, amountOfdestsToPick, availableDestsToPickFrom) {

  $scope.alert = {showAlert: false, message: "", type:""};	
  $scope.amountOfDestinationsToPick = amountOfdestsToPick;


  $scope.availableDestsToPickFrom = availableDestsToPickFrom;

	$scope.destCardSelected = function(index)
	{
		$scope.availableDestsToPickFrom[index]['selected'] = !$scope.availableDestsToPickFrom[index]['selected'];
	}

  $scope.ok = function () {
	//check that they have chosen enough
	var selectedIndexes = [];
	for(index in $scope.availableDestsToPickFrom)
	{
		if($scope.availableDestsToPickFrom[index]['selected'])
		{
			selectedIndexes.push(parseInt(index));
		}
	} 

	if(selectedIndexes.length >= $scope.amountOfDestinationsToPick)
    	$uibModalInstance.close(selectedIndexes);
	else
		showAlert("You need to chose at least " + $scope.amountOfDestinationsToPick + " destination cards", 'danger');
  };


	function showAlert(message, type)
	{
		$scope.alert.showAlert = true;
		$scope.alert.message = message;
		$scope.alert.type = type;
	}

});
