var app = angular.module('ticketToRide');

app.factory('DestinationCard', function () {

    //private data
    var cityName1 = "";
    var cityName2 = "";
    var isComplete = false;
    var points = -1;

    //constructor 
    function DestinationCard (cityName1, cityName2, isComplete, points) {
        this.cityName1 = cityName1;
        this.cityName2 = cityName2;
        this.isComplete = isComplete;
        this.points = points;
    }

    //public getters
    DestinationCard.prototype.getCityName1 = function() {
        return this.cityName1;
    };

    DestinationCard.prototype.getCityName2 = function() {
        return this.cityName2;
    };

    DestinationCard.prototype.isComplete = function() {
        return this.isComplete;
    };

    DestinationCard.prototype.getPoints = function() {
        return this.points;
    };


    return DestinationCard;
});