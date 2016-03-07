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
    function Game (gameDataJSON) {
        
    }

    return Game;
});