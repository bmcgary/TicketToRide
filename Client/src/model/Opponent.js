var app = angular.module('ticketToRide');

app.factory('Opponent', function () {

    //private data
    var playerName = "";
    var playerColor = "";
    var points = -1;
    var trainsLeft = -1;
    var playerId = -1;

    //constructor 
    function Opponent (playerName, playerColor, points, trainsLeft, playerId) {
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.points = points;
        this.trainsLeft = trainsLeft;
        this.playerId = playerId;
    }

    //public data
    User.prototype.getName = function () {
        return this.playerName;
    };

    User.prototype.getColor = function () {
        return this.playerColor;
    };

    User.prototype.getPoints = function () {
        return this.points;
    };

    User.prototype.getTrainsLeft = function () {
        return this.trainsLeft;
    };

    User.prototype.getPlayerId = function () {
        return this.playerId;
    };

    return Opponent;
});