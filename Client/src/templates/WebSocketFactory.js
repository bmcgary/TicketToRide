var app = angular.module('ticketToRide');

app.factory('WebSocket', function($websocket, $location, ServerAPI) {
	//WebSocket Connection

	var ws = $websocket("ws://" + $location.host + ":8080/");
	//var ws = $websocket("ws://localhost:8080/");
    ws.onMessage(function (event) {
        console.log('message: ', event.data);
        var response;
        try {
            response = angular.fromJson(event.data);
            alert(response);
        } catch (e) {
            console.log('error: ', e);
            response = {'error': e};
            alert("server error");
        }
        //use ServerAPI here
    });
    ws.onError(function (event) {
        console.log('connection Error', event);
    });
    ws.onClose(function (event) {
        console.log('connection closed', event);
    });
    ws.onOpen(function () {
        console.log('connection open');
        ws.send('HELLO SERVER');
    });

    return {
    	//WebSocket Methods
        status: function () {
            return ws.readyState;
        },
        send: function (message) {
            if (angular.isString(message)) {
                ws.send(message);
            }
            else if (angular.isObject(message)) {
                ws.send(JSON.stringify(message));
            }
        }
    };
});