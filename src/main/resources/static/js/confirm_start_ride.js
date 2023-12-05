ymaps.ready(init);

let url = window.location.pathname;
let segments = url.split('/');
let carId = segments[segments.length - 1];

function init(){

    fetch(`http://localhost:8081/cars-json/${carId}`)
        .then(response => response.json())
        .then(car => {

            console.log('car from json: ' + car);
            console.log('car latitude: ' + car.latitude);
            console.log('car longitude: ' + car.longitude);

            let mapCenter = [car.latitude, car.longitude];

            let myMap = new ymaps.Map("map", {
                center: mapCenter,
                zoom: 15,
                controls: []
            });

            let placeMark = new ymaps.Placemark(mapCenter, {
                balloonContent: `
            <div class="balloon">
                <div class="balloon_car_img_box">
                    <img class="img-balloon" src=${car.imgRequestUrl} alt=${car.name}>
                </div>
                <h3 class"balloon_car_name">${car.name}</h3>
            </div>
        `
            }, {
                iconLayout: 'default#image',
                iconImageHref: '/images/location-pin.png',
                iconImageSize: [32, 32],
                iconImageOffset: [-11, -30]
            });

            myMap.geoObjects.add(placeMark);
        })
        .catch(err => console.log('Error getting cars: ' + err.message));
}


