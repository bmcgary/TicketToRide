var app = angular.module('ticketToRide');

app.factory('TrainRoute', function (TrainCardColor) {

    TrainRoute.prototype.trackId = -1;
    TrainRoute.prototype.trainsRequired = -1;
    TrainRoute.prototype.trackColor = "";
    TrainRoute.prototype.boundingBox = {}; //We need some kind of bounding rectangle here
    TrainRoute.prototype.tracks = []; //This is a list of all the individual bounding rectangles in a route

    //constructor 
    function TrainRoute (trackId, trainsRequired, trackColor, coordinates) {
        this.trackId = trackId;
        this.trainsRequired = trainsRequired;
        this.trackColor = trackColor;
        this.coordinates = coordinates;
    }

    TrainRoute.prototype.toString = function() {
        return "TrackId = " + trackId + " TrainsRequired = " + this.trainsRequired + " TrackColor = " + this.trackColor + "\n";
    };


    return TrainRoute;
});