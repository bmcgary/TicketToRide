var app = angular.module('ticketToRide');

app.factory('GameDataForLobby', function (LobbyPlayer) {
    
    GameDataForLobby.prototype.players = [];
    GameDataForLobby.prototype.gameId = -1;
    GameDataForLobby.prototype.gameName = "";

    //constructor 
    function GameDataForLobby (gameDataJSON) {
        GameDataForLobby.prototype.gameId = gameDataJSON.gameID;
        GameDataForLobby.prototype.gameName = gameDataJSON.gameName;
        for(var index in gameDataJSON.players) {
            GameDataForLobby.prototype.players[index] = new LobbyPlayer(gameDataJSON.players[index]);
        }
    }

    return GameDataForLobby;
});
