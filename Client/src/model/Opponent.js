var app = angular.module('ticketToRide');

app.factory('Opponent', function () {

    Opponent.prototype.playerName = "";
    Opponent.prototype.playerColor = "";
    Opponent.prototype.points = -1;
    Opponent.prototype.trainsLeft = -1;
    Opponent.prototype.playerId = -1;

    //constructor 
    function Opponent (gameDataJSON) {

    }

    return Opponent;
});