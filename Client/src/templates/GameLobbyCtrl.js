var app = angular.module('ticketToRide');

app.controller('gameLobbyController', function($scope, $rootScope, ClientAPI, $uibModal,$state) {
	var myGames = [{
		id: "10",
		players: [
			"John",
			"Ben",
			"Kyle"
		],
		yourColor: "red"
	}, {
		id: "11",
		players: [
			"Josh",
			"Tyler",
			"Jared"
		],
		yourColor: "pink"
	}, {
		id: "12",
		players: [
			"Fred",
			"Ron",
			"Ben"
		],
		yourColor: "Blue"
	}];
	var availableGames = [{
		id: "10",
		players: [
			"ted",
			"blake",
			"Ben"
		],
		colorAvailable: [
			"blue",
			"red",
			"Orange"
		]
	}, {
		id: "11",
		players: [
			"Fred",
			"Ted",
			"Mike"
		],
		colorAvailable: [
			"blue",
			"red",
			"Orange"
		]
	}, {
		id: "12",
		players: [
			"Kyle",
			"Matt",
			"Jared"
		],
		colorAvailable: [
			"yellow",
			"red",
			"purple"
		]
	}];
	$scope.myGames = myGames;
	$scope.availableGames = availableGames;
	//end of dummy DATA
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

	//GET Joinable GAMES
	function getJoinableGames() {
		ClientAPI.updateJoinableGames();
	}
	getJoinableGames();
	//getAvailableGames(); // maybe call the function to get the data
	$rootScope.$on('server:UpdateJoinableGames', function(event, parameters) {
		alert("got joinable Games ");
		console.log(event);
		console.log(parameters);
		console.log("*************")
	});
	//END GET Joinable GAMES
	/////////////////////////
	//GET User GAMES
	function getUserGames() {
		ClientAPI.updateUserGames();
	}
	getUserGames();
	//getAvailableGames(); // maybe call the function to get the data
	$rootScope.$on('server:UpdateUserGames', function(event, parameters) {
		alert("got User Games ");
		console.log(event);
		console.log(parameters);
		console.log("$$$$$$$$$$$$$")
	});
	//END GET User GAMES
	////////////////////////
	//TODO do the same thing and get MYGAMES

	// SHOW MORE DETAILS FOR BOTH BOXES
	$scope.activeAvailableGames = "";
	$scope.showDetailAvailableGames = function(game) {
		if ($scope.activeAvailableGames != game.id) {
			$scope.activeAvailableGames = game.id;
		} else {
			$scope.activeAvailableGames = null;
		}
	}
	$scope.activeMyGames = "";
	$scope.showDetailMyGames = function(game) {
		if ($scope.activeMyGames != game.id) {
			$scope.activeMyGames = game.id;
		} else {
			$scope.activeMyGames = null;
		}
	}
	//END SHOW MORE DETAILS

	//THESE ARE THE FUNCTIONS TO CALL to JOIN/REJOIN GAMES
	//TODO connect to backend
	$scope.reJoinGame = function(game) {
		console.log("you are rejoing: " + game.id);
	}
	$scope.joinGame = function(game,color) {
		console.log("you are joing a new game: " + game.id);
		console.log("as: "+color)
	}
	//END JOIN/REJOIN FUNCTIONS
  // this Toggles the Create New game modal
  $scope.modalShown = false;
  $scope.toggleCreateNewGameModal = function() {
    $scope.modalShown = !$scope.modalShown;
  };
	$scope.newGameName ="";
	$scope.createNewGame = function (color, name){
		if(name.length >0){
			ClientAPI.createGame(name, color);
		}else{
			console.log('error');
		}
	}

	$rootScope.$on('server:CreateGame', function (event, parameters) {
		alert("we did it");
		console.log(parameters);
		if(parameters.description == "success") {
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
