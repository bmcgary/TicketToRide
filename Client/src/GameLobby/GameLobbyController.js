var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope) {
    var myGames = [
      {
        id:"10",
        players:[
          "John",
          "Ben",
          "Kyle"
        ],
        yourColor:"red"
      },
      {
        id:"11",
        players:[
          "Josh",
          "Tyler",
          "Jared"
        ],
        yourColor:"pink"
      },
      {
        id:"12",
        players:
        [
          "Fred",
          "Ron",
          "Ben"
        ],
        yourColor:"Blue"
      }
  ];
  var availableGames = [
    {
      id:"10",
      players:[
        "John",
        "Ben",
        "Kyle"
      ],
      colorAvailable:[
        "blue",
        "red",
        "Orange"
      ]
    },
    {
      id:"11",
      players:[
        "Josh",
        "Tyler",
        "Jared"
      ],
      colorAvailable:[
        "blue",
        "red",
        "Orange"
      ]
    },
    {
      id:"12",
      players:
      [
        "green",
        "blue",
        "red"
      ],
      colorAvailable:[
        "yello",
        "red",
        "purple"
      ]
    }
];
    $scope.myGames = myGames;
    $scope.availableGames = availableGames;


  $scope.reJoinGame = function (id){
    console.log("you are rejoing: "+id);
  }
  $scope.joinGame = function (id){
    console.log("you are joing a new game: "+id);
  }

});
