var app = angular.module('ticketToRide');

app.factory('ServerAPI', function () {
	//The API Methods sent from the Server to the Client

	var login = function (parameters) {
        alert("Logged in successfully!");
    }
    var register = function (parameters) {
        alert("Registered successfully!");
    }

    return {
        command: function (response) {
        	switch(response.command)
        	{
        		case "login": login(response.parameters); break;
        		case "register": register(response.parameters); break;
        		default: alert("Bad command");
        	}
    	}
    };
});