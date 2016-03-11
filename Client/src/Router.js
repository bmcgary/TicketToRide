(function () {
    'use strict';

    angular
        .module('ticketToRide', ['ui.router', 'ngWebSocket','shoppinpal.mobile-menu','ui.navbar','ui.bootstrap','smart-table'])
        .run( function ($rootScope, $state) {
	        $rootScope.$on('$stateChangeStart', function (event, next, current) {
	            //TODO: I am not sure what needs to be done in here
				//Check if the user is logged in, if they are do nothing,
				//if they aren't switch state to login
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
              .state('gameLobby', {
	            	url: '/gameLobby',
	            	templateUrl: 'templates/gameLobby.html',
	            	controller: "gameLobbyController"
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
						'chat@mainGame':{
							templateUrl:'templates/mainGameChat.html',
							controller:'mainGameChatCtrl'
						},
						'canvas@mainGame':{
							templateUrl:'templates/mainGameCanvas.html',
							controller:'mainGameCanvasCtrl'
						},
						'rightTabs@mainGame':{
							templateUrl:'templates/mainGameRightTabs.html',
							controller:'mainGameRightTabsCtrl'
						},
						'bottomTabs@mainGame':{
							templateUrl:'templates/mainGameBottomTabs.html',
							controller:'mainGameBottomTabsCtrl'
						}
					}
				})
	            .state('mainGame2', {
	            	url: '/game2',
	            	views:{
						'':{templateUrl:'templates/gameScaffolding2.html',
							controller:'gameScaffoldingCtrl'
						},
						'menu@mainGame2':{
							templateUrl:'templates/mainGameMenu.html',
							controller:'mainGameMenuCtrl'
						},
						'chat@mainGame2':{
							templateUrl:'templates/mainGameChat.html',
							controller:'mainGameChatCtrl'
						},
						'canvas@mainGame2':{
							templateUrl:'templates/mainGameCanvas.html',
							controller:'mainGameCanvasCtrl'
						},
						'tabs@mainGame2':{
							templateUrl:'templates/mainGameTabs.html',
							controller:'mainGameTabsCtrl'
						}
					}
	            })

;
    	});
})();
