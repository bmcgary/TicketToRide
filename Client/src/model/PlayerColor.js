//Blue, Red, Green, Yellow and Black

var app = angular.module('ticketToRide');

app.factory('PlayerColor', function () {
	return {
		BLACK: "black",
		RED: "red",
		YELLOW: "yellow",
		GREEN: "green",
		BLUE: "blue"
	};
});