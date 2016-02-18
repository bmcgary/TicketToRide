var app = angular.module('ticketToRide');

//The API Methods sent from the Server to the Client
app.factory('ServerAPI', function (ModelFacade) {

    return {
        command: function (response) {
        	switch(response.command)
        	{
        		case "Login": 
                    ModelFacade.login(response.parameters); 
                    break;

        		case "Register": 
                    ModelFacade.register(response.parameters); 
                    break;

                case "ForgotUserNameOrPassword": 
                    ModelFacade.forgotUserNameOrPassword(response.parameters); 
                    break;

                case "Logout": 
                    ModelFacade.logout(response.parameters); 
                    break;

                case "SendChat": 
                    ModelFacade.sendChat(response.parameters); 
                    break;

                case "GetLeaderboards": 
                    ModelFacade.getLeaderboards(response.parameters); 
                    break;

                case "UpdateJoinableGames": 
                    ModelFacade.updateJoinableGames(response.parameters); 
                    break;

                case "UpdateUserGames": 
                    ModelFacade.updateUserGames(response.parameters); 
                    break;

                case "JoinGame": 
                    ModelFacade.joinGame(response.parameters); 
                    break;

                case "CreateGame": 
                    ModelFacade.createGame(response.parameters); 
                    break;

                case "LeaveGame": 
                    ModelFacade.leaveGame(response.parameters); 
                    break;

                case "StartGame": 
                    ModelFacade.startGame(response.parameters); 
                    break;

                case "AddAI": 
                    ModelFacade.addAI(response.parameters); 
                    break;

                case "SendClientModelInformation": 
                    ModelFacade.sendClientModelInformation(response.parameters); 
                    break;

                case "BuyRoute": 
                    ModelFacade.buyRoute(response.parameters); 
                    break;

                case "DrawTrainCard": 
                    ModelFacade.drawTrainCard(response.parameters); 
                    break;

                case "NotifyDestinationRouteCompleted": 
                    ModelFacade.notifyDestinationRouteCompleted(response.parameters); 
                    break;

                case "GetDestinations": 
                    ModelFacade.getDestinations(response.parameters); 
                    break;

                case "SelectDestinations": 
                    ModelFacade.selectDestinations(response.parameters); 
                    break;

                case "GameEnded": 
                    ModelFacade.gameEnded(response.parameters); 
                    break;

        		default: alert("Bad command");
        	}
    	}
    };
});