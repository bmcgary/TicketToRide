var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor, StaticTrackList) {

    GameBoard.prototype.deckHasDestinations = true;
    GameBoard.prototype.deckHasTrains = true;
    GameBoard.prototype.isFirstRound = true;
    GameBoard.prototype.isLastRound = false;
    GameBoard.prototype.tracksPurchased = {}; //Map <routeIndex, playerId> //Maybe change playerId to Color
    GameBoard.prototype.cardsVisible = []; //TrainCardColor[]
    GameBoard.prototype.mustDrawAgain = false;

    //constructor 
    function GameBoard (gameDataJSON) {
        
    }

    return GameBoard;
});