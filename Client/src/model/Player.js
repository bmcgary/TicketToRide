var app = angular.module('ticketToRide');

app.factory('Player', function (TrainCardColor, DestinationCard) {

    Player.prototype.playerName = "";
    Player.prototype.playerColor = "";
    Player.prototype.points = -1;
    Player.prototype.trainsLeft = -1;
    Player.prototype.trainCards = {}; //Map <color, int>
    Player.prototype.destinationCards = [];
    Player.prototype.playerId = -1;

    //constructor 
    function Player (gameDataJSON) {

    }

    return Player;
});