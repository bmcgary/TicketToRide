var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor, StaticTrackList) {

    GameBoard.prototype.deckHasDestinations = true;
    GameBoard.prototype.deckHasTrains = true;
    GameBoard.prototype.isFirstRound = true;
    GameBoard.prototype.isLastRound = false;
    GameBoard.prototype.routesPurchased = {}; //Map <routeIndex, playerId> //Maybe change playerId to Color
    GameBoard.prototype.cardsVisible = []; //TrainCardColor[]
    GameBoard.prototype.mustDrawAgain = false;

    //constructor 
    function GameBoard () {}

    GameBoard.prototype.updateCardsVisible = function () {

    }

    GameBoard.prototype.updateRoutesPurchased = function (routes, color) {
        for(var index in routes) {
            this.addRoutePurchased(routes[index], color);
        }
    }

    GameBoard.prototype.addRoutePurchased = function (routeIndex, color) {
        routesPurchased[routeIndex] = color;
    }

    return GameBoard;
});