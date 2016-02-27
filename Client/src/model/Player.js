var app = angular.module('ticketToRide');

app.factory('PlayerDataMin', function (TrainCardColor, DestinationCard) {

    //private data
    var name = "";
    var color = "";
    var points = -1;
    var trainsLeft = -1;
    var trainCards = {}; //Map <color, int>
    var destinationCards = [];
    var playerId = -1;

    //constructor 
    function PlayerDataMin (gameData) {

    }

    //public data

    return PlayerDataMin;
});