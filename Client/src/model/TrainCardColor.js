var app = angular.module('ticketToRide');

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
		ORANGE: "orange",

		get: function (colorName) {
			if(colorName == "none") {
				return this.WILD;
			} else {
				return colorName;
			}
		}
	};
});