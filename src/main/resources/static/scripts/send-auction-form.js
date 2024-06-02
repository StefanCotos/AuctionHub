document.getElementById('auction-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    let title = document.getElementById('title');
    let description = document.getElementById('description');
    let price = document.getElementById('price');
    let deadline = document.getElementById('deadline');
    let time = document.getElementById('time');
    title = title.value;
    description = description.value;
    price = price.value;
    deadline = deadline.value;
    time = time.value;

    if (sessionStorage.getItem("token") === null) {
        alert("You need to log in or register!");
    } else {
        form = document.getElementById('auction-form');
        const formData = new FormData(form);

        try {
            const response = await fetch(window.location.origin+'/auctionsForm', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${sessionStorage.getItem("token")}`
                },
                body: formData
            });

            if (response.ok) {
                setTimeout(function () {
                    window.location.reload();
                }, 2000);
            } else {
                console.error('Error:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
});