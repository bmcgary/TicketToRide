var app = angular.module('ticketToRide');

app.controller('mainGameCanvasCtrl', function ($rootScope, $scope, ClientAPI, StaticTrackList, PlayerColor, $uibModal, ModelFacade, TrainCardColor, TrackColor) {


    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');

	setUpCanvas();
		
	//Set the canvas size to the size of the view it is contained in.
	function setUpCanvas()
	{
		var myEl = document.getElementById("canvasHolder");
    	canvas.width = myEl.offsetWidth; canvas.height = myEl.offsetHeight;
	}


    //This is for changing canvas size on window change.
    /*
    var addEvent = function(object, type, callback) {
        if (object == null || typeof(object) == 'undefined') return;
        if (object.addEventListener) {
            object.addEventListener(type, callback, false);
        } else if (object.attachEvent) {
            object.attachEvent("on" + type, callback);
        } else {
            object["on"+type] = callback;
        }
    };
    */
	//addEvent(window, 'resize', setUpCanvas);

    trackTransforms(context);
	
    var trainImage   = new Image();
    trainImage.src   = '/images/pieces/ttr-piece-black-sq.jpg';
    var trainImageWidth = 28;
    var trainImageHeight = 8;

    var mapImage = new Image();
    mapImage.src = '/images/tickettoridemap_crop.jpeg';
    mapImage.onload = function ()
    {
        initializeTrains();
        redraw();

    }
   
//GAME AND PLAYER VARIABLES////////////////////////////////////////////////////////////////////////////////// 
    var gameID = 0;
    var currentGameModel;
    var gameStarted = true;
    var playerHand = {
         wild:0,
         red:0,
         green:0,
         blue:0,
         yellow:0,
         black:0,
         white:0,
         purple:0,
         orange:0
    };
    var playerColorWord;

    var routesPurchased;

    function updateGameInformation(modelContainer)
    {
        currentGameModel = modelContainer
        gameID = modelContainer.getGameId();
        updatePlayerHand(modelContainer);
        setTrainImage(modelContainer);
        routesPurchased = modelContainer.getRoutesOwned();
        redraw();

    }

    function updatePlayerHand(modelContainer)
       {
            playerHand.wild = modelContainer.getTrainCardsByColor(TrainCardColor.WILD);
            playerHand.black = modelContainer.getTrainCardsByColor(TrainCardColor.BLACK);
            playerHand.white = modelContainer.getTrainCardsByColor(TrainCardColor.WHITE);
            playerHand.red = modelContainer.getTrainCardsByColor(TrainCardColor.RED);
            playerHand.yellow = modelContainer.getTrainCardsByColor(TrainCardColor.YELLOW);
            playerHand.green = modelContainer.getTrainCardsByColor(TrainCardColor.GREEN);
            playerHand.blue = modelContainer.getTrainCardsByColor(TrainCardColor.BLUE);
            playerHand.purple = modelContainer.getTrainCardsByColor(TrainCardColor.PURPLE);
            playerHand.orange = modelContainer.getTrainCardsByColor(TrainCardColor.ORANGE);
            
           
       }

    function setTrainImage(modelContainer)
    {
        switch(modelContainer.getPlayerColor())
           {
               case PlayerColor.BLACK:
                   trainImage.src   = '/images/pieces/ttr-piece-black-sq.jpg';
                   playerColorWord = "black";
                   break;
               case PlayerColor.BLUE:
                   trainImage.src   = '/images/pieces/ttr-piece-blue-sq.jpg';
                   playerColorWord = "blue";
                   break;
               case PlayerColor.GREEN:
                   trainImage.src   = '/images/pieces/ttr-piece-green-sq.jpg';
                   playerColorWord = "green";
                   break;
               case PlayerColor.RED:
                   trainImage.src   = '/images/pieces/ttr-piece-red-sq.jpg';
                   playerColorWord = "red";
                   break;
               case PlayerColor.YELLOW:
                   trainImage.src   = '/images/pieces/ttr-piece-yellow-sq.jpg';
                   playerColorWord = "yellow";
                   break;
               default:
                   trainImage.src   = '/images/pieces/ttr-piece-blue-sq.jpg';
                   playerColorWord = "blue";
           }
    }

    function setOtherTrainImage(color)
    {
        switch(color)
           {
               case PlayerColor.BLACK:
                   trainImage.src   = '/images/pieces/ttr-piece-black-sq.jpg';
                   playerColorWord = "black";
                   break;
               case PlayerColor.BLUE:
                   trainImage.src   = '/images/pieces/ttr-piece-blue-sq.jpg';
                   playerColorWord = "blue";
                   break;
               case PlayerColor.GREEN:
                   trainImage.src   = '/images/pieces/ttr-piece-green-sq.jpg';
                   playerColorWord = "green";
                   break;
               case PlayerColor.RED:
                   trainImage.src   = '/images/pieces/ttr-piece-red-sq.jpg';
                   playerColorWord = "red";
                   break;
               case PlayerColor.YELLOW:
                   trainImage.src   = '/images/pieces/ttr-piece-yellow-sq.jpg';
                   playerColorWord = "yellow";
                   break;
               default:
                   trainImage.src   = '/images/pieces/ttr-piece-blue-sq.jpg';
                   playerColorWord = "blue";
           }
    }


                    
                    
                    
//MODEL LISTENERS/////////////////////////////////////////////////////////////////////////////////////////////

    $rootScope.$on('model:StartGame', function (event, modelContainer)
    {
        gameStarted = true;
    });

    $rootScope.$on('model:SetGameInView', function (event, modelContainer)
    {
        currentGameModel = modelContainer;
        gameID = modelContainer.getGameId();
    });
        
    $rootScope.$on('model:PrivateClientModelInformation', function (event, modelContainer)
    {
       updateGameInformation(modelContainer);
    });

    $rootScope.$on('model:PublicClientModelInformation', function (event, modelContainer)
    {
        updateGameInformation(modelContainer);
    });

    $rootScope.$on('model:SelectDestinations', function (event, modelContainer)
    {
        updateGameInformation(modelContainer);
    });

    $rootScope.$on('model:TurnStartedNotification', function (event, modelContainer)
    {
        updateGameInformation(modelContainer);
    });
    $rootScope.$on('model:AvailableTrainCardsNotification', function (event, modelContainer)
    {
        updateGameInformation(modelContainer);
    });
    $rootScope.$on('model:DrawTrainCard', function (event, modelContainer)
    {
        updateGameInformation(modelContainer);
    });
    $rootScope.$on('model:BuyRoute', function (event, modelContainer)
    {
        updateGameInformation(modelContainer);
    });



//BOARD METHODS////////////////////////////////////////


    function redraw(){
         //Clear the entire canvas
         var p1 = context.transformedPoint(0,0);
         var p2 = context.transformedPoint(canvas.width,canvas.height);
        context.clearRect(p1.x,p1.y,p2.x-p1.x,p2.y-p1.y);
        //Draw the map
        context.drawImage(mapImage,0,0,890,460);
        if(gameStarted)
        {
             //Draw existing routes
            drawExistingRoutes();
        }

    }


    redraw();

    function initializeTrains()
    {
        for(var routeId in StaticTrackList)
        {
            var route = StaticTrackList[routeId].tracks;
            for(var train in route )
            {
                context.save();

                context.translate(route[train].topLeft.x, route[train].topLeft.y);


                //Calculate Rotation Angle and Assign to the train object
                var deltaX = route[train].topLeft.x - route[train].topRight.x;
                var deltaY = route[train].topLeft.y - route[train].topRight.y;
                var radians = Math.atan2(deltaY, deltaX);
                route[train].angle = (radians + Math.PI);

                context.restore();
                context.save();

                context.rotate(-(radians + Math.PI));
                context.translate(-route[train].topLeft.x, -route[train].topLeft.y);


                //Calculate the bottom points and assign them to each train
                var bottomLeft = context.transformedPoint(0, trainImageHeight);
                var bottomRight = context.transformedPoint(trainImageWidth,trainImageHeight);
                route[train].bottomLeft = {"x":bottomLeft.x, "y":bottomLeft.y};
                route[train].bottomRight = {"x":bottomRight.x, "y":bottomRight.y};

                //Create Bounding Box
                var currentTopLeft = route[train].topLeft;
                var currentTopRight = route[train].topRight;

                route[train].maxX = Math.max(currentTopLeft.x, currentTopRight.x, bottomLeft.x, bottomRight.x);
                route[train].maxY = Math.max(currentTopLeft.y, currentTopRight.y, bottomLeft.y, bottomRight.y);
                route[train].minX = Math.min(currentTopLeft.x, currentTopRight.x, bottomLeft.x, bottomRight.x);
                route[train].minY = Math.min(currentTopLeft.y, currentTopRight.y, bottomLeft.y, bottomRight.y);

                context.restore();

            }
        }

    };

    function drawExistingRoutes()
    {
        for(var routesPurchasedIterator in routesPurchased)
        {
            var boughtRouteIndex = routesPurchased[routesPurchasedIterator];
            var currentColor = currentGameModel.getPlayerColorByRouteId(boughtRouteIndex);
            setOtherTrainImage(currentColor);
            drawRoute(boughtRouteIndex);


        }


    }

    function drawRoute(routeId){
        var route = StaticTrackList[routeId].tracks;
        for(var train in route )
        {
            drawTrain(route[train].topLeft, route[train].topRight, route[train].angle);
        }

    };


    function drawTrain(topLeft, topRight, angle){
        context.save();

        context.translate(topLeft.x, topLeft.y);

        context.rotate(angle);

        context.drawImage(trainImage, 0,0, trainImageWidth, trainImageHeight);

        context.restore();
    };





    function checkIfMouseInTrain(xPosition, yPosition)
    {

        for(var routeId in StaticTrackList)
        {
            var route = StaticTrackList[routeId].tracks;

            for(var train in route )
            {
                var currentTrain = route[train];

                //Check outer Bounding Box
               if(xPosition >= currentTrain.minX && xPosition <= currentTrain.maxX)
                {
                    if(yPosition >= currentTrain.minY && yPosition <= currentTrain.maxY)
                    {

                        //More Detailed Check
                        if(is_in_triangle (xPosition, yPosition, currentTrain.topLeft.x, currentTrain.topLeft.y,currentTrain.bottomRight.x,currentTrain.bottomRight.y,currentTrain.topRight.x, currentTrain.topRight.y))
                        {
                            routeToHighlight = routeId;
                            return true;
                        }
                        else if(is_in_triangle (xPosition, yPosition, currentTrain.topLeft.x, currentTrain.topLeft.y,currentTrain.bottomRight.x,currentTrain.bottomRight.y, currentTrain.bottomLeft.x, currentTrain.bottomLeft.y))
                        {
                            routeToHighlight = routeId;
                            return true;
                        }
                        else
                        {
                            routeToHighlight = -1;
                            continue;
                        }

                    }
                }


            }
        }

    };

    function highlightRoute(routeId)
    {

        var route = StaticTrackList[routeId].tracks;
        for(var train in route )
        {

            context.save();

            context.translate(route[train].topLeft.x, route[train].topLeft.y);

            context.rotate(route[train].angle);

            context.drawImage(trainImage, 0,0, trainImageWidth, trainImageHeight);

            context.restore();

        }

    };

    function is_in_triangle (xPosition,yPosition,ax,ay,bx,by,cx,cy){

        //credit: http://www.blackpawn.com/texts/pointinpoly/default.html
        var px =xPosition;
        var py = yPosition;

        var v0 = [cx-ax,cy-ay];
        var v1 = [bx-ax,by-ay];
        var v2 = [px-ax,py-ay];

        var dot00 = (v0[0]*v0[0]) + (v0[1]*v0[1]);
        var dot01 = (v0[0]*v1[0]) + (v0[1]*v1[1]);
        var dot02 = (v0[0]*v2[0]) + (v0[1]*v2[1]);
        var dot11 = (v1[0]*v1[0]) + (v1[1]*v1[1]);
        var dot12 = (v1[0]*v2[0]) + (v1[1]*v2[1]);

        var invDenom = 1/ (dot00 * dot11 - dot01 * dot01);

        var u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        var v = (dot00 * dot12 - dot01 * dot02) * invDenom;

        return ((u >= 0) && (v >= 0) && (u + v < 1));
    };



    var lastX=canvas.width/2, lastY=canvas.height/2;
    var dragStart,dragged;



    var mouseClickPosition = 0;
    canvas.addEventListener('mousedown',function(evt){
        document.body.style.mozUserSelect = document.body.style.webkitUserSelect = document.body.style.userSelect = 'none';
        lastX = evt.offsetX || (evt.pageX - canvas.offsetLeft);
        lastY = evt.offsetY || (evt.pageY - canvas.offsetTop);
        mouseClickPosition = context.transformedPoint(lastX,lastY);
        dragStart = context.transformedPoint(lastX,lastY);
        dragged = false;
    },false);


    //Displaying the mouse location
    function writeMessage(canvas, message) {
    var context = canvas.getContext('2d');
    context.clearRect(0, 0, 250, 23);
    context.font = '18pt Calibri';
    context.fillStyle = 'black';
    context.fillText(message, 0, 20);
    };

    var printX = -1;
    var printY = -1;
    var routeToHighlight = -1;
    canvas.addEventListener('mousemove',function(evt){
        lastX = evt.offsetX || (evt.pageX - canvas.offsetLeft);
        lastY = evt.offsetY || (evt.pageY - canvas.offsetTop);
        var mouseLocation = context.transformedPoint(lastX, lastY);
        printX = mouseLocation.x.toFixed(0);
        printY = mouseLocation.y.toFixed(0);

         if(gameStarted)
         {
             if(checkIfMouseInTrain(mouseLocation.x, mouseLocation.y) == true)
             {
                if(routeToHighlight != -1)
                {
                    highlightRoute(routeToHighlight);
                }
             }
            else
            {
             redraw();
            }
         }
         else
         {
            redraw();
         }

        dragged = true;
        if (dragStart){
            var pt = context.transformedPoint(lastX,lastY);
            context.translate(pt.x-dragStart.x,pt.y-dragStart.y);
            redraw();
        }
    },false);


    canvas.addEventListener('mouseup',function(evt){

        lastX = evt.offsetX || (evt.pageX - canvas.offsetLeft);
        lastY = evt.offsetY || (evt.pageY - canvas.offsetTop);
        var mouseLocation = context.transformedPoint(lastX, lastY);

        dragStart = null;
        if(gameStarted)
        {
            if(checkIfMouseInTrain(mouseLocation.x, mouseLocation.y) == true)
            {
                if(routeToHighlight != -1)
                {
                    highlightRoute(routeToHighlight);
                    if(canBuyRoute())
                    {
                        openBuyRouteModal(routeToHighlight);
                    }
                }
            }
        }


        if (!dragged) checkIfMouseInTrain(mouseClickPosition.x,mouseClickPosition.y);
    },false);





    var firstLine = true;
    document.addEventListener('keydown', function(evt) {
        var code = evt.keyCode;
        //alert(event.keyCode);

        if(code == 81)
        {
            if(firstLine == true)
            {
                document.getElementById("resultBox").innerHTML += '{"topLeft":{"x":' + printX + ', "y":' + printY +'},';
                firstLine = false;
            }
            else
            {
                document.getElementById("resultBox").innerHTML += '"topRight":{"x":' + printX + ', "y":' + printY +'}},'+'\n';
                firstLine = true;
            }
        }

    }, false);




    var scaleFactor = 1.1;
    /*var zoom = function(clicks){
        var pt = context.transformedPoint(lastX,lastY);
        context.translate(pt.x,pt.y);
        var factor = Math.pow(scaleFactor,clicks);
        context.scale(factor,factor);
        context.translate(-pt.x,-pt.y);

        redraw();
    }*/

    var handleScroll = function(evt){
        var delta = evt.wheelDelta ? evt.wheelDelta/40 : evt.detail ? -evt.detail : 0;
        if (delta)
        {
            var pt = context.transformedPoint(lastX,lastY);
            context.translate(pt.x,pt.y);
            var factor = Math.pow(scaleFactor,delta);
            context.scale(factor,factor);
            context.translate(-pt.x,-pt.y);

            redraw();

        }
        return evt.preventDefault() && false;
    };
    canvas.addEventListener('DOMMouseScroll',handleScroll,false);
    canvas.addEventListener('mousewheel',handleScroll,false);



// Adds context.getTransform() - returns an SVGMatrix
// Adds context.transformedPoint(x,y) - returns an SVGPoint
function trackTransforms(context){
    var svg = document.createElementNS("http://www.w3.org/2000/svg",'svg');
    var xform = svg.createSVGMatrix();

    context.getTransform = function(){ return xform; };

    var savedTransforms = [];
    var save = context.save;

    context.save = function(){
        savedTransforms.push(xform.translate(0,0));
        return save.call(context);
    };

    var restore = context.restore;

    context.restore = function(){
        xform = savedTransforms.pop();
        return restore.call(context);
    };

    var scale = context.scale;

    context.scale = function(sx,sy){
        xform = xform.scaleNonUniform(sx,sy);
        return scale.call(context,sx,sy);
    };

    var rotate = context.rotate;

    context.rotate = function(radians){
        xform = xform.rotate(radians*180/Math.PI);
        return rotate.call(context,radians);
    };

    var translate = context.translate;

    context.translate = function(dx,dy){
        xform = xform.translate(dx,dy);
        return translate.call(context,dx,dy);
    };

    var transform = context.transform;

    context.transform = function(a,b,c,d,e,f){
        var m2 = svg.createSVGMatrix();
        m2.a=a; m2.b=b; m2.c=c; m2.d=d; m2.e=e; m2.f=f;
        xform = xform.multiply(m2);
        return transform.call(context,a,b,c,d,e,f);
    };

    var setTransform = context.setTransform;

    context.setTransform = function(a,b,c,d,e,f){
        xform.a = a;
        xform.b = b;
        xform.c = c;
        xform.d = d;
        xform.e = e;
        xform.f = f;
        return setTransform.call(context,a,b,c,d,e,f);
    };

    var pt  = svg.createSVGPoint();

    context.transformedPoint = function(x,y){
        pt.x=x; pt.y=y;
        return pt.matrixTransform(xform.inverse());
    };


    };





//---------------------------Buy Route modal -------------------------------------------------------


    var modalColors = [];
    function defineCardSelectionColors(routeColor)
          {
               modalColors = [];
               if(routeColor == TrackColor.GRAY)
               {
                  for(var cardColor in playerHand)
                  {
                     if(playerHand[cardColor] > 0)
                     {

                        var colorInfo = {};
                        colorInfo[cardColor] = playerHand[cardColor];
                        modalColors.push(colorInfo);


                     }
                  }

               }
               else
               {
                     if(playerHand[routeColor] > 0)
                     {
                         var cardColor = TrainCardColor.get(routeColor);
                         var colorInfo = {};
                         colorInfo[cardColor] =  playerHand[cardColor];;
                         modalColors.push(colorInfo);

                     }

               }


          }



//Buy Route Functionality//////////////////////////////////////////////////////////////////////////////////////////////

    function canBuyRoute()
    {
    //from current model- if player must draw again- can't buy route
        if(!$scope.selectDestOnRight)
        {
            if(!$scope.secondTrainCardRound)
            {

                return true;

            }

        }
        return false;
    }




	function openBuyRouteModal(routeIndex)
	{

        var routeInfo = StaticTrackList[routeIndex];
        defineCardSelectionColors(routeInfo.trackColor);

		var modalInstance = $uibModal.open({
			  animation: true,
			  templateUrl: 'buyRoute.html',
			  controller: 'buyRouteCtrl',



			  resolve:
			  {
			        routeColor: function()
			        {
			            return routeInfo.trackColor;
			        },

			        modalColors: function()
			        {
			            return modalColors;
			        },
			        
			        routeCost: function()
			        {
			            return routeInfo.trainsRequired;
			        },

			        playerHand: function()
			        {
			            return playerHand;
			        }
			  }
			});

            modalInstance.result.then(

                function (buyRouteInfo) {

                  var colorToSend = buyRouteInfo["color"];
                  var wildsToSend = buyRouteInfo["wilds"];

                  ClientAPI.buyRoute(gameID, routeIndex, colorToSend, wildsToSend);
                },
                function () {

                });

    }

});

// Destination modal's controller ------------------------------------------------------------------------
app.controller('buyRouteCtrl', function ($scope, $uibModalInstance, routeColor, routeCost, playerHand, modalColors) {

     $scope.alert = {showAlert: false, message: "", type:""};

      $scope.colors = [];
      for(var colorIndex in modalColors)
      {
        for(var color in modalColors[colorIndex])

        $scope.colors.push(color);
      }

      


      $scope.normalTrainNumbers = [];
      $scope.wildNumbers = [];
      $scope.trainCardPath = "";
      $scope.trainCost = "0";
      $scope.wildCost = "0";
      $scope.trainColor = "Select A Color";


        for(var modalColor in modalColors[0])
        {
            if(modalColor == 'wild')
            {
                for(var counter = 0; counter <= modalColors[colorIndex][color]; counter ++ )
                 {
                    $scope.wildNumbers.push(counter);
                 }
            }
        }


     $scope.ok = function () {

        if($scope.trainColor != "Select A Color")
        {
            if(($scope.trainCost + $scope.wildCost) >= routeCost)
               {
                    var lowerTrainColor = $scope.trainColor.toLowerCase();
                    var wildCardsUsed = $scope.wildCost;
                    var buyRouteInfo = {"color":lowerTrainColor, "wilds":wildCardsUsed};
                   $uibModalInstance.close(buyRouteInfo);
               }
            else
            {
                alert("Please select more trains.");
            }
        }

      };


      $scope.selectColor = function(color)
      {
        $scope.normalTrainNumbers = [];
        for(var colorIndex in modalColors)
        {
            for(var modalColor in modalColors[colorIndex])
            {
                if(modalColor == color)
                {
                    for(var counter = 0; counter <= modalColors[colorIndex][color]; counter ++ )
                     {
                        $scope.normalTrainNumbers.push(counter);
                     }
                }
            }

        }
        switch(color)
        {
            case "red":
            $scope.trainCardPath = "/images/trainCards/ttr-train-red.jpg";
            $scope.trainColor = "Red";
            break;
            case "blue":
            $scope.trainCardPath = "/images/trainCards/ttr-train-blue.jpg";
            $scope.trainColor = "Blue";
            break;
            case "black":
            $scope.trainCardPath = "/images/trainCards/ttr-train-black.jpg";
            $scope.trainColor = "Black";
            break;
            case "green":
            $scope.trainCardPath = "/images/trainCards/ttr-train-green.jpg";
            $scope.trainColor = "Green";
            break;
            case "orange":
            $scope.trainCardPath = "/images/trainCards/ttr-train-orange.jpg";
            $scope.trainColor = "Orange";
            break;
            case "purple":
            $scope.trainCardPath = "/images/trainCards/ttr-train-purple.jpg";
            $scope.trainColor = "Purple";
            break;
            case "yellow":
            $scope.trainCardPath = "/images/trainCards/ttr-train-yellow.jpg";
            $scope.trainColor = "Yellow";
            break;
            case "white":
            $scope.trainCardPath = "/images/trainCards/ttr-train-white.jpg";
            $scope.trainColor = "White";
            break;
            default:
            $scope.trainCardPath = "/images/trainCards/ttr-train-white.jpg";
            $scope.trainColor = "Select Train Color";
            break;


        }


      }

      $scope.selectTrainCost = function(cost)
      {
        $scope.trainCost = cost;
      }

      $scope.selectWildCost = function(cost)
      {
        $scope.wildCost = cost;
      }



      $scope.cancel = function(){
        $uibModalInstance.dismiss('cancel');
      }


        function showAlert(message, type)
        {
            $scope.alert.showAlert = true;
            $scope.alert.message = message;
            $scope.alert.type = type;
        }




});

