document.getElementById('container__form').addEventListener('submit', async function (event) {
    event.preventDefault();

    let username = document.getElementById('username');
    let password = document.getElementById('password');
    let passwordConfirm = document.getElementById('passwordConfirm');
    username = username.value;
    password = password.value;
    passwordConfirm = passwordConfirm.value;

    if (password !== passwordConfirm)
        alert("Different password!");
    else {
        const data = {
            "username": username,
            "password": password,
        };

        try {
            const response = await fetch(window.location.origin+'/register', {
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
                alert("Username already exist!");
            }
        } catch (error) {
            console.error('Error:', error);
            alert("Username already exist!");
        }
    }
});