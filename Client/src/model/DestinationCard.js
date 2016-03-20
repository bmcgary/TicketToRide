var app = angular.module('ticketToRide');

app.factory('DestinationCard', function () {

    DestinationCard.prototype.cityName1 = "";
    DestinationCard.prototype.cityName2 = "";
    DestinationCard.prototype.isComplete = false;
    DestinationCard.prototype.points = -1;

    //constructor 
    function DestinationCard (gameDataJSON) {
        //this.cityName1 = Cities[gameDataJSON.cityIndex1];
        //this.cityName2 = Cities[gameDataJSON.cityIndex2];

        this.points = gameDataJSON.points;
    }

    return DestinationCard;
});