var app = angular.module('ticketToRide');

//The API Methods sent from the Server to the Client
app.factory('ServerAPI', function ($rootScope) {
    return {

        command: function (response) {
            
            //Keep switch for testing
            switch(response.command)
            {
                case "Login":
					if(response.parameters)
					{
						$rootScope.loggedIn = true;
					}
                case "Register": 
					if(response.parameters)
					{
						$rootScope.loggedIn = true;
					}
                case "ForgotUserNameOrPassword": 
                case "Logout": 
                case "UpdateJoinableGames": 
                case "UpdateUserGames": 
                case "GetLeaderboards": 
                case "UpdateGame":
                case "JoinGame": 
                case "CreateGame": 
                case "LeaveGame": 
                case "StartGame": 
                case "AddAI": 
                case "SendClientModelInformation": 
                case "PublicClientModelInformation":
                case "PrivateClientModelInformation":
                case "SendChat": 
                case "BuyRoute": 
                case "DrawTrainCard": 
                case "NotifyDestinationRouteCompleted": 
                case "GetDestinations": 
                case "SelectDestinations": 
                case "GameEnded": 
				case "UpdateGame":

                    $rootScope.$broadcast('server:'+response.command, response.parameters);
					console.log("A good command has come back! The server sent back something the client understoond! YIPPY!");
                    break;

                default: alert("Bad Server command: " + response.command);
            }
        }
    };
});
