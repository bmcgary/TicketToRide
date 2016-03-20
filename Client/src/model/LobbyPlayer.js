var app = angular.module('ticketToRide');

app.factory('LobbyPlayer', function () {

    User.prototype.playerName = "";
    User.prototype.playerColor = "";
    User.prototype.playerId = -1;

    //constructor 
    function LobbyPlayer (gameDataJSON) {
        this.playerName = gameDataJSON.username;
        this.playerColor = gameDataJSON.color;
        this.playerId = gameDataJSON.playerOrder;
    }

    return LobbyPlayer;
});