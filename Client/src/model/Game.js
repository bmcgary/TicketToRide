var app = angular.module('ticketToRide');

app.factory('Game', function (Opponent, Player, GameBoard) {

    //private data
    var opponents = [];
    var player;
    var board;
    var gameId = -1;
    var gameName = "";
    var gameHistory = [];
    var gameChat = [];

    //constructor 
    function Game (opponents, player, board, gameId, gameName, gameHistory, gameChat) {

    }

    //public data
    Game.prototype.getOpponents = function() {
        return this.opponents;
    };

    Game.prototype.getPlayer = function() {
        return this.player;
    };

    Game.prototype.getBoard = function() {
        return this.board;
    };

    Game.prototype.getGameId = function() {
        return this.gameId;
    };

    Game.prototype.getGameName = function() {
        return this.gameName;
    };

    Game.prototype.getGameHistory = function() {
        return this.gameHistory;
    };

    Game.prototype.getGameChat = function() {
        return this.gameChat;
    };

    //public methods

    return Game;
});