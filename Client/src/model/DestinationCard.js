var app = angular.module('ticketToRide');

app.factory('DestinationCard', function () {

    DestinationCard.prototype.cityName1 = "";
    DestinationCard.prototype.cityName2 = "";
    DestinationCard.prototype.isComplete = false;
    DestinationCard.prototype.points = -1;

    //constructor 
    function DestinationCard (gameDataJSON) {
        
    }

    return DestinationCard;
});