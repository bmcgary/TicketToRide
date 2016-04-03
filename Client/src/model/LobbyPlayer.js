var app = angular.module('ticketToRide');

app.factory('LobbyPlayer', function () {

    LobbyPlayer.prototype.playerName = "";
    LobbyPlayer.prototype.playerColor = "";
    LobbyPlayer.prototype.playerId = -1;

    //constructor 
    function LobbyPlayer (gameDataJSON) {
        this.playerName = gameDataJSON.username;
        this.playerColor = gameDataJSON.color;
        this.playerId = gameDataJSON.playerOrder;
    }

    return LobbyPlayer;
});