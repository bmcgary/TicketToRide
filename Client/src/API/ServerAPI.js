var app = angular.module('ticketToRide');

app.factory('ServerAPI', function() {
    return {
    	//The API Methods sent from the Server to the Client
        login: function () {
            alert("Logged in successfully!");
        },
        register: function () {
            alert("Registered successfully!");
        }
    };
});