var app = angular.module('ticketToRide', ['immutable']);

app.factory('TrainCardColor', function () {
	return {
		WILD: "wild",
		BLACK: "black",
		WHITE: "white",
		RED: "red",
		YELLOW: "yellow",
		GREEN: "green",
		BLUE: "blue",
		PURPLE: "purple",
		ORANGE: "orange"
	};
});