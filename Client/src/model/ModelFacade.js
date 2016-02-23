var app = angular.module('ticketToRide');

app.factory('ModelFacade', function ($rootScope) {
    //store and access game models
    //listeners for each command that can modify the model
    	//each listener will:
    		//get the model
    		//do logic
    		//broadcast the change to the controller listeners
    		//the broadcast will include the model
    //implementation for each of the canDos
    //also will include a listener for when the player switches games
});