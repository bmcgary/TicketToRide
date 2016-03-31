var app = angular.module('ticketToRide');

app.factory('ModelContainer', function () {

    var model = {};

    //constructor 
    function ModelContainer (model) {
        this.model = model;
    }

    //Game data
    ModelContainer.prototype.getGameId = function() {
        return this.model.gameId;
    };

    ModelContainer.prototype.getGameName = function() {
        return this.model.gameName;
    };

    ModelContainer.prototype.getGameHistorySize = function() {
        return this.model.gameHistory.length;
    };

    ModelContainer.prototype.getGameHistoryAtIndex = function(index) {
        return this.model.gameHistory[index];
    };

    ModelContainer.prototype.getGameChatSize = function() {
        return this.model.gameChat.length;
    };

    ModelContainer.prototype.getGameChatAtIndex = function(index) {
        return this.model.gameChat[index];
    };

    ModelContainer.prototype.isGameOver = function() {
        return this.model.gameOver;
    };

    //Gameboard Data
    ModelContainer.prototype.deckHasDestinations = function() {
        return this.model.board.deckHasDestinations;
    };

    ModelContainer.prototype.deckHasTrains = function() {
        return this.model.board.deckHasTrains;
    };

    ModelContainer.prototype.isFirstRound = function() {
        return this.model.board.isFirstRound;
    };

    ModelContainer.prototype.isLastRound = function() {
        return this.model.board.isLastRound;
    };

    ModelContainer.prototype.getCardVisibleAt = function(index) {
        return this.model.board.cardsVisible[index];
    };

    ModelContainer.prototype.getRoutesOwned = function() {
        return Object.keys(this.model.board.routesPurchased);
    };

    ModelContainer.prototype.getPlayerColorByRouteId = function(routeId) {
        var playerId = this.model.board.tracksPurchased[routeId];

        if(this.getPlayerId() == playerId) {
            return this.getPlayerColor();
        } else {
            for(var i = 0; i < this.getOpponentsSize(); i++) {
                if(this.getOpponentPlayerId(i) == playerId) {
                    return this.getOpponentColor(i);
                }
            }
        }
    };

    ModelContainer.prototype.getPlayerIdForTheLongestBonus = function() {
        if(!this.model.gameOver)
        {
            alert("The longest bonus is awarded after the game is over");
        }
        else
        {
            return this.model.board.playerIdForTheLongestBonus;
        }
    };

    //Opponent Data
    ModelContainer.prototype.getOpponentsSize = function() {
        return this.model.opponents.length;
    };

    ModelContainer.prototype.getOpponentName = function(index) {
        return this.model.opponents[index].playerName;
    };

    ModelContainer.prototype.getOpponentColor = function(index) {
        return this.model.opponents[index].playerColor;
    };

    ModelContainer.prototype.getOpponentPoints = function(index) {
        return this.model.opponents[index].points;
    };

    ModelContainer.prototype.getOpponentTrainsLeft = function(index) {
        return this.model.opponents[index].trainsLeft;
    };

    ModelContainer.prototype.getOpponentPlayerId = function(index) {
        return this.model.opponents[index].playerId;
    };

    //Player Data
    ModelContainer.prototype.getPlayerName = function() {
        return this.model.player.playerName;
    };

    ModelContainer.prototype.getPlayerColor = function() {
        return this.model.player.playerColor;
    };

    ModelContainer.prototype.getPlayerPoints = function() {
        return this.model.player.points;
    };

    ModelContainer.prototype.getPlayerTrainsLeft = function() {
        return this.model.player.trainsLeft;
    };

    ModelContainer.prototype.getPlayerId = function() {
        return this.model.player.playerId;
    };

    ModelContainer.prototype.getTurnIndex = function() {
        return this.model.turnIndex;
    };

    ModelContainer.prototype.getTrainCardsByColor = function(trainCardColor) {
        var count = this.model.player.trainCards[trainCardColor];
        if(typeof(count) === 'undefined' || typeof(count) === 'null') {
            count = 0;
        }
        return count;
    };

    //DestinationCard Data
    ModelContainer.prototype.getPlayersDestinationSize = function() {
        return this.model.player.destinationCards.length;
    };

    ModelContainer.prototype.getPlayersDestinationCityName1 = function(index) {
        return this.model.player.destinationCards[index].cityName1;
    };

    ModelContainer.prototype.getPlayersDestinationCityName2 = function(index) {
        return this.model.player.destinationCards[index].cityName2;
    };

    ModelContainer.prototype.getPlayersDestinationIsComplete = function(index) {
        return this.model.player.destinationCards[index].isComplete;
    };

    ModelContainer.prototype.getPlayersDestinationPoints = function(index) {
        return this.model.player.destinationCards[index].points;
    };

    ModelContainer.prototype.getTemporaryStorageOfDestinationCardsToBeSelectedFrom = function() {
        return this.model.player.temporaryStorageOfCardsToBeSelectedFrom;
    };

    return ModelContainer;
});
