var app = angular.module('ticketToRide');

app.factory('ModelFacade', function ($state, $rootScope, Game, GameDataForLobby, ModelContainer, TrainCardColor, StaticTrackList) {
	//store and access game models
    var username = "";
	var usersGames = {};
    var joinableGames = {};
	var gameInView = -1;

	var getModel = function () {
		return usersGames[gameInView];
	};

    //Lobby stuff=======================================================================================
    $rootScope.$on('server:UpdateJoinableGames', function (event, parameters) {
        if(parameters.description == "success") {
            for(var index in parameters.games) {
                joinableGames[parameters.games[index].gameId] = new GameDataForLobby(parameters.games[index]);
            }    
            $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames);        
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: UpdateJoinableGames-" + parameters.description);
        }
    });

    $rootScope.$on('server:UpdateGame', function (event, parameters) {
        if(parameters.description == "add" || parameters.description == "update") {

            joinableGames[parameters.gameId] = new GameDataForLobby(parameters.game);
            $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames);  
        } else if(parameters.description == "delete") {
            joinableGames[parameters.gameId] = {};
            $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames);  
        } else {
            alert("Unknown Server Response: UpdateGame-" + parameters.description);
        }
    });

    $rootScope.$on('server:UpdateUserGames', function (event, parameters) {
        if(parameters.description == "success") {
            //Still have stuff to figure out/////////////////////
            for(var index in parameters.games) {
                var gameId = parameters.games[index].gameId;
                if(!(gameId in usersGames))
                {
                    usersGames[gameId] = new Game(parameters.games[index].gameId);
                }
                usersGames[parameters.games[index].gameId].updateLobbyData(parameters.games[index], this.username);
                $rootScope.$broadcast('model:UpdateUserGames', new ModelContainer(getModel()));
            }            
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: UpdateUserGames-" + parameters.description);
        }
    });

	$rootScope.$on('server:StartGame', function (event, parameters) {

        if(parameters.description == "success") {
            //Do nothing
        } else if(parameters.description == "unable to start game") {
            alert("Unable to Start Game");
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: StartGame-" + parameters.description);
        }
    });

    $rootScope.$on('server:CreateGame', function (event, parameters) {
        if(parameters.description == "success") {
            //Do nothing
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: CreateGame-" + parameters.description);
        }
    });

    $rootScope.$on('server:JoinGame', function (event, parameters) {
        if(parameters.description == "success") {
            //Do nothing
        } else if(parameters.description == "invalid input") {
            alert("Invalid Input");
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: JoinGame-" + parameters.description);
        }
    });

    $rootScope.$on('server:SendClientModelInformation', function (event, parameters) {
        if(parameters.description == "success") {
            //Do nothing       
        } else if(parameters.description == "not in game") {
            alert("You cannot request that information");
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: SendClientModelInformation-" + parameters.description);
        }
    });

    $rootScope.$on('server:Logout', function (event, parameters) {
        $state.go('login');
    });

    $rootScope.$on('server:LeaveGame', function (event, parameters) {
        //future
        /*delete usersGames[parameters.gameId];*/
    });


    //In Game===========================================================================================
    var broadcast = function (gameId, command) {
        if(gameId == gameInView) { 
            $rootScope.$broadcast('model:' + command, new ModelContainer(getModel()));
        }
    };

    $rootScope.$on('server:PrivateClientModelInformation', function (event, parameters) {
        if(parameters.description == "success") { //Maybe remove description and need gameId
            usersGames[parameters.gameId].player.setInGameData(parameters);
            broadcast(parameters.gameId, 'PrivateClientModelInformation');

            if("possibleDestinationCards" in parameters) {
                $rootScope.$broadcast('model:GetDestinations', parameters.possibleDestinationCards);
            }

        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: PrivateClientModelInformation-" + parameters.description);
        }
    });

    $rootScope.$on('server:PublicClientModelInformation', function (event, parameters) {
        if(parameters.description == "success") {
            for(var index in parameters.players) {
                var playerId = parameters.players[index].playerOrder;
                usersGames[parameters.gameId].getPlayerById(playerId).trainsLeft = parameters[index].trainsLeft;
                usersGames[parameters.gameId].board.setRoutesPurchased(players.routes, game.getPlayerById(playerId).playerColor);
            }

        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: PublicClientModelInformation-" + parameters.description);
        }
    });

    $rootScope.$on('server:SendChat', function (event, parameters) {
        //future

        broadcast(parameters.gameId, 'SendChat');
    });


    $rootScope.$on('server:BuyRoute', function (event, parameters) {
        if(parameters.description == "success") {
            //do logic

            broadcast(parameters.gameId, 'BuyRoute');
        } else if(parameters.description == "invalid route location") {
            alert("Invalid Route Location");
        } else if(parameters.description == "insuffcient trains") {
            alert("Insuffcient Trains");
        } else if(parameters.description == "invalid train color") {
            alert("Invalid Train Color");
        } else if(parameters.description == "not your turn") {
            alert("It is not your turn!");
        } else if(parameters.description == "server error") {
            alert("Server Error");
        } else {
            alert("Unknown Server Response: BuyRoute-" + parameters.description);
        }
    });


    $rootScope.$on('server:DrawTrainCard', function (event, parameters) {
		//do logic

        broadcast(parameters.gameId, 'DrawTrainCard');
    });


    $rootScope.$on('server:GetDestinations', function (event, parameters) {
		//do logic

        broadcast(parameters.gameId, 'GetDestinations');
    });


    $rootScope.$on('server:SelectDestinations', function (event, parameters) {
		//do logic

        broadcast(parameters.gameId, 'SelectDestinations');
    });

    return {

        //CanDo Methods=================================================================================
    	canBuyRoute: function (routeIndex, trainColor, numberOfWilds) {
            var model = getModel();

            var type = typeof(model.board.tracksPurchased[routeIndex]);
            if(type != 'undefined' && type != 'null') { //route is owned
                return false;
            } else if(numberOfWilds > model.player.trainCards[TrainCardColor.WILD]) { //The player has enough wilds
                return false;
            } else { //The player has enough cards of that train color
                return StaticTrackList.routeIndex.trainsRequired <= (numberOfWilds + model.player.trainCards[trainColor]);
            }
    	},

    	canDrawCard: function (cardLocation) {
    		var model = getModel();

            if(cardLocation == 0) { //The player is drawing from the top of the deck
                return model.board.deckHasTrains;
            } else if(model.board.mustDrawAgain) { //The player has already drawn one card, so the second cannot be a wild
                return TrainCardColor.WILD != model.board.cardsVisible[cardLocation - 1];
            } else {
                return  true;
            }
    	},

    	canDrawDestination: function () {
    		var model = getModel();

            //The player can draw destinations, as long as there are destinations to be drawn, and the player has not already drawn a train card
    		return model.board.deckHasDestinations && !model.board.mustDrawAgain;
    	},

    	canSelectDestination: function (destinationsSelected) {
    		var model = getModel();

            if(model.board.isFirstRound) { //During the first round, the player must select at least 2 destinations
                return destinationsSelected.length >= 2;
            } else { //Otherwise, the player must select at least 1 destination
                return destinationsSelected.length >= 1;
            }
    	},

        //All in game controllers must listen for the "model:SetGameInView" command. This will give the controller a new model from the selected game
    	setGameInView: function (gameId) {
            if(gameId in usersGames) {
                gameInView = gameId;
                broadcast(gameInView, 'SetGameInView');
            } else {
                alert("Invalid Game Id");
            }
    	}

        setUsername: function (username) {
            this.username = username;
        }
    };
});
