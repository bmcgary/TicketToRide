var app = angular.module('ticketToRide');

app.factory('Game', function (Opponent, Player, GameBoard) {

    Game.prototype.opponents = [];
    Game.prototype.player = {};
    Game.prototype.board = {};
    Game.prototype.gameId = -1;
    Game.prototype.gameName = "";
    Game.prototype.gameHistory = [];
    Game.prototype.gameChat = [];

    //constructor 
    function Game (gameId) {
        this.gameId = gameId;
        this.player = new Player();
    }

    Game.prototype.updateLobbyData = function (gameDataJSON) {
        this.gameName = gameDataJSON.gameName;
        this.opponents = [];
        this.board = new GameBoard();
        //for gameDataJSON.opponents;
    }

    Game.prototype.getPlayerById = function (playerId) {

        if(this.player.playerId == playerId) {
            return this.player;
        } else {
            for(var index in this.opponents) {
                if(this.opponents[index].playerId == playerId) {
                    return opponents[index];
                }
            }
        }
    }

    return Game;
});