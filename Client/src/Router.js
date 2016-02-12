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
	                templateUrl: 'templates/login.html'
	            })
	            .state('register', {
	                url: '/register',
	                templateUrl: 'templates/register.html'
	            });
    	});
})();

