var app = angular.module('ticketToRide');

app.factory('Player', function (TrainCardColor, DestinationCard) {

    Player.prototype.playerName = "";
    Player.prototype.playerColor = "";
    Player.prototype.points = -1;
    Player.prototype.trainsLeft = -1;
    Player.prototype.trainCards = {}; //Map <color, int>
    Player.prototype.destinationCards = [];
    Player.prototype.playerId = -1;

    Player.prototype.temporaryStorageOfCardsToBeSelectedFrom = [];

    //constructor 
    function Player () {}

    Player.prototype.updateLobbyData = function (gameDataJSON) {

    }

    Player.prototype.setInGameData = function (gameDataJSON) {
        this.playerId = gameDataJSON.playerOrder;
        this.trainCards = {};
        this.destinationCards = [];

        for(var trainColor in gameDataJSON.trainCards) {
            this.trainCards[TrainCardColor.get(trainColor)] = gameDataJSON.trainCards[trainColor];
        }

        for(var index in gameDataJSON.destinationCards) {
            this.destinationCards[index] = new DestinationCard(gameDataJSON.destinationCards[index]);
        }

        if("possibleDestinationCards" in gameDataJSON) {
            temporaryStorageOfCardsToBeSelectedFrom = gameDataJSON.possibleDestinationCards;
        }
    }

    Player.prototype.setDestinationComplete = function (destinationCompleted) {
        for(var index in destinationCards) {
            if(destinationCards[index].equals(destinationCompleted)) {
                destinationCards[index].isComplete = true;
                break;
            }
        }
    }

    Player.prototype.addDestinationCards = function (destinations) {
        for(var index in destinations) {
            destinationCards.push(new DestinationCard(destinations[index]));
        }
    }

    return Player;
});
