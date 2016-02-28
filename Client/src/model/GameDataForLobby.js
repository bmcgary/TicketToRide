var app = angular.module('ticketToRide');

app.factory('GameDataForLobby', function (LobbyPlayer) {
    
    //private data
    var players = [];
    var usersPlayerId = -1;
    var gameId = -1;
    var gameName = "";

    //constructor 
    function GameDataForLobby (players, usersPlayerId, gameId, gameName) {

    }

    //public getters
    GameDataForLobby.prototype.getPlayers = function() {
        return this.players;
    };

    GameDataForLobby.prototype.getPlayer = function(playerId) {
        return this.players[playerId];
    };

    GameDataForLobby.prototype.getUsersId = function() {
        return this.usersPlayerId;
    };

    GameDataForLobby.prototype.getGameId = function() {
        return this.gameId;
    };

    GameDataForLobby.prototype.getGameName = function() {
        return this.gameName;
    };

    //public methods



    return GameDataForLobby;
});