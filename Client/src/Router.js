(function () {
    'use strict';

    angular
        .module('ticketToRide', ['ui.router', 'ngWebSocket'])
        .run( function ($rootScope, $state) {
	        $rootScope.$on('$stateChangeStart', function (event, next, current) {
	            console.log("Ray sucks");
	        });
    	})
        .config( function ($stateProvider, $urlRouterProvider) {
	        $urlRouterProvider.otherwise('/login');
	        $stateProvider
	            .state('login', {
	                url: '/login',
	                templateUrl: 'templates/login.html',
	                controller: "loginController"
	            })
	            .state('register', {
	                url: '/register',
	                templateUrl: 'templates/register.html',
	                controller: "registerController"
	            });
    	})
    	.factory('ATP', function ($websocket, $location) {
            
            // Open a WebSocket connection
            //var ws = $websocket("ws://"+$location.host()+":8080/");
            var ws = $websocket("ws://localhost:8080/");
            var atp = [];
            ws.onMessage(function (event) {
                console.log('message: ', event.data);
                var response = null;
                try {
                    response = angular.fromJson(event.data);

                } catch (e) {
                    console.log('error: ', e);
                    response = {'error': e};
                    alert("server error");
                }
                if (response != null) {
                    alert("success");
                } else {
                    alert("crap");
                }
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
                atp: atp,
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
})();

