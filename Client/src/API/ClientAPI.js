var app = angular.module('ticketToRide');

app.factory('ClientAPI', function (WebSocket) {
    return {
    	//The API Methods sent from the Client to the Server
    	
        login: function (username, password) {
        	var jsonCommand = {
        		"command": "login",
        		"parameters": {
        			"username": username,
        			"password": password
        		}
        	}
            WebSocket.send(jsonCommand);
        },

        register: function (username, password, email) {
            var jsonCommand = {
        		"command": "register",
        		"parameters": {
        			"username": username,
        			"password": password,
        			"email": email
        		}
        	}
            WebSocket.send(jsonCommand);
        }
    };
});