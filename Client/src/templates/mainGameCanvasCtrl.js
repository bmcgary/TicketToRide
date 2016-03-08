var app = angular.module('ticketToRide');

app.controller('mainGameCanvasCtrl', function ($scope, ClientAPI) {

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
	

    var mapImage = new Image();
    mapImage.onload = function ()
    {
        initializeTrains();
        redraw();

    }
    mapImage.src = '/images/tickettoridemap_crop.jpeg';

    var trainImage   = new Image();
    trainImage.src   = '/images/pieces/ttr-piece-blue-sq.jpg';
    var trainImageWidth = 30;
    var trainImageHeight = 10;

    function redraw(){
         //Clear the entire canvas
         var p1 = context.transformedPoint(0,0);
         var p2 = context.transformedPoint(canvas.width,canvas.height);
        context.clearRect(p1.x,p1.y,p2.x-p1.x,p2.y-p1.y);

        context.drawImage(mapImage,0,0);
        drawTrains();
    };
    redraw();
  var trains = {0:[{"topLeft":{"x":216, "y":57}, "topRight":{"x":237, "y":81}}],
                      1:[{"topLeft":{"x":239, "y":84}, "topRight":{"x":259, "y":109}}],
                      2:[{"topLeft":{"x":33, "y":210}, "topRight":{"x":45, "y":183}}],

                      3:[
                         {"topLeft":{"x":61, "y":169}, "topRight":{"x":91, "y":175}},
                         {"topLeft":{"x":97, "y":177}, "topRight":{"x":126, "y":187}},
                         {"topLeft":{"x":131, "y":190}, "topRight":{"x":157, "y":207}},
                         {"topLeft":{"x":163, "y":211}, "topRight":{"x":186, "y":230}},
                         {"topLeft":{"x":191, "y":233}, "topRight":{"x":212, "y":257}},
                         {"topLeft":{"x":215, "y":261}, "topRight":{"x":230, "y":289}}
                         ],
                      4:[{"topLeft":{"x":41, "y":184}, "topRight":{"x":29, "y":212}}],
                      5:[
                         {"topLeft":{"x":697, "y":347},"topRight":{"x":710, "y":320}},
                         {"topLeft":{"x":714, "y":315},"topRight":{"x":734, "y":290}},
                         {"topLeft":{"x":739, "y":287},"topRight":{"x":764, "y":268}},
                         {"topLeft":{"x":769, "y":266},"topRight":{"x":786, "y":242}}
                         ],
                       6:[
                        {"topLeft":{"x":317, "y":177},"topRight":{"x":348, "y":176}},
                        {"topLeft":{"x":354, "y":175},"topRight":{"x":384, "y":176}},
                        {"topLeft":{"x":389, "y":176},"topRight":{"x":421, "y":176}},
                        {"topLeft":{"x":426, "y":176},"topRight":{"x":455, "y":174}},
                        {"topLeft":{"x":462, "y":174},"topRight":{"x":492, "y":174}},
                        {"topLeft":{"x":496, "y":174},"topRight":{"x":526, "y":173}}
                       ],
                       7:[
                            {"topLeft":{"x":314, "y":164},"topRight":{"x":335, "y":142}},
                            {"topLeft":{"x":338, "y":138},"topRight":{"x":360, "y":117}},
                            {"topLeft":{"x":364, "y":112},"topRight":{"x":385, "y":91}},
                            {"topLeft":{"x":389, "y":87},"topRight":{"x":411, "y":66}},

                       ],
                       8:[
                       {"topLeft":{"x":381, "y":331},"topRight":{"x":410, "y":333}},
                        {"topLeft":{"x":417, "y":333},"topRight":{"x":446, "y":331}},
                        {"topLeft":{"x":452, "y":330},"topRight":{"x":482, "y":324}},
                        {"topLeft":{"x":486, "y":322},"topRight":{"x":514, "y":310}}

                       ]
                      };


        function initializeTrains()
        {
            for(var routeId in trains)
            {
                var route = trains[routeId];
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

                    context.restore();

                    //Create Bounding Box
                    var currentTopLeft = route[train].topLeft;
                    var currentTopRight = route[train].topRight;

                    route[train].maxX = Math.max(currentTopLeft.x, currentTopRight.x, bottomLeft.x, bottomRight.x);
                    route[train].maxY = Math.max(currentTopLeft.y, currentTopRight.y, bottomLeft.y, bottomRight.y);
                    route[train].minX = Math.min(currentTopLeft.x, currentTopRight.x, bottomLeft.x, bottomRight.x);
                    route[train].minY = Math.min(currentTopLeft.y, currentTopRight.y, bottomLeft.y, bottomRight.y);


                }
            }



        };





        function drawTrains(){
            for(var routeId in trains)
            {
                var route = trains[routeId];
                for(var train in route )
                {
                    drawTrain(route[train].topLeft, route[train].topRight, route[train].angle);
                }
            }

        };


        function drawTrain(topLeft, topRight, angle){
            context.save();

            context.translate(topLeft.x, topLeft.y);
            // var deltaX = topLeft.x - topRight.x;
            // var deltaY = topLeft.y - topRight.y;
            // var radians = Math.atan2(deltaY, deltaX);
            context.rotate(angle);

            context.drawImage(trainImage, 0,0, trainImageWidth, trainImageHeight);

            context.restore();
        };


        function checkIfMouseInTrain(xPosition, yPosition)
        {

            for(var routeId in trains)
            {
                var route = trains[routeId];

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
                                return false;
                            }

                        }
                    }


                }
            }

        };

        function highlightRoute(routeId)
        {

            var route = trains[routeId];
            for(var train in route )
            {

                context.save();

                context.translate(route[train].topLeft.x, route[train].topLeft.y);

                context.rotate(route[train].angle);

                context.drawImage(trainImage, 0,0, trainImageWidth * 1.2, trainImageHeight * 1.8);

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

            var message = 'Mouse position: ' + mouseLocation.x.toFixed(0) + "," + mouseLocation.y.toFixed();
             writeMessage(canvas, message);
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

            dragged = true;
            if (dragStart){
                var pt = context.transformedPoint(lastX,lastY);
                context.translate(pt.x-dragStart.x,pt.y-dragStart.y);
                redraw();
            }
        },false);

        canvas.addEventListener('mouseup',function(evt){
            dragStart = null;
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
});
