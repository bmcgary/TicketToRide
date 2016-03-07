var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor, StaticTrackList) {

    GameBoard.prototype.deckHasDestinations = true;
    GameBoard.prototype.deckHasTrains = true;
    GameBoard.prototype.isFirstRound = true;
    GameBoard.prototype.isLastRound = false;
    GameBoard.prototype.tracksPurchased = {}; //Map <routeIndex, playerId>
    GameBoard.prototype.cardsVisible = [];

    //constructor 
    function GameBoard (gameDataJSON) {
        
    }

    return GameBoard;
});