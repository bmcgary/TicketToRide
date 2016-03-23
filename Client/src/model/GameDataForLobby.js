var app = angular.module('ticketToRide');

app.factory('GameDataForLobby', function (LobbyPlayer) {
    
    GameDataForLobby.prototype.players = [];
    GameDataForLobby.prototype.gameId = -1;
    GameDataForLobby.prototype.gameName = "";

    //constructor 
    function GameDataForLobby (gameDataJSON) {
        this.gameId = gameDataJSON.gameId;
        this.gameName = gameDataJSON.gameName;
        for(var index in gameDataJSON.players) {
            this.players.push(new LobbyPlayer(gameDataJSON.players[index]));
        }
    }

    return GameDataForLobby;
});