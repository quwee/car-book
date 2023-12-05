window.addEventListener('load', function() {

    let btnCheck = document.getElementById('btn-check');
    btnCheck.addEventListener('click', function(event) {

        let minValue = document.getElementById('min').value;
        let hourValue = document.getElementById('hour').value;
        let dayValue = document.getElementById('day').value;

        fetch(`http://localhost:8081/check?mins=${minValue}&hours=${hourValue}&days=${dayValue}`)
            .then(response => {
                if(response.ok) {
                    return response.json();
                }
                else {
                    throw new Error('Error: ' + response.status);
                }
            })
            .then(total => {
                let span = document.getElementById('total-value');
                span.innerText = total.toFixed(2);
            })
            .catch(error => console.log(error));
    });

    let btnFinish = document.getElementById('btn-finish');
    btnFinish.addEventListener('click', function() {

        let form = document.getElementById('finish-form');
        form.submit();
    })
});




