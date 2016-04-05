var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor, StaticTrackList) {

    GameBoard.prototype.deckHasDestinations = true;
    GameBoard.prototype.deckHasTrains = true;
    GameBoard.prototype.isFirstRound = true;
    GameBoard.prototype.isLastRound = false;
    GameBoard.prototype.routesPurchased = {}; //Map <routeIndex, playerId> //Maybe change playerId to Color
    GameBoard.prototype.cardsVisible = []; //TrainCardColor[]
    GameBoard.prototype.mustDrawAgain = false;
    GameBoard.prototype.playerIdForTheLongestBonus = -1;

    //constructor 
    function GameBoard () {
        
    }

    GameBoard.prototype.updateCardsVisible = function (availableTrainCards) {
        for(var i = 0; i < 5; i++) {
            this.cardsVisible[i] = availableTrainCards[i].color;
        }
    }

    GameBoard.prototype.resetRoutesPurchased = function () {
        this.routesPurchased = {};
    }

    GameBoard.prototype.addRoutesPurchased = function (routes, trainColor) {
        for(var index in routes) {
            this.addRoutePurchased(routes[index], trainColor);
        }
    }

    GameBoard.prototype.addRoutePurchased = function (routeIndex, trainColor) {
        this.routesPurchased[routeIndex] = TrainCardColor.get(trainColor);
    }

    return GameBoard;
});