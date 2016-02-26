var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor) {

    //private data
    var deckHasDestinations = true;
    var deckHasTrains = true;
    var isFirstRound = true;
    var isLastRound = false;
    var tracksPurchased = {}; //Map <trackIndex, playerId>
    var cardVisible1;
    var cardVisible2;
    var cardVisible3;
    var cardVisible4;
    var cardVisible5;

    //constructor 
    function GameBoard (gameData) {

    }

    //public data

    return GameBoard;
});