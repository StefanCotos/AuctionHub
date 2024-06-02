var map = L.map("map").setView([47.1563, 27.5843], 14);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

var marker1 = L.marker([47.1594, 27.619]).addTo(map);
var marker2 = L.marker([47.1719, 27.5813]).addTo(map);
var marker3 = L.marker([47.1467, 27.5841]).addTo(map);

var circle1 = L.circle([47.169, 27.5996], {
    color: "#f03",
    fillColor: "#f03",
    fillOpacity: 0.3,
    radius: 100,
}).addTo(map);
var circle2 = L.circle([47.1491, 27.57944], {
    color: "#f03",
    fillColor: "#f03",
    fillOpacity: 0.3,
    radius: 200,
}).addTo(map);
var circle3 = L.circle([47.1449, 27.6205], {
    color: "#f03",
    fillColor: "#f03",
    fillOpacity: 0.3,
    radius: 300,
}).addTo(map);
