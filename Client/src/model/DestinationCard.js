var app = angular.module('ticketToRide');

app.factory('DestinationCard', function () {
    //constructor
    function DestinationCard (gameDataJSON) {
        this.cityName1 = gameDataJSON.city1;
        this.cityName2 = gameDataJSON.city2;

        this.points = gameDataJSON.points;
        this.isComplete = gameDataJSON.completed;
    }

    DestinationCard.prototype.equals = function (card) {
        return this.cityName1 == card.cityName1 && this.cityName2 == card.cityName2 && this.points == card.points;
    }

    //Getters
    DestinationCard.prototype.getIsComplete = function () {
        return this.isComplete;
    }

    DestinationCard.prototype.getPoints = function () {
        return this.points;
    }

    DestinationCard.prototype.getCityName1 = function () {
        return this.cityName1;
    }
    DestinationCard.prototype.getCityName2 = function () {
        return this.cityName2;
    }

    return DestinationCard;
});
