var app = angular.module('ticketToRide');

app.factory('LobbyPlayer', function () {

    User.prototype.playerName = "";
    User.prototype.playerColor = "";
    User.prototype.playerId = -1;

    //constructor 
    function LobbyPlayer (gameDataJSON) {

    }

    return LobbyPlayer;
});