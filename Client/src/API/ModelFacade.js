var app = angular.module('ticketToRide');

//The methods in this class are for modifying the model and notifying the appropriate controllers
app.factory('ModelFacade', function () {

    return {
        login: function (parameters) {
            alert("Login: " + parameters.description);
        },

        register: function (parameters) {
            alert("Register: " + parameters.description);
        },

        forgotUserNameOrPassword: function (parameters) {
            alert("ForgotUserNameOrPassword: " + parameters.description);
        },

        logout: function (parameters) {
            alert("Logout: " + parameters.description);
        },

        sendChat: function (parameters) {
            alert("SendChat: " + parameters.description);
        },

        getLeaderboards: function (parameters) {
            alert("GetLeaderboards: " + parameters.description);
        },

        updateJoinableGames: function (parameters) {
            alert("UpdateJoinableGames: " + parameters.description);
        },

        updateUserGames: function (parameters) {
            alert("UpdateUserGames: " + parameters.description);
        },

        joinGame: function (parameters) {
            alert("JoinGame: " + parameters.description);
        },

        createGame: function (parameters) {
            alert("CreateGame: " + parameters.description);
        },

        leaveGame: function (parameters) {
            alert("LeaveGame: " + parameters.description);
        },

        startGame: function (parameters) {
            alert("StartGame: " + parameters.description);
        },

        addAI: function (parameters) {
            alert("AddAI: " + parameters.description);
        },

        sendClientModelInformation: function (parameters) {
            alert("SendClientModelInformation: " + parameters.description);
        },

        buyRoute: function (parameters) {
            alert("BuyRoute: " + parameters.description);
        },

        drawTrainCard: function (parameters) {
            alert("DrawTrainCard: " + parameters.description);
        },

        notifyDestinationRouteCompleted: function (parameters) {
            alert("NotifyDestinationRouteCompleted: " + parameters.description);
        },

        getDestinations: function (parameters) {
            alert("GetDestinations: " + parameters.description);
        },

        selectDestinations: function (parameters) {
            alert("SelectDestinations: " + parameters.description);
        },

        gameEnded: function (parameters) {
            alert("GameEnded: " + parameters.description);
        }
    };
});