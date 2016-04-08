var app = angular.module('ticketToRide');

app.factory('Game', function (Opponent, Player, GameBoard) {
    //constructor
    function Game (id) {
        this.gameId = id;
        this.player = new Player();

        this.opponents = [];
        this.board = {};
        this.gameName = "";
        this.gameHistory = [];
        this.gameChat = [];
        this.turnIndex = -1;
        this.gameOver = false;
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
            if(opponent.getPlayerId() == playerId) {
                this.player.setPlayerColor(this.opponents[index].playerColor);
                this.player.setPlayerName(this.opponents[index].playerName);
                this.opponents.splice(index,1);
            }
        }
        this.player.setInGameData(parameters);
    }

    Game.prototype.getPlayerById = function (playerId) {
        if(this.player.getPlayerId() == playerId) {
            return this.player;
        } else {
            for(var index in this.opponents) {
                var opponent = this.opponents[index];
                if(opponent.getPlayerId() == playerId) {
                    return opponent;
                }
            }
        }
    }

    //Getters
    Game.prototype.getTurnIndex = function () {
        return this.turnIndex;
    }

    Game.prototype.getGameOver = function () {
        return this.gameOver;
    }

    Game.prototype.getGameName = function () {
        return this.gameName;
    }

    Game.prototype.getGameId = function () {
        return this.gameId;
    }

    Game.prototype.getBoard = function () {
        return this.board;
    }

    Game.prototype.getPlayer = function () {
        return this.player;
    }

    Game.prototype.getOpponents = function () {
        return this.opponents;
    }

    //Setters
    Game.prototype.setTurnIndex = function (index) {
        this.turnIndex = index;
    }

    Game.prototype.setGameOver = function (over) {
        this.gameOver = over;
    }

    Game.prototype.getWinnerName() = function () {
        var winner = this.player;

        for(var index in this.opponents)
        {
            if(this.opponents[index].getPoints() > winner.getPoints())
            {
                winner = this.opponents[index];
            }
        }

        return winner.getPlayerName();
    }

    return Game;
});
