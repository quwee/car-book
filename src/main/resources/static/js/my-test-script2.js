const pageNum = 0;
let pageSize = 3;

let activeIndex = 1;
let activeClassName = 'active';
let carContainerName = 'container-row-kek'

fetch(`http://localhost:8081/cars-json?pageNum=${pageNum}&pageSize=${pageSize}`)
    .then(response => response.json())
    .then(responseCarObject => {

        responseCarObject.cars.forEach(car => {
           createDivCarElement(car);
        })

        createPaginationItems(responseCarObject.countPages);

        addListenersToPaginationItems();

    })
    .catch(err => console.log('Error getting cars: ' + err.message))






function createDivCarElement(car) {
    console.log(car)
    let divString =
        `<div class="col-md-4">
                    <div class="car-wrap rounded ftco-animate">
                        <div class="img rounded d-flex align-items-end"
                             style="background-image: url(${car.imgRequestUrl});">
                        </div>
                        <div class="text">
                            <h2 class="mb-0"><a href="car-single.html">${car.name}</a></h2>
                            <div class="d-flex mb-3">
                                <span class="cat">${car.brand}</span>
                                <p class="price ml-auto">$${car.pricePerDay} <span>/day</span></p>
                            </div>
                            <p class="d-flex mb-0 d-block"><a href="/confirm-start-ride/${car.id}" class="btn btn-primary py-2 mr-1">Book now</a> <a
                                    href="/cars/${car.id}" class="btn btn-secondary py-2 ml-1">Details</a></p>
                        </div>
                    </div>
                </div>`

    const parentElement = document.getElementById(carContainerName);

    const tempElement = document.createElement('div');
    tempElement.innerHTML = divString;

    const divElement = tempElement.firstChild;

    parentElement.appendChild(divElement);
}

function createPaginationItems(countPages) {
    const ul = document.getElementById('ul-pagination');

    for (let i = 0; i < countPages + 2; i++) {
        let liStr;
        switch (i) {
            case 0:
                liStr = '<li><span>&lt;</span></li>';
                break;
            case 1:
                liStr = '<li class="active"><span>1</span></li>';
                break;
            case countPages + 1:
                liStr = '<li><span>&gt;</span></li>';
                break;
            default:
                liStr = `<li><span>${i}</span></li>`;
        }
        const tempElement = document.createElement('li');
        tempElement.innerHTML = liStr;
        const li = tempElement.firstChild;
        ul.appendChild(li);
    }
}

function addListenersToPaginationItems() {
    let listItems = document.querySelectorAll('.block-27 ul li');
    let leftArrow = listItems[0];
    let rightArrow = listItems[listItems.length - 1];

    leftArrow.addEventListener('click', function () {
        if (activeIndex > 1) {
            listItems[activeIndex].classList.remove(activeClassName);
            activeIndex--;
            listItems[activeIndex].classList.add(activeClassName);
            loadPageCars();
        }
    });

    rightArrow.addEventListener('click', function () {
        if (activeIndex < listItems.length - 2) {
            listItems[activeIndex].classList.remove(activeClassName);
            activeIndex++;
            listItems[activeIndex].classList.add(activeClassName);
            loadPageCars();
        }
    });


    let firstIndex = 1;
    let lastIndex = listItems.length - 1;

    for (let i = firstIndex; i < lastIndex; i++) {
        let item = listItems[i];
        item.addEventListener('click', function () {
            if (!this.classList.contains(activeClassName)) {
                let activeItem = document.querySelector('.block-27 ul li.' + activeClassName);
                if (activeItem) {
                    activeItem.classList.remove(activeClassName);
                    activeIndex = parseInt(this.textContent);
                    this.classList.add(activeClassName);

                    loadPageCars();
                }
            }
        })
    }
}

function loadPageCars() {
    const parentElement = document.getElementById(carContainerName);
    parentElement.innerHTML = '';

    fetch(`http://localhost:8081/cars-json?pageNum=${activeIndex - 1}&pageSize=${pageSize}`)
        .then(response => response.json())
        .then(responseCarObject => {
            responseCarObject.cars.forEach(car => {
                createDivCarElement(car);
            })
        })
}