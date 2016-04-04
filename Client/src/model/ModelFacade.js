var app = angular.module('ticketToRide');

app.factory('ModelFacade', function ($state, $rootScope, Game, GameDataForLobby, ModelContainer, TrainCardColor, StaticTrackList) {
	//store and access game models
    var username = "";
	var usersGames = {};
    var joinableGames = {};
	var gameInView = 1;

	var getModel = function () {
		return usersGames[gameInView];
	};

    var checkDescriptionIsSuccess = function (description, command)
    {
        if(description.toLowerCase() == "success")
        {
            return true;
        }
        else
        {
           alert("Failed! See ModelFacade-" + command + ": description='" + description + "'");
           return false;
        }
    }

    //Lobby stuff=======================================================================================
    $rootScope.$on('server:UpdateJoinableGames', function (event, parameters)
    {
        if(checkDescriptionIsSuccess(parameters.description, 'UpdateJoinableGames'))
        {
            for(var index in parameters.games)
            {
                var game = parameters.games[index];
                joinableGames[game.gameId] = new GameDataForLobby(game);
            }    
            $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames);
        }
    });

    $rootScope.$on('server:UpdateGame', function (event, parameters)
    {
        var description = parameters.description;
        var gameId = parameters.gameId;

        if(description == "add" || description == "update")
        {
            joinableGames[gameId] = new GameDataForLobby(parameters.game);
            $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames);  
        }
        else if(description == "delete")
        {
            delete joinableGames[gameId];
            $rootScope.$broadcast('model:UpdateJoinableGames', joinableGames);  
        }
        else
        {
            alert("Unknown Server Response: UpdateGame-" + parameters.description);
        }
    });

    $rootScope.$on('server:UpdateUserGames', function (event, parameters)
    {
        if(checkDescriptionIsSuccess(parameters.description, 'UpdateUserGames'))
        {
            for(var index in parameters.games) {
                var gameId = parameters.games[index].gameID;
				if(typeof(gameId) != 'undefined')
				{                
					if(!(gameId in usersGames))
		            {
		                usersGames[gameId] = new Game(parameters.games[index].gameID);
		            }
		            usersGames[parameters.games[index].gameID].updateLobbyData(parameters.games[index], this.username);
		            broadcastIfInView(gameId, 'UpdateUserGames');
				}
				else
				{
					alert("gameId was undefined. ModelFacade.js server:UpdateUsersGames");
				}
            }            
        }
    });

	$rootScope.$on('server:StartGame', function (event, parameters)
	{
        if(checkDescriptionIsSuccess(parameters.description, 'StartGame'))
		{
			broadcastIfInView(parameters.gameId, 'StartGame');
		}
		
            //Do nothing
    });

    $rootScope.$on('server:CreateGame', function (event, parameters)
    {
        checkDescriptionIsSuccess(parameters.description, 'CreateGame')
            //Do nothing
    });

    $rootScope.$on('server:JoinGame', function (event, parameters)
    {
        checkDescriptionIsSuccess(parameters.description, 'JoinGame')
            //Do nothing
    });

    $rootScope.$on('server:SendClientModelInformation', function (event, parameters) {
        checkDescriptionIsSuccess(parameters.description, 'SendClientModelInformation')
            //Do nothing
    });

    $rootScope.$on('server:Logout', function (event, parameters)
    {
        this.username = "";
        this.usersGames = {};
        this.joinableGames = {};
        this.gameInView = -1;

        $state.go('login');
    });


    //In Game===========================================================================================
    var broadcastIfInView = function (gameId, command)
    {
		console.log("broadCastIfinView " + gameId + " " + gameInView);
        if(gameId == gameInView)
        {
            $rootScope.$broadcast('model:' + command, new ModelContainer(getModel()));
        }
    };

    $rootScope.$on('server:PrivateClientModelInformation', function (event, parameters)
    {

        usersGames[parameters.gameId].setPrivateInfo(parameters);
        broadcastIfInView(parameters.gameId, 'PrivateClientModelInformation');

    });

    $rootScope.$on('server:PublicClientModelInformation', function (event, parameters)
    {
        var game = usersGames[parameters.gameID];
        var playersFromJSON = parameters.players;
        for(var index in playersFromJSON)
        {
            var playerId = playersFromJSON[index].playerOrder;
            var playerInModel = game.getPlayerById(playerId);

            playerInModel.trainsLeft = playersFromJSON[index].trainsLeft;
            playerInModel.points = playersFromJSON[index].points;
            game.board.addRoutesPurchased(playersFromJSON[index].routes, playerInModel.playerColor);

        }
        //get game history???

        broadcastIfInView(game.gameId, 'PublicClientModelInformation');
    });

    $rootScope.$on('server:BuyRoute', function (event, parameters)
    {
        if(checkDescriptionIsSuccess(parameters.description, 'BuyRoute'))
        {
            broadcastIfInView(parameters.gameId, 'BuyRoute');
        }
    });


    $rootScope.$on('server:DrawTrainCard', function (event, parameters)
    {
		if(checkDescriptionIsSuccess(parameters.description, 'DrawTrainCard'))
		{
            var playerId = parameters.playerIndex;
            var game = usersGames[parameters.gameId];

			if(parameters.cardDrawn in game.player.trainCards)
	            game.player.trainCards[parameters.cardDrawn] += 1;
			else
				game.player.trainCards[parameters.cardDrawn] = 1;

			game.board.mustDrawAgain = parameters.canDrawAgain;
            broadcastIfInView(parameters.gameId, 'DrawTrainCard');
        }
    });

    $rootScope.$on('server:AvailableTrainCardsNotification', function (event, parameters)
    {
            var game = usersGames[parameters.gameId];
            game.board.updateCardsVisible(parameters.availableTrainCards);

            broadcastIfInView(parameters.gameId, 'DrawTrainCard');
    });

    $rootScope.$on('server:NotifyDestinationRouteCompleted', function (event, parameters)
    {
        var game = usersGames[parameters.gameId];
        var player = game.player;
        player.setDestinationComplete(parameters.route);

        broadcastIfInView(parameters.gameId, 'NotifyDestinationRouteCompleted');
    });

    $rootScope.$on('server:SelectDestinations', function (event, parameters)
    {
		if(checkDescriptionIsSuccess(parameters.description, 'SelectDestinations'))
		{
            var game = usersGames[parameters.gameId];
            var player = game.player;

            player.addDestinationCards(parameters.destinationCards);
            broadcastIfInView(parameters.gameId, 'SelectDestinations');
        }
    });

    $rootScope.$on('server:GetDestinations', function (event, parameters)
    {
        if(checkDescriptionIsSuccess(parameters.description, 'GetDestinations'))
        {
            var game = usersGames[parameters.gameId];
            var player = game.player;

            player.temporaryStorageOfCardsToBeSelectedFrom = parameters.destinationCards;
            broadcastIfInView(parameters.gameId, 'GetDestinations');
        }
    });

    $rootScope.$on('server:TurnStartedNotification', function (event, parameters)
    {
        var game = usersGames[parameters.gameId];
        var playerId = parameters.playerIndex;
        var player = game.getPlayerById(playerId);

        game.turnIndex = playerId;
        game.board.isLastRound = parameters.lastRound;

        if(game.board.isLastRound)
        {
            game.gameHistory.push(player.playerName + " begins the last round");
        }
			
		//the gameScaffoldingCtrl listens to this to show the select destinations
		broadcastIfInView(parameters.gameId, 'TurnStartedNotification');

        if(game.board.isFirstRound && playerId == game.player.playerId)
        {
            game.board.isFirstRound = false;
        }

    });

    $rootScope.$on('server:GameEnded', function (event, parameters)
    {
        var game = usersGames[parameters.gameId];
        for(var index in parameters.players)
        {
            var playerId = parameters.players[index].playerIndex;
            var points = parameters.players[index].points;

            if(parameters.players[index].longestRouteRecipient)
            {
                game.board.playerIdForTheLongestBonus = playerId;
            }
            game.getPlayerById(playerId).points = points;
        }
        game.gameOver = true;

        broadcastIfInView(parameters.gameId, 'GameEnded');
    });

    return {

        //CanDo Methods=================================================================================
    	canBuyRoute: function (routeIndex, trainColor, numberOfWilds) {
            var model = getModel();

            if(model.player.playerId != model.turnIndex) { //not your turn!
                return false;
            }

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

            if(model.player.playerId != model.turnIndex) { //not your turn!
                return false;
            }

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

            if(model.player.playerId != model.turnIndex) { //not your turn!
                return false;
            }

            //The player can draw destinations, as long as there are destinations to be drawn, and the player has not already drawn a train card
    		return model.board.deckHasDestinations && !model.board.mustDrawAgain;
    	},

    	canSelectDestination: function (destinationsSelected) {
    		var model = getModel();

            if(model.player.playerId != model.turnIndex) { //not your turn!
                return false;
            }

            if(model.board.isFirstRound) { //During the first round, the player must select at least 2 destinations
                return destinationsSelected.length >= 2;
            } else { //Otherwise, the player must select at least 1 destination
                return destinationsSelected.length >= 1;
            }
    	},

        //All in game controllers must listen for the "model:SetGameInView" command. This will give the controller a new model from the selected game
    	setGameInView: function (gameId)
    	{
            if(!(gameId in usersGames))
            {
                //alert("Failed! See ModelFacade-SetGameInView: description='Invalid GameId'");
                usersGames[gameId] = new Game(gameId);
            }

            gameInView = gameId;
            broadcastIfInView(gameInView, 'SetGameInView');
    	},

        getGameInView: function ()
        {
            return new ModelContainer(getModel());//broadcastIfInView(gameInView, 'SetGameInView');
        }
    };
});
