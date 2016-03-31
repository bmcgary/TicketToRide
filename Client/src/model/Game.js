var app = angular.module('ticketToRide');

app.factory('Game', function (Opponent, Player, GameBoard) {

    Game.prototype.opponents = [];
    Game.prototype.player = {};
    Game.prototype.board = {};
    Game.prototype.gameId = -1;
    Game.prototype.gameName = "";
    Game.prototype.gameHistory = [];
    Game.prototype.gameChat = [];
    Game.prototype.turnIndex = -1;
    Game.prototype.gameOver = false;

    //constructor 
    function Game (gameId) {
        this.gameId = gameId;
        this.player = new Player();
    }

    Game.prototype.updateLobbyData = function (gameDataJSON) {
        this.gameName = gameDataJSON.gameName;
        this.opponents = [];
        this.board = new GameBoard();
        this.turnIndex = 0;
        for(var index in gameDataJSON.players) {
            var player = gameDataJSON.players[index];
            this.opponents[index] = new Opponent(player, index);
        }
    }

    Game.prototype.setPrivateInfo = function (parameters) {
        var playerId = parameters.playerOrder;
        for(var index in this.opponents) {
            var opponent = this.opponents[index];
            if(opponent.playerId == playerId) {
                delete opponents[index];
            }
        }
        player.setInGameData(parameters);
    }

    Game.prototype.getPlayerById = function (playerId) {
        if(this.player.playerId == playerId) {
            return this.player;
        } else {
            for(var index in this.opponents) {
                var opponent = this.opponents[index];
                if(opponent.playerId == playerId) {
                    return opponent;
                }
            }
        }
    }

    return Game;
});
