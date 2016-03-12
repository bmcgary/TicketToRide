var app = angular.module('ticketToRide');

app.controller('mainGameRightTabsCtrl', function ($scope, ClientAPI) {

	setUpHeight();
		
	//Set the height size to the size of the view it is contained in.
	function setUpHeight()
	{
		var myEl = document.getElementById("rightTabs");
    	var height = myEl.offsetHeight;

		$scope.heightAmount = (height / 7) + "px";
	}

	$scope.cardSelected = function(input)
	{
		states[$scope.currentTurn]['cardSelected'](input);
	}


	$scope.disableCard = function(input)
	{
		return states[$scope.currentTurn]['disableCard'](input);
	}

	var notYourTurnState = 
	{
		'disableCard':function(input){return true;},
		'cardSelected':function(input){return;}
	}
	var yourTurnState = 
	{
		'disableCard':function(input){
			switch(input)
			{
				case 'destination':
					//if player has already drawn a card the option to draw a destination card is no longer an option
					return false;
					break;
				case 5:
					return false;
					break;
				default:
					return !checkEligibility(input);
			}

		},
		'cardSelected':function(input){
			switch(input)
			{
				case 'destination':
					console.log("Destination card selected");
					break;
				case 5:
					console.log("Train deck card selected");
					break;					
				default:
					if(checkEligibility(input))
					{
						switch(input)
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
						}
					}
					else
						console.log("Card Disabled");
			}
		}
	}

	function checkEligibility(input)
	{
		if($scope.cardsVisible[input] === 'wild' && !canDrawWild)
			return false;	
		return true;
	}

	var states = {'notYourTurn':notYourTurnState, 'yourTurn':yourTurnState};
	var canDrawWild = false;

	

});
