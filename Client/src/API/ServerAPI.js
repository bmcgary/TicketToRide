var app = angular.module('ticketToRide');

//The API Methods sent from the Server to the Client
app.factory('ServerAPI', function ($rootScope) {
    return {

        command: function (response) {
            
            //Keep switch for testing
            switch(response.command)
            {
                case "Login":
                case "Register": 
                case "ForgotUserNameOrPassword": 
                case "Logout": 
                case "SendChat": 
                case "GetLeaderboards": 
                case "UpdateJoinableGames": 
                case "UpdateUserGames": 
                case "JoinGame": 
                case "CreateGame": 
                case "LeaveGame": 
                case "StartGame": 
                case "AddAI": 
                case "SendClientModelInformation": 
                case "BuyRoute": 
                case "DrawTrainCard": 
                case "NotifyDestinationRouteCompleted": 
                case "GetDestinations": 
                case "SelectDestinations": 
                case "GameEnded": 

                    $rootScope.$broadcast('server:'+response.command, response.parameters);
					alert("A good command has come back! The server sent back something the client understoond! YIPPY!");
                    break;

                default: alert("Bad Server command");
            }
        }
    };
});
