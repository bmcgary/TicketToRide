var app = angular.module('ticketToRide');

app.factory('ClientAPI', function (WebSocket) {
    return {
    	//The API Methods sent from the Client to the Server
        login: function (username, password) {
            WebSocket.send(username + " " + password);
        },
        register: function (username, password, email) {
            WebSocket.send(username + " " + password + " " + email);
        }
    };
});