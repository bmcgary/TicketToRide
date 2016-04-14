(function () {
    'use strict';

    angular
        .module('ticketToRide', ['ui.router', 'ngWebSocket','shoppinpal.mobile-menu','ui.navbar','ui.bootstrap','smart-table', 'ui.bootstrap.modal'])
        .run( function ($rootScope, $state) {
	        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
				//Check if the user is logged in, if they are do nothing,
				//if they aren't switch state to login

				if (toState.authenticate && !$rootScope.loggedIn){ //TODO The "true" should be changed to check the model if the user is acutally logged in or not
				  // User isn’t authenticated
				  $state.transitionTo("login");
//Prob not needed but just incase ------> //$rootScope.redirTo = toState.name; //save this so once they log in we redirect to their first choice
				  event.preventDefault(); 
				}

	        });
    	})
        .config( function ($stateProvider, $urlRouterProvider) {
	        $urlRouterProvider.otherwise('/login');
	        $stateProvider
	            .state('login', {
	                url: '/login',
	                templateUrl: 'templates/login.html',
	                controller: "loginController",
					authenticate: false
	            })
	            .state('register', {
	                url: '/register',
	                templateUrl: 'templates/register.html',
	                controller: "registerController",
					authenticate: false
	            })
	            .state('forgot', {
	            	url: '/forgot',
	            	templateUrl: 'templates/forgot.html',
	            	controller: "forgotController",
					authenticate: false
	            })
              .state('gameLobby', {
	            	url: '/gameLobby',
	            	templateUrl: 'templates/GameLobby.html',
	            	controller: "gameLobbyController",
					authenticate: true
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
					},
					authenticate: true
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
						'rightTabs@mainGame2':{
							templateUrl:'templates/mainGameRightTabs.html',
							controller:'mainGameRightTabsCtrl'
						},
						'bottomTabs@mainGame2':{
							templateUrl:'templates/mainGameBottomTabs.html',
							controller:'mainGameBottomTabsCtrl'
						}
					},
					authenticate: true
	            })

;
    	});
})();
