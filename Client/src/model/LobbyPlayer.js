var app = angular.module('ticketToRide');

app.factory('LobbyPlayer', function () {

    //private data
    var playerName = "";
    var playerColor = "";
    var playerId = -1;

    //constructor 
    function LobbyPlayer (playerName, playerColor, playerId) {
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.playerId = playerId;
    }

    //public data
    User.prototype.getName = function () {
        return this.playerName;
    };

    User.prototype.getColor = function () {
        return this.playerColor;
    };

    User.prototype.getPlayerId = function () {
        return this.playerId;
    };

    return LobbyPlayer;
});