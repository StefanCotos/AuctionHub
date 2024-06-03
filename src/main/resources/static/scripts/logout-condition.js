admin_button = document.getElementById("admin-permission")

if (sessionStorage.getItem("token") === null) {
    admin_button.style.display="block";
}