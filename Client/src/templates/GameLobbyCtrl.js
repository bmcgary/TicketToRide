var app = angular.module('ticketToRide');

app.controller('gameLobbyController', function($scope, $rootScope, ClientAPI, $uibModal,$state, ModelFacade) {
	//LOGOUT
	$scope.logOut =function(){
		ClientAPI.logout();
	}
	$rootScope.$on('server:Logout', function(event, parameters) {
		console.log("you are logging out");
		if(parameters.description == "success") {
			$state.go('login');
		} else {
			alert("Unexpected Server description: " + parameters.description);
		}
	});
	//END LOGOUT
//Update games
var isUserLeavingGameLobby = false;
$rootScope.$on('server:UpdateGame', function(event, parameters) {
	/*if(!isUserLeavingGameLobby){
		$scope.availableGames.push(parameters.game);
		getAvailableColors($scope.availableGames);
	}*/
});
//
	//GET Joinable GAMES
	function getJoinableGames() {
		ClientAPI.updateJoinableGames();
	}
		getJoinableGames();
		$rootScope.$on('model:UpdateJoinableGames', function(event, games) {
		getAvailableColors(games);
		$scope.availableGames = games;
	});
	//END GET Joinable GAMES
	//GET User GAMES
	function getUserGames() {
		ClientAPI.updateUserGames();
	}
	getUserGames();
	$rootScope.$on('server:UpdateUserGames', function(event, parameters) {
		getAvailableColors(parameters.games);
		$scope.myGames = parameters.games;
	});
	function getAvailableColors(games){
		var keys = Object.keys(games);

		for(var i =0; i < keys.length; i++ ){
    			var availablecolors = ["red", "yellow", "blue","black","green"];//starts off full
    			var takenColors = [];
    			for(var k=0; k<games[keys[i]].players.length; k++){//First check to see what colors are taken
    				takenColors.push(games[keys[i]].players[k].playerColor);
    			}
    			for(var c =0; c<takenColors.length; c++){//loop through taken colors and remove them from availableColors
            var index = availablecolors.indexOf(takenColors[i]);
            availablecolors.splice(index, 1);
    			}
					games[keys[i]].availableColors= availablecolors;//attach availableColors to the game object
    		}
    }
	//END GET User GAMES

	// SHOW MORE DETAILS FOR BOTH BOXES
	$scope.activeAvailableGames = "";
	$scope.showDetailAvailableGames = function(game) {
		if ($scope.activeAvailableGames != game.gameId) {
			$scope.activeAvailableGames = game.gameId;
		} else {
			$scope.activeAvailableGames = null;
		}
	}
	$scope.activeMyGames = "";
	$scope.showDetailMyGames = function(game) {
		if ($scope.activeMyGames != game.gameId) {
			$scope.activeMyGames = game.gameId;
		} else {
			$scope.activeMyGames = null;
		}
	}
	//END SHOW MORE DETAILS
	$scope.joinGame = function(game,color) {
		ClientAPI.joinGame(game.gameId,color);
	}

	$rootScope.$on('server:JoinGame', function(event, parameters) {
		if(parameters.description == "success") {
			ModelFacade.setGameInView(parameters.gameId, false);
			$state.go('mainGame');
		}
	});
	//END JOIN/REJOIN FUNCTIONS
  // this Toggles the Create New game modal
  $scope.modalShown = false;
  $scope.toggleCreateNewGameModal = function() {
    $scope.modalShown = !$scope.modalShown;
  };
	$scope.newGameName ="";
	$scope.createNewGame = function (color, name){
		if(name.length >0){
			isUserLeavingGameLobby = true;
			ClientAPI.createGame(name, color);
		}else{
		    alert("Game Name cannot be empty");
			console.log('error');
		}
	}

	$rootScope.$on('server:CreateGame', function (event, parameters) {
		if(parameters.description == "success") {
			ModelFacade.setGameInView(parameters.gameId, true);
			$state.go('mainGame');
		}
	});

});
//THIS IS THE DIRECTIVE FOR THE CREATENEWGAME popup
app.directive('modalDialog', function() {
  return {
    restrict: 'E',
    scope: {
      show: '='
    },
    replace: true, // Replace with the template below
    transclude: true, // we want to insert custom content inside the directive
    link: function(scope, element, attrs) {
      scope.dialogStyle = {};
      if (attrs.width)
        scope.dialogStyle.width = attrs.width;
      if (attrs.height)
        scope.dialogStyle.height = attrs.height;
      scope.hideModal = function() {
        scope.show = false;
      };
    },
    template: "<div class='ng-modal' ng-show='show'>"+
                "<div class='ng-modal-overlay' ng-click='hideModal()'></div>"+
                "<div class='ng-modal-dialog' ng-style='dialogStyle'>"+
                  "<div class='ng-modal-close' ng-click='hideModal()'>X</div>"+
                  "<div class='ng-modal-dialog-content' ng-transclude></div>"+
                "</div>"+
              "</div>" // See below
  };
});
