var app = angular.module('ticketToRide');

app.factory('ModelFacade', function ($rootScope, Game, ModelContainer, TrainCardColor, StaticTrackList) {
	//store and access game models
	var usersGames = {};
    var joinableGames = {};
	var gameInView = -1;

	var getModel = function () {
		return usersGames[gameInView];
	};

    var broadcast = function (gameId, command) {
        if(gameId == gameInView) { 
            //QUESTION: Does this mean only the game being played will be updated? not any other game thats going on behind the scenes?
            //RESPONSE: No, it means that the controllers will only be notified about changes in the game being played. All changes will still be made here.
            $rootScope.$broadcast('model:' + command, new ModelContainer(getModel()));
        }
    };

    //Lobby stuff=======================================================================================
    $rootScope.$on('server:UpdateUserGames', function (event, parameters) {
        //do logic

        broadcast(parameters.gameId, 'UpdateUserGames');
    });

    $rootScope.$on('server:UpdateJoinableGames', function (event, parameters) {
        //do logic

        $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames[parameters.gameId]);
    });

	$rootScope.$on('server:StartGame', function (event, parameters) {
        //do logic

    	broadcast(parameters.gameId, 'StartGame');
    });

    $rootScope.$on('server:LeaveGame', function (event, parameters) {
        //future
        delete usersGames[parameters.gameId];
        broadcast(parameters.gameId, 'LeaveGame');
    });

    //In Game===========================================================================================
    $rootScope.$on('server:SendChat', function (event, parameters) {
        //future

        broadcast(parameters.gameId, 'SendChat');
    });


    $rootScope.$on('server:BuyRoute', function (event, parameters) {
        //do logic

        broadcast(parameters.gameId, 'BuyRoute');
    });


    $rootScope.$on('server:DrawTrainCard', function (event, parameters) {
		//do logic

        broadcast(parameters.gameId, 'DrawTrainCard');
    });


    $rootScope.$on('server:GetDestinations', function (event, parameters) {
		//do logic

        broadcast(parameters.gameId, 'GetDestinations');
    });


    $rootScope.$on('server:SelectDestinations', function (event, parameters) {
		//do logic

        broadcast(parameters.gameId, 'SelectDestinations');
    });

    return {

        //CanDo Methods=================================================================================
    	canBuyRoute: function (routeIndex, trainColor, numberOfWilds) {
            var model = getModel();

            var type = typeof(model.board.tracksPurchased[routeIndex]);
            if(type != 'undefined' && type != 'null') { //route is owned
                return false;
            } else if(numberOfWilds > model.player.trainCards[TrainCardColor.WILD]) {
                return false;
            } else {
                return StaticTrackList.routeIndex.trainsRequired <= (numberOfWilds + model.player.trainCards[trainColor]);
            }
    	},

    	canDrawCard: function (cardLocation) {
    		var model = getModel();

            if(cardLocation == 0) {
                return model.board.deckHasTrains;
            } else if(model.board.mustDrawAgain) {
                return TrainCardColor.WILD != model.board.cardsVisible[cardLocation - 1];
            } else {
                return  true;
            }
    	},

    	canDrawDestination: function () {
    		var model = getModel();

    		return model.board.deckHasDestinations && !model.board.mustDrawAgain;
    	},

    	canSelectDestination: function (destinationsSelected) {
    		var model = getModel();

            if(model.board.isFirstRound) { //change to better constraints
                return destinationsSelected.length >= 2;
            } else {
                return destinationsSelected.length >= 1;
            }
    	},

    	switchGame: function (gameId) {
            gameInView = gameId;
            broadcast(gameInView, 'SwitchGame');
    	}
    };
});
