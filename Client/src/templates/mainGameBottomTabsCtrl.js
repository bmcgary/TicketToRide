var app = angular.module('ticketToRide');

app.controller('mainGameBottomTabsCtrl', function ($scope, ClientAPI) {

	setUpWidth();
		
	//Set the width size to the size of the view it is contained in.
	// that way the train cards are scaled to fit in their correct sport correctly
	function setUpWidth()
	{
		var myEl = document.getElementById("trainCardsInHandTab");
    	var width = myEl.offsetHeight;

		$scope.widthAmount = (width / 7) + "px";
	}

});


