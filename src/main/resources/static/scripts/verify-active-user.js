user = document.getElementById("verify-user");
if (sessionStorage.getItem("token") !== null) {
    user.textContent = sessionStorage.getItem("username");

    user.addEventListener("click", function (event) {
        event.preventDefault();

        askQuestion();
    });
}

function askQuestion() {
    let response = confirm("You want to log out?");

    if (response) {
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("username");

        setTimeout(function() {
            window.location.href="/";
        }, 1000);
    }
}