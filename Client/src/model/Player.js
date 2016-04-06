var app = angular.module('ticketToRide');

app.factory('Player', function (TrainCardColor, DestinationCard) {
    //constructor 
    function Player () {
        this.playerName = "";
        this.playerColor = "";
        this.points = -1;
        this.trainsLeft = -1;
        this.trainCards = {}; //Map <color, int>
        this.destinationCards = [];
        this.playerId = -1;
        this.temporaryStorageOfCardsToBeSelectedFrom = [];
    }

    Player.prototype.setInGameData = function (gameDataJSON) {
        this.playerId = gameDataJSON.playerOrder;
        this.trainCards = {};
        this.destinationCards = [];

        for(var index in gameDataJSON.trainCards) {
            var pair = gameDataJSON.trainCards[index];
            this.trainCards[TrainCardColor.get(pair.color)] = pair.amount; 
        }

        for(var index in gameDataJSON.destinationCards) {
            this.destinationCards[index] = new DestinationCard(gameDataJSON.destinationCards[index]);
        }

        if("possibleDestinationCards" in gameDataJSON) {
            this.temporaryStorageOfCardsToBeSelectedFrom = gameDataJSON.possibleDestinationCards;
        }
    }

    Player.prototype.setDestinationComplete = function (destinationCompleted) {
        for(var index in this.destinationCards) {
            if(this.destinationCards[index].equals(this.destinationCompleted)) {
                this.destinationCards[index].setIsComplete(true);
                break;
            }
        }
    }

    Player.prototype.addDestinationCards = function (destinations) {
        this.destinations = [];
        for(var index in destinations) {
            this.destinationCards.push(new DestinationCard(destinations[index]));
        }
    }

    //Getters
    Player.prototype.getPlayerName = function () {
        return this.playerName;
    }

    Player.prototype.getPlayerColor = function () {
        return this.playerColor;
    }

    Player.prototype.getPoints = function () {
        return this.points;
    }

    Player.prototype.getTrainsLeft = function () {
        return this.trainsLeft;
    }

    Player.prototype.getTrainCards = function () {
        return this.trainCards;
    }

    Player.prototype.getDestinationCards = function () {
        return this.destinationCards;
    }

    Player.prototype.getPlayerId = function () {
        return this.playerId;
    }

    Player.prototype.getTemporaryStorageOfCardsToBeSelectedFrom = function () {
        return this.temporaryStorageOfCardsToBeSelectedFrom;
    }

    //Setters
    Player.prototype.incrementTrainCards = function (cardDrawn) {
        trainCards[cardDrawn] += 1;
    }

    Player.prototype.setTrainCards = function (cardDrawn, value) {
        trainCards[cardDrawn] = value;
    }

    Player.prototype.setPlayerName = function (name) {
        this.playerName = name;
    }

    Player.prototype.setPlayerColor = function (color) {
        this.playerColor = color;
    }

    Player.prototype.setTrainsLeft = function (trains) {
        this.trainsLeft = trains;
    }

    Player.prototype.setPoints = function (points) {
        this.points = points;
    }

    return Player;
});
