var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor, StaticTrackList) {

    //private data
    var deckHasDestinations = true;
    var deckHasTrains = true;
    var isFirstRound = true;
    var isLastRound = false;
    var tracksPurchased = {}; //Map <trackIndex, playerId>
    var cardsVisible = [];

    //constructor 
    function GameBoard (deckHasDestinations, deckHasTrains, isFirstRound, isLastRound, tracksPurchased, cardsVisible) {
        this.deckHasDestinations = deckHasDestinations;
        this.deckHasTrains = deckHasTrains;
        this.isFirstRound = isFirstRound;
        this.isLastRound = isLastRound;
        this.tracksPurchased = tracksPurchased;
        this.cardsVisible = cardsVisible;
    }

    //public getters
    GameBoard.prototype.deckHasDestinations = function() {
        return this.deckHasDestinations;
    };

    GameBoard.prototype.deckHasTrains = function() {
        return this.deckHasTrains;
    };

    GameBoard.prototype.isFirstRound = function() {
        return this.isFirstRound;
    };

    GameBoard.prototype.isLastRound = function() {
        return this.isLastRound;
    };

    GameBoard.prototype.getTracksPurchased = function() {
        return this.tracksPurchased;
    };

    GameBoard.prototype.getTrackById = function(trackId) {
        return this.tracksPurchased[trackId];
    };

    GameBoard.prototype.getCardsVisible = function() {
        return this.cardsVisible;
    };

    //public methods


    return GameBoard;
});