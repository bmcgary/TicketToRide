var app = angular.module('ticketToRide');

app.factory('GameDataForLobby', function (LobbyPlayer) {
    
    GameDataForLobby.prototype.players = [];
    GameDataForLobby.prototype.usersPlayerId = -1;
    GameDataForLobby.prototype.gameId = -1;
    GameDataForLobby.prototype.gameName = "";

    //constructor 
    function GameDataForLobby (gameDataJSON) {

    }

    return GameDataForLobby;
});