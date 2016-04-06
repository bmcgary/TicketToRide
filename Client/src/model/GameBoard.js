var app = angular.module('ticketToRide');

app.factory('GameBoard', function (TrainCardColor, StaticTrackList) {
    //constructor 
    function GameBoard () {
        this.deckHasDestinations = true;
        this.deckHasTrains = true;
        this.isFirstRound = true;
        this.isLastRound = false;
        this.routesPurchased = {};
        this.cardsVisible = [];
        this.mustDrawAgain = false;
        this.playerIdForTheLongestBonus = -1;
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

    //Getters
    GameBoard.prototype.getDeckHasDestinations = function () {
        return this.deckHasDestinations;
    }

    GameBoard.prototype.getDeckHasTrains = function () {
        return this.deckHasTrains;
    }

    GameBoard.prototype.getIsFirstRound = function () {
        return this.isFirstRound;
    }

    GameBoard.prototype.getIsLastRound = function () {
        return this.isLastRound;
    }

    GameBoard.prototype.getRoutesPurchased = function () {
        return this.routesPurchased;
    }

    GameBoard.prototype.getCardsVisibleAt = function (index) {
        return this.cardsVisible[index];
    }

    GameBoard.prototype.getMustDrawAgain = function () {
        return this.mustDrawAgain;
    }

    GameBoard.prototype.getPlayerIdForTheLongestBonus = function () {
        return this.playerIdForTheLongestBonus;
    }

    //Setters
    GameBoard.prototype.setIsLastRound = function (last) {
        this.isLastRound = last;
    }

    GameBoard.prototype.setMustDrawAgain = function (drawAgain) {
        this.getMustDrawAgain = drawAgain;
    }

    GameBoard.prototype.setPlayerIdForTheLongestBonus = function (id) {
        this.playerIdForTheLongestBonus = id;
    }

    return GameBoard;
});