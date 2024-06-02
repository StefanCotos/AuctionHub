document.getElementById('container__form').addEventListener('submit', async function (event) {
    event.preventDefault();

    let username = document.getElementById('username');
    let password = document.getElementById('password');
    username = username.value;
    password=password.value;


    const data = {
        "username": username,
        "password": password,
    };

    try {
        const response = await fetch(window.location.origin+'/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const jsonResponse = await response.json();
            sessionStorage.setItem("token", jsonResponse.token);
            sessionStorage.setItem("username", username);

            setTimeout(function() {
                window.location.href = "/";
            }, 2000);
        } else {
            console.error('Error:', response.statusText);
            alert("Incorrect data!");
        }
    } catch (error) {
        console.error('Error:', error);
        alert("Incorrect data!");
    }
});