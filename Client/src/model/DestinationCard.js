var app = angular.module('ticketToRide');

app.factory('DestinationCard', function () {

    DestinationCard.prototype.cityName1 = "";
    DestinationCard.prototype.cityName2 = "";
    DestinationCard.prototype.isComplete = false;
    DestinationCard.prototype.points = -1;

    //constructor 
    function DestinationCard (gameDataJSON) {
        this.cityName1 = gameDataJSON.city1;
        this.cityName2 = gameDataJSON.city2;

        this.points = gameDataJSON.points;
    }

    DestinationCard.prototype.equals = function (card) {
        return this.cityName1 == card.cityName1 && this.cityName2 == card.cityName2 && this.points == card.points;
    }

    return DestinationCard;
});
