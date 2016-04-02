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

    Player.prototype.setInGameData = function (gameDataJSON) {
        this.playerId = gameDataJSON.playerOrder;
        this.trainCards = {};
        this.destinationCards = [];

        for(var index in gameDataJSON.trainCards) {
            var pair = gameDataJSON.trainCards[index];
            this.trainCards[TrainCardColor.get(pair.color)] = pair.amount; 
        }

        for(var index in gameDataJSON.destinationCards) {
            Player.prototype.destinationCards[index] = new DestinationCard(gameDataJSON.destinationCards[index]);
        }

        if("possibleDestinationCards" in gameDataJSON) {
            this.temporaryStorageOfCardsToBeSelectedFrom = gameDataJSON.possibleDestinationCards;
        }
    }

    Player.prototype.setDestinationComplete = function (destinationCompleted) {
        for(var index in this.destinationCards) {
            if(this.destinationCards[index].equals(this.destinationCompleted)) {
                this.destinationCards[index].isComplete = true;
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

    return Player;
});
