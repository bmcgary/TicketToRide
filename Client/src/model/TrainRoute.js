var app = angular.module('ticketToRide');

app.factory('TrainRoute', function (TrainCardColor) {

    //private data
    var trackId = -1;
    var trainsRequired = -1;
    var trackColor = "";
    var coordinates = {
        "boundingBox": {}, //We need some kind of bounding rectangle here
        "tracks": [] //This is a list of all the individual bounding rectangles in a route
    }; 

    //constructor 
    function TrainRoute (trackId, trainsRequired, trackColor, coordinates) {
        this.trackId = trackId;
        this.trainsRequired = trainsRequired;
        this.trackColor = trackColor;
        this.coordinates = coordinates;
    }

    //public data
    TrainRoute.prototype.getTrackId = function() {
        return this.trackId;
    };

    TrainRoute.prototype.getTrainsRequired = function() {
        return this.trainsRequired;
    };

    TrainRoute.prototype.getTrackColor = function() {
        return this.trackColor;
    };

    TrainRoute.prototype.getCoordinates = function() {
        return this.coordinates;
    };

    TrainRoute.prototype.toString = function() {
        return "TrackId = " + trackId + " TrainsRequired = " + this.trainsRequired + " TrackColor = " + this.trackColor + "\n";
    };


    return TrainRoute;
});