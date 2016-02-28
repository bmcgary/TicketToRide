var app = angular.module('ticketToRide');

app.factory('TrainTrack', function (TrainCardColor) {

    //private data
    var trackId = -1;
    var trainsRequired = -1;
    var trackColor = "";
    var coordinates = {}; //We need some kind of rectangle to contain here

    //constructor 
    function TrainTrack (trackId, trainsRequired, trackColor, coordinates) {
        this.trackId = trackId;
        this.trainsRequired = trainsRequired;
        this.trackColor = trackColor;
        this.coordinates = coordinates;
    }

    //public data
    TrainTrack.prototype.getTrackId = function() {
        return this.trackId;
    };

    TrainTrack.prototype.getTrainsRequired = function() {
        return this.trainsRequired;
    };

    TrainTrack.prototype.getTrackColor = function() {
        return this.trackColor;
    };

    TrainTrack.prototype.getCoordinates = function() {
        return this.coordinates;
    };

    TrainTrack.prototype.toString = function() {
        return "TrackId = " + trackId + " TrainsRequired = " + this.trainsRequired + " TrackColor = " + this.trackColor + "\n";
    };


    return TrainTrack;
});