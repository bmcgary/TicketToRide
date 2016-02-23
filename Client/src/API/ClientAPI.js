var app = angular.module('ticketToRide');

//The API Methods sent from the Client to the Server
app.factory('ClientAPI', function (WebSocket) {
    return {
    	
        login: function (username, password) {
        	var jsonCommand = {
        		"command": "Login",
        		"parameters": {
        			"username": username,
        			"password": password
        		}
        	};
            WebSocket.send(jsonCommand);
        },

        register: function (username, password, email) {
            var jsonCommand = {
        		"command": "Register",
        		"parameters": {
        			"username": username,
        			"password": password,
        			"email": email
        		}
        	};
            WebSocket.send(jsonCommand);
        },

        forgotUserNameOrPassword: function () {
            var jsonCommand = {
                "command": "ForgotUserNameOrPassword",
                "parameters": {}
            };
            alert("Check specs before using");
        },

        logout: function () {
            var jsonCommand = {
                "command": "Logout",
                "parameters": {}
            };
            WebSocket.send(jsonCommand);
        },

        sendChat: function () {
            var jsonCommand = {
                "command": "SendChat",
                "parameters": {}
            };
            alert("Check specs before using");
        },

        getLeaderboards: function () {
            var jsonCommand = {
                "command": "GetLeaderboards",
                "parameters": {}
            };
            alert("Check specs before using");
        },

        updateJoinableGames: function () {
            var jsonCommand = {
                "command": "UpdateJoinableGames",
                "parameters": {}
            };
            WebSocket.send(jsonCommand);
        },

        updateUserGames: function () {
            var jsonCommand = {
                "command": "UpdateUserGames",
                "parameters": {}
            };
            WebSocket.send(jsonCommand);
        },

        joinGame: function (gameId, color) {
            var jsonCommand = {
                "command": "JoinGame",
                "parameters": {
                    "gameId": gameId,
                    "color": color
                }
            };
            WebSocket.send(jsonCommand);
        },

        createGame: function (gameName, color) {
            var jsonCommand = {
                "command": "CreateGame",
                "parameters": {
                    "gameName": gameName,
                    "color": color
                }
            };
            WebSocket.send(jsonCommand);
        },

        leaveGame: function (gameId) {
            var jsonCommand = {
                "command": "LeaveGame",
                "parameters": {
                    "gameId": gameId
                }
            };
            alert("Check specs before using");
        },

        startGame: function (gameId) {
            var jsonCommand = {
                "command": "StartGame",
                "parameters": {
                    "gameId": gameId
                }
            };
            WebSocket.send(jsonCommand);
        },

        addAI: function (gameId, difficulty) {
            var jsonCommand = {
                "command": "AddAI",
                "parameters": {
                    "gameId": gameId
                }
            };
            alert("Check specs before using");
        },

        sendClientModelInformation: function (gameId) {
            var jsonCommand = {
                "command": "SendClientModelInformation",
                "parameters": {
                    "gameId": gameId
                }
            };
            WebSocket.send(jsonCommand);
        },

        buyRoute: function (gameId, routeIndex, trainColor, numberOfWilds) {
            var jsonCommand = {
                "command": "BuyRoute",
                "parameters": {
                    "gameId": gameId,
                    "routeIndex": routeIndex,
                    "trainColor": trainColor,
                    "numberOfWilds": numberOfWilds
                }
            };
            WebSocket.send(jsonCommand);
        },

        drawTrainCard: function (gameId, cardLocation) {
            var jsonCommand = {
                "command": "DrawTrainCard",
                "parameters": {
                    "gameId": gameId,
                    "cardLocation": cardLocation
                }
            };
            WebSocket.send(jsonCommand);
        },

        getDestinations: function (gameId) {
            var jsonCommand = {
                "command": "GetDestinations",
                "parameters": {
                    "gameId": gameId
                }
            };
            WebSocket.send(jsonCommand);
        },

        selectDestinations: function (gameId, destinationsSelected) {
            var jsonCommand = {
                "command": "SelectDestinations",
                "parameters": {
                    "gameId": gameId,
                    "destinationsSelected": destinationsSelected
                }
            };
            WebSocket.send(jsonCommand);
        } 
    };
});