let Ma = {
   'Init' : {
     app_id:   'BTHrIHz6qnrDm7zBkR7l',
     app_code: 'iYejqpbyyoaPkfGvkYYH0A',
     useHTTPS: true
   },
   'Behavior' :    {},       // Управление событиями карты
   'Container' :   {},       // Контейнер для отображения карты
   'PlacesService':{},       // Сервис Places API
   'PlacesGroup':  {},       // Группа для хранения точек интереса
   'Lat' :         55.815214,// Широта (центр карты)
   'Lng' :         37.574802,// Долгота (центр карты)
   'Layers' :      {},       // Список картографических основ
   'Map' :         {},       // Объект карты
   'Platform' :    {},       // Платформа HERE API
   'UI' :          {},       // Пользовательский интерфейс
   'Zoom' :        17        // 1 == весь мир, 15 == масштаб улицы
};


// Контейнер для отображения карты
Ma.Container = document.querySelector('#map')
  
// Инициализация платформы
Ma.Platform = new H.service.Platform(Ma.Init)
  
// Получение доступа к сервису Places API
Ma.PlacesService = Ma.Platform.getPlacesService()

// Создание группы для хранения результатов запроса Places API
Ma.PlacesGroup = new H.map.Group()
  
// Список картографических основ
Ma.Layers = Ma.Platform.createDefaultLayers({lg:'rus'})

// Создание объекта карты 
Ma.Map = new H.Map(Ma.Container, Ma.Layers.normal.map)
  
// Добавление интерактивности
Ma.Behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(Ma.Map))
  
// Пользовательский интерфейс
Ma.UI = H.ui.UI.createDefault(Ma.Map, Ma.Layers)

// Обработчик изменения размеров окна браузера
window.addEventListener('resize', () => {
  Ma.Map.getViewPort().resize()
})

// Функция для отображения карты
function displayMap () {
    
    // Центр карты
    Ma.Map.setCenter({lat: Ma.Lat, lng: Ma.Lng})
    
    // Масштаб 2 - весь мир, 17 - уровень улицы
    Ma.Map.setZoom(Ma.Zoom)

    // Группа для хранения маркеров
    Ma.Map.addObject(Ma.PlacesGroup)
}

// Вызов функции для инициализации карты
displayMap()

let G = {
    'StartTrackPosition': {},  // Функция отслеживания местоположения
    'ShowPosition': {},        // Функция отображения местоположения пользователя на карте
    'ShowError': {},           // Обработчик ошибки - например пользователь не дал доступ к трекингу геолокации 
    'CurrentPosition': {},     // Координаты текущего местоположения
    'LocationMarker':{},       // Маркер с текущим местоположением
}

G.StartTrackPosition = () => {
    // Проверка поддержки браузером Geolocation API
    if (navigator.geolocation) {
        // Начать трекинг местоположения
        navigator.geolocation.watchPosition(G.ShowPosition, G.ShowError)
    } else {
        // Вывод в консоль сообщения об ошибке
        console.log("Geolocation is not supported by this browser.")
    }
}

G.ShowPosition = position => {
    // Сохранение координат текущего местоположения
    G.CurrentPosition = { 
        lat: position.coords.latitude, 
        lng: position.coords.longitude 
    }

    // Обновление центра карты
    // M.Map.setCenter(G.CurrentPosition)
    
    // Если объект маркера уже существует, обновляем значение координат на текущее
    if (G.LocationMarker instanceof H.map.Marker) {
        G.LocationMarker.setPosition(G.CurrentPosition)
    } else {
        G.LocationMarker = new H.map.Marker({ lat: G.CurrentPosition.lat, lng: G.CurrentPosition.lng }, {});
        Ma.Map.addObject(G.LocationMarker)
    }       
}

// Обработка ошибок
G.ShowError = error =>{
    switch(error.code) {
        case error.PERMISSION_DENIED:
          alert("User denied the request for Geolocation.")
          break
        case error.POSITION_UNAVAILABLE:
          alert("Location information is unavailable.")
          break
        case error.TIMEOUT:
          alert("The request to get user location timed out.")
          break
        case error.UNKNOWN_ERROR:
          alert("An unknown error occurred.")
          break
    }
}

// Запуск трекинга местоположения
G.StartTrackPosition()

var polyline, group, from, to;

function calculateRouteFromAtoB (way, type = 0) {
  var router = Ma.Platform.getRoutingService(),
    routeRequestParams = {
      mode: type ? 'shortest;car' : 'shortest;publicTransport',
      representation: 'display',
      routeattributes : 'waypoints,summary,shape,legs',
      maneuverattributes: 'direction,action'
    };

  for (var i in way) {
    routeRequestParams['waypoint'+i] = way[i].lat+","+way[i].lng;
  }

  router.calculateRoute(
    routeRequestParams,
    onSuccess,
    onError
  );
}

function onSuccess(result) {
  var route = result.response.route[0];
  if (polyline) Ma.Map.removeObject(polyline); 
  addRouteShapeToMap(route);
  // if (group) Ma.Map.removeObject(group);
  // addManueversToMap(route);
}

function onError(error) {
  alert('Ooops!');
}

var bubble;

function openBubble(position, text){
  if(!bubble){
    bubble = new H.ui.InfoBubble(
      position,
      // The FO property holds the province name.
      {content: text});
    Ma.UI.addBubble(bubble);
  } else {
    bubble.setPosition(position);
    bubble.setContent(text);
    bubble.open();
  }
}

function addRouteShapeToMap(route){
  var lineString = new H.geo.LineString(),
    routeShape = route.shape;

  routeShape.forEach(function(point) {
    var parts = point.split(',');
    lineString.pushLatLngAlt(parts[0], parts[1]);
  });

  polyline = new H.map.Polyline(lineString, {
    style: {
      lineWidth: 4,
      strokeColor: 'rgba(0, 128, 255, 0.7)'
    }
  });
  // Add the polyline to the map
  Ma.Map.addObject(polyline);
  // And zoom to its bounding rectangle
  // Ma.Map.setViewBounds(polyline.getBounds(), true);
}

// function addManueversToMap(route){
//   var svgMarkup = '<svg width="18" height="18" ' +
//     'xmlns="http://www.w3.org/2000/svg">' +
//     '<circle cx="8" cy="8" r="8" ' +
//       'fill="#1b468d" stroke="white" stroke-width="1"  />' +
//     '</svg>',
//     dotIcon = new H.map.Icon(svgMarkup, {anchor: {x:8, y:8}}),
//     i,
//     j;

//   group = new  H.map.Group();

//   // Add a marker for each maneuver
//   for (i = 0;  i < route.leg.length; i += 1) {
//     for (j = 0;  j < route.leg[i].maneuver.length; j += 1) {
//       // Get the next maneuver.
//       maneuver = route.leg[i].maneuver[j];
//       // Add a marker to the maneuvers group
//       var marker =  new H.map.Marker({
//         lat: maneuver.position.latitude,
//         lng: maneuver.position.longitude} ,
//         {icon: dotIcon});
//       marker.instruction = maneuver.instruction;
//       group.addObject(marker);
//     }
//   }

//   group.addEventListener('tap', function (evt) {
//     Ma.Map.setCenter(evt.target.getPosition());
//     openBubble(evt.target.getPosition(), evt.target.instruction);
//   }, false);

//   // Add the maneuvers group to the map
//   Ma.Map.addObject(group);
// }

// function reverseGeocode(lat, lng, type) {
//   var geocoder = Ma.Platform.getGeocodingService(),
//     reverseGeocodingParameters = {
//       prox: lat+','+lng+',150',
//       mode: 'retrieveAddresses',
//       maxresults: '1',
//       jsonattributes : 1
//     };

//   geocoder.reverseGeocode(
//     reverseGeocodingParameters,
//     type == 'from' ? onSuccessFrom : onSuccessTo,
//     onError
//   );
// }

// function onSuccessFrom(result) {
//   inputFrom.value = result.response.view[0].result[0].location.address.label;
//   M.updateTextFields();
// }

// function onSuccessTo(result) {
//   inputTo.value = result.response.view[0].result[0].location.address.label;
//   M.updateTextFields();
// }

$('#clearMap').on('click', function() {
  let obj = Ma.Map.getObjects().filter(el => el.Ob.src.B);
  Ma.Map.removeObjects(obj);
  Ma.Map.removeObject(polyline); 
  polyline = 0;
});

var socket = io();

var reader = new FileReader();
reader.onload = function(){
  socket.emit('sendCsv', reader.result);
  alert('Send!');
};

let openCsv = function(e){
  reader.readAsText(e.target.files[0]);
};

socket.emit('getData');

setInterval(() => { if (onlineData.checked) {socket.emit('getData')} }, 5 * 1000);

socket.on('recData', (data) => {
  console.log('update');
  Ma.Map.removeObjects(Ma.Map.getObjects().filter(el => el.Ob.src.B));
  for (var i of data) {
    // console.log(i);
    let circle = new H.map.Circle(
      {lat: i.lat, lng: i.lng}, i.radius,
      {style: {lineWidth: 0, fillColor: 'rgba(0, 128, 0, 0.7)'}}
    );

    circle.instruction = i.time;

    circle.addEventListener('tap', function (evt) {
      // Ma.Map.setCenter(evt.target.getCenter());
      openBubble(evt.target.getCenter(), evt.target.instruction+'s');
    }, false);

    Ma.Map.addObject(circle);
  }

  calculateRouteFromAtoB( Ma.Map.getObjects().filter(el => el.Ob.src.B).map(el => el.getCenter()) );

  M.Map.setViewBounds(M.PlacesGroup.getBounds());
});