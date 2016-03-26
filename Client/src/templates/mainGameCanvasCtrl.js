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
    var trainImageWidth = 29;
    var trainImageHeight = 10;

    function redraw(){
         //Clear the entire canvas
         var p1 = context.transformedPoint(0,0);
         var p2 = context.transformedPoint(canvas.width,canvas.height);
        context.clearRect(p1.x,p1.y,p2.x-p1.x,p2.y-p1.y);

        context.drawImage(mapImage,0,0,890,460);
        drawTrains();
    };
    redraw();
var trains = {

                        1:[{"topLeft":{"x":64, "y":59},"topRight":{"x":65, "y":84}}
                        ],
                        2:[{"topLeft":{"x":59, "y":100},"topRight":{"x":48, "y":122}}
                        ],
                        3:[{"topLeft":{"x":40, "y":141},"topRight":{"x":27, "y":164}},
                            {"topLeft":{"x":26, "y":165},"topRight":{"x":20, "y":189}},
                            {"topLeft":{"x":20, "y":192},"topRight":{"x":19, "y":217}},
                            {"topLeft":{"x":19, "y":220},"topRight":{"x":20, "y":245}},
                            {"topLeft":{"x":21, "y":246},"topRight":{"x":30, "y":270}}
                        ],
                        4:[{"topLeft":{"x":36, "y":292},"topRight":{"x":48, "y":314}},
                            {"topLeft":{"x":49, "y":317},"topRight":{"x":67, "y":337}},
                            {"topLeft":{"x":68, "y":339},"topRight":{"x":89, "y":357}}
                        ],
                        5:[{"topLeft":{"x":76, "y":59},"topRight":{"x":76, "y":84}}
                        ],
                        6:[{"topLeft":{"x":70, "y":103},"topRight":{"x":58, "y":126}}
                        ],
                        7:[{"topLeft":{"x":51, "y":142},"topRight":{"x":39, "y":165}},
                            {"topLeft":{"x":38, "y":167},"topRight":{"x":32, "y":191}},
                            {"topLeft":{"x":32, "y":194},"topRight":{"x":30, "y":218}},
                            {"topLeft":{"x":30, "y":221},"topRight":{"x":32, "y":245}},
                            {"topLeft":{"x":34, "y":247},"topRight":{"x":42, "y":272}}
                        ],
                        8:[{"topLeft":{"x":46, "y":286},"topRight":{"x":57, "y":309}},
                            {"topLeft":{"x":59, "y":312},"topRight":{"x":76, "y":332}},
                            {"topLeft":{"x":78, "y":334},"topRight":{"x":99, "y":351}}
                        ],
                        9:[{"topLeft":{"x":80, "y":43},"topRight":{"x":110, "y":40}},
                            {"topLeft":{"x":113, "y":40},"topRight":{"x":143, "y":37}},
                            {"topLeft":{"x":147, "y":37},"topRight":{"x":177, "y":34}}
                        ],
                        10:[{"topLeft":{"x":77, "y":87},"topRight":{"x":108, "y":87}},
                            {"topLeft":{"x":111, "y":87},"topRight":{"x":141, "y":85}},
                            {"topLeft":{"x":143, "y":83},"topRight":{"x":168, "y":68}},
                            {"topLeft":{"x":170, "y":67},"topRight":{"x":183, "y":44}}
                        ],
                        11:[{"topLeft":{"x":76, "y":98},"topRight":{"x":107, "y":104}},
                            {"topLeft":{"x":109, "y":105},"topRight":{"x":140, "y":110}},
                            {"topLeft":{"x":142, "y":111},"topRight":{"x":172, "y":117}},
                            {"topLeft":{"x":175, "y":117},"topRight":{"x":205, "y":123}},
                            {"topLeft":{"x":208, "y":123},"topRight":{"x":237, "y":129}},
                            {"topLeft":{"x":241, "y":130},"topRight":{"x":270, "y":135}}
                        ],
                        12:[{"topLeft":{"x":57, "y":129},"topRight":{"x":86, "y":134}},
                            {"topLeft":{"x":90, "y":134},"topRight":{"x":119, "y":144}},
                            {"topLeft":{"x":122, "y":145},"topRight":{"x":149, "y":159}},
                            {"topLeft":{"x":152, "y":161},"topRight":{"x":175, "y":176}},
                            {"topLeft":{"x":179, "y":179},"topRight":{"x":199, "y":197}},
                            {"topLeft":{"x":202, "y":200},"topRight":{"x":216, "y":221}}
                        ],
                        13:[{"topLeft":{"x":45, "y":269},"topRight":{"x":74, "y":262}},
                            {"topLeft":{"x":77, "y":261},"topRight":{"x":105, "y":253}},
                            {"topLeft":{"x":108, "y":252},"topRight":{"x":136, "y":245}},
                            {"topLeft":{"x":139, "y":244},"topRight":{"x":167, "y":236}},
                            {"topLeft":{"x":170, "y":235},"topRight":{"x":198, "y":228}}
                        ],
                        14:[{"topLeft":{"x":49, "y":279},"topRight":{"x":78, "y":271}},
                            {"topLeft":{"x":80, "y":270},"topRight":{"x":108, "y":263}},
                            {"topLeft":{"x":112, "y":262},"topRight":{"x":140, "y":254}},
                            {"topLeft":{"x":142, "y":253},"topRight":{"x":171, "y":246}},
                            {"topLeft":{"x":174, "y":245},"topRight":{"x":202, "y":237}}
                        ],
                        15:[{"topLeft":{"x":172, "y":313},"topRight":{"x":192, "y":295}},
                            {"topLeft":{"x":194, "y":292},"topRight":{"x":206, "y":271}},
                            {"topLeft":{"x":207, "y":268},"topRight":{"x":211, "y":244}}
                        ],
                        16:[{"topLeft":{"x":101, "y":349},"topRight":{"x":114, "y":325}},
                            {"topLeft":{"x":123, "y":320},"topRight":{"x":151, "y":315}}
                        ],
                        17:[{"topLeft":{"x":112, "y":354},"topRight":{"x":140, "y":351}},
                            {"topLeft":{"x":145, "y":350},"topRight":{"x":175, "y":351}},
                            {"topLeft":{"x":179, "y":351},"topRight":{"x":210, "y":357}}
                        ],
                        18:[{"topLeft":{"x":117, "y":367},"topRight":{"x":141, "y":381}},
                            {"topLeft":{"x":145, "y":383},"topRight":{"x":171, "y":393}},
                            {"topLeft":{"x":175, "y":394},"topRight":{"x":203, "y":400}},
                            {"topLeft":{"x":207, "y":400},"topRight":{"x":236, "y":404}},
                            {"topLeft":{"x":241, "y":403},"topRight":{"x":268, "y":402}},
                            {"topLeft":{"x":273, "y":402},"topRight":{"x":300, "y":396}}
                        ],
                        19:[{"topLeft":{"x":198, "y":32},"topRight":{"x":226, "y":22}},
                            {"topLeft":{"x":231, "y":21},"topRight":{"x":261, "y":15}},
                            {"topLeft":{"x":265, "y":15},"topRight":{"x":295, "y":14}},
                            {"topLeft":{"x":299, "y":14},"topRight":{"x":329, "y":16}},
                            {"topLeft":{"x":334, "y":16},"topRight":{"x":362, "y":23}},
                            {"topLeft":{"x":367, "y":24},"topRight":{"x":394, "y":34}}
                        ],
                        20:[{"topLeft":{"x":202, "y":44},"topRight":{"x":221, "y":62}},
                            {"topLeft":{"x":223, "y":65},"topRight":{"x":242, "y":83}},
                            {"topLeft":{"x":245, "y":86},"topRight":{"x":264, "y":104}},
                            {"topLeft":{"x":267, "y":107},"topRight":{"x":286, "y":126}}
                        ],
                        21:[{"topLeft":{"x":222, "y":215},"topRight":{"x":236, "y":194}},
                            {"topLeft":{"x":240, "y":191},"topRight":{"x":254, "y":170}},
                            {"topLeft":{"x":257, "y":167},"topRight":{"x":271, "y":147}}
                        ],
                        22:[{"topLeft":{"x":229, "y":225},"topRight":{"x":258, "y":230}},
                            {"topLeft":{"x":262, "y":230},"topRight":{"x":291, "y":235}},
                            {"topLeft":{"x":295, "y":236},"topRight":{"x":323, "y":241}}
                        ],
                        23:[{"topLeft":{"x":227, "y":235},"topRight":{"x":256, "y":239}},
                            {"topLeft":{"x":260, "y":240},"topRight":{"x":289, "y":245}},
                            {"topLeft":{"x":292, "y":245},"topRight":{"x":321, "y":250}}
                        ],
                        24:[{"topLeft":{"x":215, "y":358},"topRight":{"x":226, "y":335}},
                            {"topLeft":{"x":228, "y":333},"topRight":{"x":243, "y":311}},
                            {"topLeft":{"x":245, "y":308},"topRight":{"x":262, "y":288}},
                            {"topLeft":{"x":266, "y":285},"topRight":{"x":289, "y":269}},
                            {"topLeft":{"x":293, "y":267},"topRight":{"x":322, "y":258}}
                        ],
                        25:[{"topLeft":{"x":230, "y":358},"topRight":{"x":257, "y":348}},
                            {"topLeft":{"x":261, "y":347},"topRight":{"x":287, "y":336}},
                            {"topLeft":{"x":291, "y":335},"topRight":{"x":318, "y":325}}
                        ],
                        26:[{"topLeft":{"x":225, "y":367},"topRight":{"x":254, "y":374}},
                            {"topLeft":{"x":258, "y":375},"topRight":{"x":287, "y":381}},
                            {"topLeft":{"x":290, "y":382},"topRight":{"x":320, "y":389}}
                        ],
                        27:[{"topLeft":{"x":292, "y":125},"topRight":{"x":313, "y":107}},
                            {"topLeft":{"x":315, "y":105},"topRight":{"x":337, "y":87}},
                            {"topLeft":{"x":339, "y":86},"topRight":{"x":360, "y":69}},
                            {"topLeft":{"x":363, "y":66},"topRight":{"x":385, "y":49}}
                        ],
                        28:[{"topLeft":{"x":293, "y":144},"topRight":{"x":304, "y":168}},
                            {"topLeft":{"x":305, "y":170},"topRight":{"x":316, "y":192}},
                            {"topLeft":{"x":318, "y":195},"topRight":{"x":330, "y":219}},
                            {"topLeft":{"x":331, "y":221},"topRight":{"x":341, "y":243}}
                        ],
                        29:[{"topLeft":{"x":338, "y":267},"topRight":{"x":337, "y":292}},
                            {"topLeft":{"x":337, "y":295},"topRight":{"x":335, "y":319}}
                        ],
                        30:[{"topLeft":{"x":335, "y":336},"topRight":{"x":333, "y":360}},
                            {"topLeft":{"x":333, "y":363},"topRight":{"x":331, "y":387}}
                        ],
                        31:[{"topLeft":{"x":295, "y":134},"topRight":{"x":326, "y":133}},
                            {"topLeft":{"x":329, "y":133},"topRight":{"x":360, "y":133}},
                            {"topLeft":{"x":363, "y":133},"topRight":{"x":393, "y":133}},
                            {"topLeft":{"x":397, "y":133},"topRight":{"x":427, "y":132}},
                            {"topLeft":{"x":430, "y":132},"topRight":{"x":461, "y":132}},
                            {"topLeft":{"x":464, "y":132},"topRight":{"x":494, "y":131}}
                        ],
                        32:[{"topLeft":{"x":306, "y":145},"topRight":{"x":334, "y":154}},
                            {"topLeft":{"x":338, "y":155},"topRight":{"x":365, "y":164}},
                            {"topLeft":{"x":369, "y":165},"topRight":{"x":397, "y":175}},
                            {"topLeft":{"x":400, "y":176},"topRight":{"x":427, "y":185}},
                            {"topLeft":{"x":431, "y":186},"topRight":{"x":459, "y":196}}
                        ],
                        33:[{"topLeft":{"x":344, "y":246},"topRight":{"x":366, "y":231}},
                            {"topLeft":{"x":370, "y":228},"topRight":{"x":397, "y":217}},
                            {"topLeft":{"x":402, "y":217},"topRight":{"x":431, "y":210}},
                            {"topLeft":{"x":435, "y":209},"topRight":{"x":464, "y":204}}
                        ],
                        34:[{"topLeft":{"x":356, "y":253},"topRight":{"x":384, "y":255}},
                            {"topLeft":{"x":388, "y":255},"topRight":{"x":417, "y":253}},
                            {"topLeft":{"x":421, "y":253},"topRight":{"x":451, "y":248}},
                            {"topLeft":{"x":453, "y":247},"topRight":{"x":480, "y":238}}
                        ],
                        35:[{"topLeft":{"x":355, "y":263},"topRight":{"x":385, "y":265}},
                            {"topLeft":{"x":389, "y":265},"topRight":{"x":418, "y":263}},
                            {"topLeft":{"x":421, "y":262},"topRight":{"x":451, "y":258}},
                            {"topLeft":{"x":454, "y":257},"topRight":{"x":481, "y":248}}
                        ],
                        36:[{"topLeft":{"x":347, "y":269},"topRight":{"x":368, "y":286}},
                            {"topLeft":{"x":371, "y":288},"topRight":{"x":397, "y":298}},
                            {"topLeft":{"x":401, "y":298},"topRight":{"x":429, "y":302}},
                            {"topLeft":{"x":434, "y":303},"topRight":{"x":463, "y":304}}
                        ],
                        37:[{"topLeft":{"x":343, "y":324},"topRight":{"x":373, "y":320}},
                            {"topLeft":{"x":376, "y":320},"topRight":{"x":407, "y":317}},
                            {"topLeft":{"x":410, "y":317},"topRight":{"x":440, "y":313}},

                        ],
                        38:[{"topLeft":{"x":336, "y":388},"topRight":{"x":364, "y":381}},
                            {"topLeft":{"x":367, "y":380},"topRight":{"x":393, "y":369}},
                            {"topLeft":{"x":397, "y":367},"topRight":{"x":422, "y":354}},
                            {"topLeft":{"x":424, "y":352},"topRight":{"x":445, "y":335}},
                            {"topLeft":{"x":448, "y":333},"topRight":{"x":464, "y":314}}
                        ],
                        39:[{"topLeft":{"x":354, "y":395},"topRight":{"x":383, "y":391}},
                            {"topLeft":{"x":387, "y":390},"topRight":{"x":416, "y":387}},
                            {"topLeft":{"x":419, "y":386},"topRight":{"x":449, "y":383}},
                            {"topLeft":{"x":453, "y":382},"topRight":{"x":483, "y":378}}
                        ],
                        40:[{"topLeft":{"x":336, "y":399},"topRight":{"x":361, "y":411}},
                            {"topLeft":{"x":365, "y":413},"topRight":{"x":392, "y":420}},
                            {"topLeft":{"x":396, "y":421},"topRight":{"x":426, "y":425}},
                            {"topLeft":{"x":429, "y":425},"topRight":{"x":459, "y":426}},
                            {"topLeft":{"x":462, "y":425},"topRight":{"x":492, "y":421}},
                            {"topLeft":{"x":494, "y":420},"topRight":{"x":522, "y":412}}
                        ],
                        41:[{"topLeft":{"x":420, "y":40},"topRight":{"x":449, "y":46}},
                            {"topLeft":{"x":453, "y":46},"topRight":{"x":482, "y":51}},
                            {"topLeft":{"x":486, "y":52},"topRight":{"x":515, "y":57}},
                            {"topLeft":{"x":519, "y":58},"topRight":{"x":547, "y":62}},
                            {"topLeft":{"x":552, "y":63},"topRight":{"x":580, "y":68}},
                            {"topLeft":{"x":584, "y":69},"topRight":{"x":614, "y":74}}
                        ],
                        42:[{"topLeft":{"x":409, "y":49},"topRight":{"x":431, "y":66}},
                            {"topLeft":{"x":434, "y":69},"topRight":{"x":455, "y":86}},
                            {"topLeft":{"x":458, "y":87},"topRight":{"x":479, "y":104}},
                            {"topLeft":{"x":482, "y":107},"topRight":{"x":503, "y":124}}
                        ],
                        43:[{"topLeft":{"x":499, "y":146},"topRight":{"x":489, "y":168}},
                            {"topLeft":{"x":487, "y":172},"topRight":{"x":476, "y":194}}
                        ],
                        44:[{"topLeft":{"x":511, "y":149},"topRight":{"x":500, "y":172}},
                            {"topLeft":{"x":499, "y":176},"topRight":{"x":488, "y":197}}
                        ],
                        45:[{"topLeft":{"x":481, "y":215},"topRight":{"x":494, "y":236}}
                        ],
                        46:[{"topLeft":{"x":492, "y":210},"topRight":{"x":505, "y":232}}
                        ],
                        47:[{"topLeft":{"x":498, "y":253},"topRight":{"x":490, "y":276}},
                            {"topLeft":{"x":488, "y":279},"topRight":{"x":480, "y":302}}
                        ],
                        48:[{"topLeft":{"x":509, "y":256},"topRight":{"x":501, "y":278}},
                            {"topLeft":{"x":500, "y":282},"topRight":{"x":491, "y":304}}
                        ],
                        49:[{"topLeft":{"x":485, "y":319},"topRight":{"x":488, "y":341}},
                            {"topLeft":{"x":489, "y":346},"topRight":{"x":493, "y":369}}
                        ],
                        50:[{"topLeft":{"x":497, "y":318},"topRight":{"x":500, "y":341}},
                            {"topLeft":{"x":501, "y":346},"topRight":{"x":505, "y":368}}
                        ],
                        51:[{"topLeft":{"x":503, "y":385},"topRight":{"x":522, "y":402}}
                        ],
                        52:[{"topLeft":{"x":512, "y":378},"topRight":{"x":533, "y":395}}
                        ],
                        53:[{"topLeft":{"x":522, "y":119},"topRight":{"x":547, "y":109}},
                            {"topLeft":{"x":553, "y":108},"topRight":{"x":580, "y":98}},
                            {"topLeft":{"x":584, "y":97},"topRight":{"x":610, "y":87}}
                        ],
                        54:[{"topLeft":{"x":514, "y":130},"topRight":{"x":544, "y":125}},
                            {"topLeft":{"x":549, "y":125},"topRight":{"x":577, "y":121}},
                            {"topLeft":{"x":583, "y":120},"topRight":{"x":610, "y":115}},
                            {"topLeft":{"x":616, "y":115},"topRight":{"x":644, "y":111}},
                            {"topLeft":{"x":647, "y":111},"topRight":{"x":675, "y":108}},
                            {"topLeft":{"x":681, "y":106},"topRight":{"x":710, "y":102}}
                        ],
                        55:[{"topLeft":{"x":515, "y":139},"topRight":{"x":540, "y":151}},
                            {"topLeft":{"x":544, "y":153},"topRight":{"x":571, "y":160}},
                            {"topLeft":{"x":576, "y":161},"topRight":{"x":604, "y":167}}
                        ],
                        56:[{"topLeft":{"x":486, "y":201},"topRight":{"x":509, "y":187}},
                            {"topLeft":{"x":513, "y":186},"topRight":{"x":537, "y":172}},
                            {"topLeft":{"x":547, "y":170},"topRight":{"x":575, "y":174}},
                            {"topLeft":{"x":579, "y":174},"topRight":{"x":608, "y":177}}
                        ],
                        57:[{"topLeft":{"x":506, "y":234},"topRight":{"x":535, "y":234}},
                            {"topLeft":{"x":539, "y":234},"topRight":{"x":568, "y":234}}
                        ],
                        58:[{"topLeft":{"x":505, "y":245},"topRight":{"x":535, "y":244}},
                            {"topLeft":{"x":539, "y":244},"topRight":{"x":568, "y":243}}
                        ],
                        59:[{"topLeft":{"x":490, "y":307},"topRight":{"x":518, "y":307}},
                            {"topLeft":{"x":523, "y":307},"topRight":{"x":552, "y":306}}
                        ],
                        60:[{"topLeft":{"x":509, "y":360},"topRight":{"x":526, "y":340}},
                            {"topLeft":{"x":528, "y":338},"topRight":{"x":546, "y":318}}
                        ],
                        61:[{"topLeft":{"x":545, "y":403},"topRight":{"x":574, "y":399}},
                            {"topLeft":{"x":578, "y":399},"topRight":{"x":607, "y":395}}
                        ],
                        62:[{"topLeft":{"x":610, "y":166},"topRight":{"x":631, "y":147}},
                            {"topLeft":{"x":635, "y":144},"topRight":{"x":660, "y":130}},
                            {"topLeft":{"x":664, "y":129},"topRight":{"x":693, "y":122}},
                            {"topLeft":{"x":696, "y":121},"topRight":{"x":719, "y":105}}
                        ],
                        63:[{"topLeft":{"x":610, "y":191},"topRight":{"x":594, "y":211}},
                            {"topLeft":{"x":592, "y":214},"topRight":{"x":576, "y":234}}
                        ],
                        64:[{"topLeft":{"x":620, "y":196},"topRight":{"x":604, "y":216}},
                            {"topLeft":{"x":602, "y":219},"topRight":{"x":586, "y":240}}
                        ],
                        65:[{"topLeft":{"x":582, "y":253},"topRight":{"x":575, "y":277}},
                            {"topLeft":{"x":574, "y":281},"topRight":{"x":567, "y":304}}
                        ],
                        66:[{"topLeft":{"x":572, "y":318},"topRight":{"x":587, "y":341}},
                            {"topLeft":{"x":587, "y":343},"topRight":{"x":601, "y":365}},
                            {"topLeft":{"x":604, "y":368},"topRight":{"x":617, "y":389}}
                        ],
                        67:[{"topLeft":{"x":588, "y":255},"topRight":{"x":617, "y":261}},
                            {"topLeft":{"x":620, "y":262},"topRight":{"x":649, "y":269}}
                        ],
                        68:[{"topLeft":{"x":571, "y":308},"topRight":{"x":601, "y":306}},
                            {"topLeft":{"x":604, "y":305},"topRight":{"x":631, "y":295}},
                            {"topLeft":{"x":634, "y":294},"topRight":{"x":656, "y":277}}
                        ],
                        69:[{"topLeft":{"x":630, "y":73},"topRight":{"x":653, "y":57}},
                            {"topLeft":{"x":656, "y":55},"topRight":{"x":683, "y":43}},
                            {"topLeft":{"x":687, "y":42},"topRight":{"x":716, "y":33}},
                            {"topLeft":{"x":720, "y":32},"topRight":{"x":750, "y":26}},
                            {"topLeft":{"x":754, "y":26},"topRight":{"x":785, "y":26}}
                        ],
                        70:[{"topLeft":{"x":640, "y":81},"topRight":{"x":670, "y":86}},
                            {"topLeft":{"x":674, "y":87},"topRight":{"x":703, "y":92}}
                        ],
                        71:[{"topLeft":{"x":627, "y":166},"topRight":{"x":655, "y":160}},
                            {"topLeft":{"x":659, "y":158},"topRight":{"x":690, "y":155}},
                            {"topLeft":{"x":693, "y":155},"topRight":{"x":724, "y":156}}
                        ],
                        72:[{"topLeft":{"x":631, "y":177},"topRight":{"x":659, "y":170}},
                            {"topLeft":{"x":664, "y":170},"topRight":{"x":693, "y":166}},
                            {"topLeft":{"x":698, "y":166},"topRight":{"x":727, "y":167}}
                        ],
                        73:[{"topLeft":{"x":586, "y":244},"topRight":{"x":611, "y":231}},
                            {"topLeft":{"x":614, "y":230},"topRight":{"x":640, "y":217}},
                            {"topLeft":{"x":644, "y":216},"topRight":{"x":670, "y":204}},
                            {"topLeft":{"x":673, "y":203},"topRight":{"x":699, "y":190}},
                            {"topLeft":{"x":702, "y":188},"topRight":{"x":727, "y":177}}
                        ],
                        74:[{"topLeft":{"x":620, "y":389},"topRight":{"x":631, "y":365}},
                            {"topLeft":{"x":633, "y":362},"topRight":{"x":650, "y":341}},
                            {"topLeft":{"x":652, "y":338},"topRight":{"x":671, "y":319}},
                            {"topLeft":{"x":675, "y":316},"topRight":{"x":697, "y":301}}
                        ],
                        75:[{"topLeft":{"x":630, "y":396},"topRight":{"x":640, "y":372}},
                            {"topLeft":{"x":642, "y":369},"topRight":{"x":657, "y":349}},
                            {"topLeft":{"x":662, "y":345},"topRight":{"x":681, "y":327}},
                            {"topLeft":{"x":684, "y":324},"topRight":{"x":706, "y":308}}
                        ],
                        76:[{"topLeft":{"x":641, "y":398},"topRight":{"x":666, "y":385}},
                            {"topLeft":{"x":672, "y":383},"topRight":{"x":699, "y":377}},
                            {"topLeft":{"x":705, "y":376},"topRight":{"x":736, "y":376}},
                            {"topLeft":{"x":742, "y":378},"topRight":{"x":769, "y":389}},
                            {"topLeft":{"x":773, "y":391},"topRight":{"x":797, "y":407}},
                            {"topLeft":{"x":799, "y":410},"topRight":{"x":819, "y":429}}
                        ],
                        77:[{"topLeft":{"x":717, "y":89},"topRight":{"x":732, "y":68}},
                            {"topLeft":{"x":736, "y":63},"topRight":{"x":759, "y":47}},
                            {"topLeft":{"x":763, "y":45},"topRight":{"x":791, "y":34}}
                        ],
                        78:[{"topLeft":{"x":737, "y":107},"topRight":{"x":739, "y":131}},
                            {"topLeft":{"x":740, "y":135},"topRight":{"x":741, "y":159}}
                        ],
                        79:[{"topLeft":{"x":651, "y":266},"topRight":{"x":664, "y":244}},
                            {"topLeft":{"x":667, "y":241},"topRight":{"x":685, "y":222}},
                            {"topLeft":{"x":690, "y":218},"topRight":{"x":715, "y":205}},
                            {"topLeft":{"x":718, "y":204},"topRight":{"x":734, "y":185}}
                        ],
                        80:[{"topLeft":{"x":670, "y":266},"topRight":{"x":695, "y":253}},
                            {"topLeft":{"x":701, "y":250},"topRight":{"x":729, "y":245}},
                            {"topLeft":{"x":735, "y":244},"topRight":{"x":765, "y":246}}
                        ],
                        81:[{"topLeft":{"x":676, "y":278},"topRight":{"x":701, "y":291}}
                        ],
                        82:[{"topLeft":{"x":747, "y":156},"topRight":{"x":773, "y":143}},
                            {"topLeft":{"x":776, "y":142},"topRight":{"x":801, "y":129}}
                        ],
                        83:[{"topLeft":{"x":754, "y":164},"topRight":{"x":778, "y":152}},
                            {"topLeft":{"x":782, "y":150},"topRight":{"x":807, "y":138}}
                        ],
                        84:[{"topLeft":{"x":758, "y":175},"topRight":{"x":785, "y":185}},
                            {"topLeft":{"x":788, "y":187},"topRight":{"x":814, "y":197}}
                        ],
                        85:[{"topLeft":{"x":755, "y":187},"topRight":{"x":761, "y":211}},
                            {"topLeft":{"x":762, "y":213},"topRight":{"x":768, "y":237}}
                        ],
                        86:[{"topLeft":{"x":713, "y":290},"topRight":{"x":734, "y":274}},
                            {"topLeft":{"x":738, "y":272},"topRight":{"x":760, "y":256}}
                        ],
                        87:[{"topLeft":{"x":720, "y":298},"topRight":{"x":742, "y":282}},
                            {"topLeft":{"x":745, "y":279},"topRight":{"x":768, "y":263}}
                        ],
                        88:[{"topLeft":{"x":726, "y":306},"topRight":{"x":756, "y":306}},
                            {"topLeft":{"x":760, "y":307},"topRight":{"x":790, "y":307}}
                        ],
                        89:[{"topLeft":{"x":816, "y":422},"topRight":{"x":798, "y":404}},
                            {"topLeft":{"x":796, "y":401},"topRight":{"x":777, "y":383}},
                            {"topLeft":{"x":774, "y":379},"topRight":{"x":756, "y":361}},
                            {"topLeft":{"x":753, "y":357},"topRight":{"x":735, "y":339}},
                            {"topLeft":{"x":732, "y":336},"topRight":{"x":714, "y":318}}
                        ],
                        90:[{"topLeft":{"x":803, "y":45},"topRight":{"x":807, "y":70}},
                            {"topLeft":{"x":808, "y":73},"topRight":{"x":812, "y":97}},
                            {"topLeft":{"x":813, "y":100},"topRight":{"x":818, "y":124}}
                        ],
                        91:[{"topLeft":{"x":824, "y":145},"topRight":{"x":825, "y":169}},
                            {"topLeft":{"x":826, "y":172},"topRight":{"x":827, "y":195}}
                        ],
                        92:[{"topLeft":{"x":835, "y":144},"topRight":{"x":836, "y":168}},
                            {"topLeft":{"x":837, "y":172},"topRight":{"x":838, "y":195}}
                        ],
                        93:[{"topLeft":{"x":774, "y":247},"topRight":{"x":793, "y":228}},
                            {"topLeft":{"x":795, "y":225},"topRight":{"x":814, "y":207}}
                        ],
                        94:[{"topLeft":{"x":783, "y":252},"topRight":{"x":801, "y":234}},
                            {"topLeft":{"x":804, "y":231},"topRight":{"x":823, "y":213}}
                        ],
                        95:[{"topLeft":{"x":783, "y":260},"topRight":{"x":808, "y":273}},
                            {"topLeft":{"x":819, "y":279},"topRight":{"x":807, "y":301}}
                        ],
                        96:[{"topLeft":{"x":805, "y":315},"topRight":{"x":806, "y":338}},
                            {"topLeft":{"x":807, "y":342},"topRight":{"x":811, "y":366}},
                            {"topLeft":{"x":811, "y":369},"topRight":{"x":820, "y":392}},
                            {"topLeft":{"x":822, "y":395},"topRight":{"x":837, "y":415}}
                        ],
                        97:[{"topLeft":{"x":819, "y":32},"topRight":{"x":842, "y":48}},
                            {"topLeft":{"x":845, "y":50},"topRight":{"x":869, "y":66}}
                        ],
                        98:[{"topLeft":{"x":812, "y":40},"topRight":{"x":834, "y":55}},
                            {"topLeft":{"x":838, "y":58},"topRight":{"x":860, "y":73}}
                        ],
                        99:[{"topLeft":{"x":824, "y":127},"topRight":{"x":838, "y":106}},
                            {"topLeft":{"x":841, "y":103},"topRight":{"x":856, "y":83}}
                        ],
                        100:[{"topLeft":{"x":833, "y":132},"topRight":{"x":848, "y":112}},
                             {"topLeft":{"x":850, "y":108},"topRight":{"x":866, "y":88}}
                        ],

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
