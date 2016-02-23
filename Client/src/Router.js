(function () {
    'use strict';

    angular
        .module('ticketToRide', ['ui.router', 'ngWebSocket'])
        .run( function ($rootScope, $state) {
	        $rootScope.$on('$stateChangeStart', function (event, next, current) {
	            //TODO: I am not sure what needs to be done in here
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
	            })
	            .state('forgot', {
	            	url: '/forgot',
	            	templateUrl: 'templates/forgot.html',
	            	controller: "forgotController"
	            })
				.state('mainGame', {
					url:'/game',
					views:{
						'':{templateUrl:'templates/gameScaffolding.html',
							controller:'gameScaffoldingCtrl'
						},
						'menu@mainGame':{
							templateUrl:'templates/mainGameMenu.html',
							controller:'mainGameMenuCtrl'
						},
						'canvas@mainGame':{
							templateUrl:'templates/mainGameCanvas.html',
							controller:'mainGameCanvasCtrl'
						},
						'tabs@mainGame':{
							templateUrl:'templates/mainGameTabs.html',
							controller:'mainGameTabsCtrl'
						}
					}
				});
    	});
})();

