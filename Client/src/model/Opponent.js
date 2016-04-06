var app = angular.module('ticketToRide');

app.factory('Opponent', function () {
    //constructor 
    function Opponent (gameDataJSON, index) {
        this.playerName = gameDataJSON.username;
        this.playerColor = gameDataJSON.color;
        this.playerId = index;

        this.points = -1;
        this.trainsLeft = -1;
    }

    //Getter
    Opponent.prototype.getPlayerName = function () {
        return this.playerName;
    }

    Opponent.prototype.getPlayerColor = function () {
        return this.playerColor;
    }

    Opponent.prototype.getPoints = function () {
        return this.points;
    }

    Opponent.prototype.getTrainsLeft = function () {
        return this.trainsLeft;
    }

    Opponent.prototype.getPlayerId = function () {
        return this.playerId;
    }

    //Setter
    Opponent.prototype.setTrainsLeft = function (trains) {
        this.trainsLeft = trains;
    }

    Opponent.prototype.setPoints = function (points) {
        this.points = points;
    }

    return Opponent;
});