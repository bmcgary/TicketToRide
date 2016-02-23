var app = angular.module('ticketToRide');

app.factory('ModelFacade', function ($rootScope) {
	//store and access game models
	var models = [];
	var currentModelId = -1;

	var getModel = function () {
		return models[currentModelId];
	};


	$rootScope.$on('server:JoinGame', function (event, parameters) {
    	//do logic

    	$rootScope.$broadcast('model:JoinGame', getModel());
    });


    $rootScope.$on('server:SendChat', function (event, parameters) {
    	//do logic

    	$rootScope.$broadcast('model:SendChat', getModel());
    });


    $rootScope.$on('server:BuyRoute', function (event, parameters) {
		//do logic

    	$rootScope.$broadcast('model:BuyRoute', getModel());
    });


    $rootScope.$on('server:DrawTrainCard', function (event, parameters) {
		//do logic

    	$rootScope.$broadcast('model:DrawTrainCard', getModel());
    });


    $rootScope.$on('server:GetDestinations', function (event, parameters) {
		//do logic

    	$rootScope.$broadcast('model:GetDestinations', getModel());
    });


    $rootScope.$on('server:SelectDestinations', function (event, parameters) {
		//do logic

    	$rootScope.$broadcast('model:SelectDestinations', getModel());
    });
    	
    return {
    	
    	canBuyRoute: function (routeIndex, trainColor, numberOfWilds) {
    		//do logic
    		return false;
    	},

    	canDrawWildCard: function (cardLocation) {
    		//do logic
    		return false;
    	},

    	canDrawDestination: function () {
    		//do logic
    		return false;
    	},

    	canSelectDestination: function (destinationsSelected) {
    		//do logic
    		return false;
    	}

    	switchGame: function (gameId) {
    		currentModelId = gameId;
    		$rootScope.$broadcast('model:SwitchGame', getModel());
    	}
    };
});