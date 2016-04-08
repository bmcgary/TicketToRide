var app = angular.module('ticketToRide');

app.factory('ModelContainer', function () {

    var model = {};

    //constructor 
    function ModelContainer (model) {
        this.model = model;
    }

    //Game data
    ModelContainer.prototype.getGameId = function() {
        return this.model.getGameId();
    };

    ModelContainer.prototype.getGameName = function() {
        return this.model.getGameName();
    };

    ModelContainer.prototype.getGameHistorySize = function() {
        alert("not implemented: ModelContainer-getGameHistorySize");
        //return this.model.getGameHistory().length;
    };

    ModelContainer.prototype.getGameHistoryAtIndex = function(index) {
        alert("not implemented: ModelContainer-getGameHistoryAtIndex");
        //return this.model.getGameHistory()[index];
    };

    ModelContainer.prototype.getGameChatSize = function() {
        alert("not implemented: ModelContainer-getGameChatSize");
        //return this.model.getGameChat().length;
    };

    ModelContainer.prototype.getGameChatAtIndex = function(index) {
        alert("not implemented: ModelContainer-getGameChatAtIndex");
        //return this.model.getGameChat()[index];
    };

    ModelContainer.prototype.isGameOver = function() {
        return this.model.getGameOver();
    };

    ModelContainer.prototype.getWinnerName = function() {
        return this.model.getWinnerName();
    };

    //Gameboard Data
    ModelContainer.prototype.deckHasDestinations = function() {
        alert("not implemented: ModelContainer-deckHasDestinations");
        //return this.model.getBoard().getDeckHasDestinations();
    };

    ModelContainer.prototype.deckHasTrains = function() {
        alert("not implemented: ModelContainer-deckHasTrains");
        //return this.model.getBoard().getDeckHasTrains();
    };

    ModelContainer.prototype.isFirstRound = function() {
        return this.model.getBoard().getIsFirstRound();
    };

    ModelContainer.prototype.isLastRound = function() {
        return this.model.getBoard().getIsLastRound();
    };

    ModelContainer.prototype.getCardVisibleAt = function(index) {
        return this.model.getBoard().getCardsVisibleAt(index);
    };

    ModelContainer.prototype.getRoutesOwned = function() {
        return Object.keys(this.model.getBoard().getRoutesPurchased());
    };

    ModelContainer.prototype.getPlayerColorByRouteId = function(routeId) {
        return this.model.getBoard().getRoutesPurchased()[routeId];
    };

    ModelContainer.prototype.getPlayerIdForTheLongestBonus = function() {
        if(!this.model.getGameOver())
        {
            alert("The longest bonus is awarded after the game is over");
        }
        else
        {
            return this.model.getBoard().getPlayerIdForTheLongestBonus();
        }
    };

    ModelContainer.prototype.playerMustDrawAgain = function() {
        return this.model.getBoard().getMustDrawAgain();
    };

    //Opponent Data
    ModelContainer.prototype.getOpponentsSize = function() {
        return this.model.getOpponents().length;
    };

    ModelContainer.prototype.getOpponentName = function(index) {
        return this.model.getOpponents()[index].getPlayerName();
    };

    ModelContainer.prototype.getOpponentColor = function(index) {
        return this.model.getOpponents()[index].getPlayerColor();
    };

    ModelContainer.prototype.getOpponentPoints = function(index) {
        return this.model.getOpponents()[index].getPoints();
    };

    ModelContainer.prototype.getOpponentTrainsLeft = function(index) {
        return this.model.getOpponents()[index].getTrainsLeft();
    };

    ModelContainer.prototype.getOpponentPlayerId = function(index) {
        return this.model.getOpponents()[index].getPlayerId();
    };

    //Player Data
    ModelContainer.prototype.getPlayerName = function() {
        return this.model.getPlayer().getPlayerName();
    };

    ModelContainer.prototype.getPlayerColor = function() {
        return this.model.getPlayer().getPlayerColor();
    };

    ModelContainer.prototype.getPlayerPoints = function() {
        return this.model.getPlayer().getPoints();
    };

    ModelContainer.prototype.getPlayerTrainsLeft = function() {
        return this.model.getPlayer().getTrainsLeft();
    };

    ModelContainer.prototype.getPlayerId = function() {
        return this.model.getPlayer().getPlayerId();
    };

    ModelContainer.prototype.getTurnIndex = function() {
        return this.model.getTurnIndex();
    };

    ModelContainer.prototype.getTrainCardsByColor = function(trainCardColor) {
		var convertedColor =  trainCardColor == 'wild' ? 'None' : trainCardColor;
		convertedColor = (convertedColor.charAt(0).toUpperCase() + convertedColor.slice(1));
        var count = this.model.getPlayer().getTrainCards()[convertedColor];
        if(typeof(count) === 'undefined' || typeof(count) === 'null') {
            count = 0;
        }
        return count;
    };

    ModelContainer.prototype.getPlayerNameById = function(playerId) {
        return this.model.getPlayerById(playerId).getPlayerName();
    };

    //DestinationCard Data
    ModelContainer.prototype.getPlayersDestinationSize = function() {
        return this.model.getPlayer().getDestinationCards().length;
    };

    ModelContainer.prototype.getPlayersDestinationCityName1 = function(index) {
        return this.model.getPlayer().getDestinationCards()[index].getCityName1();
    };

    ModelContainer.prototype.getPlayersDestinationCityName2 = function(index) {
        return this.model.getPlayer().getDestinationCards()[index].getCityName2();
    };

    ModelContainer.prototype.getPlayersDestinationIsComplete = function(index) {
        return this.model.getPlayer().getDestinationCards()[index].getIsComplete();
    };

    ModelContainer.prototype.getPlayersDestinationPoints = function(index) {
        return this.model.getPlayer().getDestinationCards()[index].getPoints();
    };

    ModelContainer.prototype.getTemporaryStorageOfDestinationCardsToBeSelectedFrom = function() {
        return this.model.getPlayer().getTemporaryStorageOfCardsToBeSelectedFrom();
    };

    return ModelContainer;
});
