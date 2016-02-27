var app = angular.module('ticketToRide');

app.factory('ModelFacade', function ($rootScope, Game) {
	//store and access game models
	var usersGames = {};
    var availableGames = {};
	var gameInView = -1;

	var getModel = function () {
		return usersGames[gameInView];
	};

    var broadcast = function (gameId, command) {
        if(gameId == gameInView) {
            $rootScope.$broadcast('model:' + command, getModel());
        }
    }

    $rootScope.$on('server:UpdateUserGames', function (event, parameters) {
        //do logic

        broadcast(parameters.gameId, 'StartGame');
    });

    $rootScope.$on('server:UpdateJoinableGames', function (event, parameters) {
        //do logic

        broadcast(parameters.gameId, 'StartGame');
    });

	$rootScope.$on('server:StartGame', function (event, parameters) {
        //do logic

    	broadcast(parameters.gameId, 'StartGame');
    });

    $rootScope.$on('server:LeaveGame', function (event, parameters) {
        //games.Remove(parameters.gameId);

        broadcast(parameters.gameId, 'LeaveGame');
    });

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
    	
    	canBuyRoute: function (routeIndex, trainColor, numberOfWilds) {
            //return getModel().canBuyRoute()
    		return false;
    	},

    	canDrawWildCard: function (cardLocation) {
    		//return getModel().canDrawWildCard()
    		return false;
    	},

    	canDrawDestination: function () {
    		//return getModel().canDrawDestination()
    		return false;
    	},

    	canSelectDestination: function (destinationsSelected) {
    		//return getModel().canSelectDestination()
    		return false;
    	}

    	switchGame: function (gameId) {
    		currentGameId = gameId;
    		$rootScope.$broadcast('model:SwitchGame', getModel());
    	}
    };
});