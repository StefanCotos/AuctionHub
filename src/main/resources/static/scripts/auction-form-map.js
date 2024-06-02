var map = L.map("map").setView([47.1563, 27.5843], 14);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

var marker = L.marker();

function onMapClick(e) {
    marker = L.marker(e.latlng).addTo(map);
}

map.on("click", onMapClick);

function toggleTag(tag) {
    var button = document.getElementById(tag);
    button.classList.toggle("active");

    var input = document.getElementById(tag + "-input");
    input.value = input.value === "1" ? "0" : "1";
}