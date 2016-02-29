var app = angular.module('ticketToRide');

app.factory('Player', function (TrainCardColor, DestinationCard) {

    //private data
    var playerName = "";
    var playerColor = "";
    var points = -1;
    var trainsLeft = -1;
    var trainCards = {}; //Map <color, int>
    var destinationCards = [];
    var playerId = -1;

    //constructor 
    function Player (playerName, playerColor, points, trainsLeft, trainCards, destinationCards, playerId) {
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.points = points;
        this.trainsLeft = trainsLeft;
        this.trainCards = trainCards;
        this.destinationCards = destinationCards;
        this.playerId = playerId;
    }

    //public getters
    Player.prototype.getName = function() {
        return this.playerName;
    };

    Player.prototype.getColor = function() {
        return this.playerColor;
    };

    Player.prototype.getPoints = function() {
        return this.points;
    };

    Player.prototype.getTrainsLeft = function() {
        return this.trainsLeft;
    };

    Player.prototype.getTrainCards = function() {
        return this.trainCards;
    };

    Player.prototype.getTrainCardByType = function(type) {
        return this.trainCards[type];
    };

    Player.prototype.getDestinationCards = function() {
        return this.destinationCards;
    };

    Player.prototype.getPlayerId = function() {
        return this.playerId;
    };

    //public methods


    return Player;
});