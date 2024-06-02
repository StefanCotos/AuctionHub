myAuctionLink = document.getElementById("my-auctions")

if (sessionStorage.getItem("token") === null) {

    myAuctionLink.addEventListener("click", function (event) {
        event.preventDefault();

        alert("You need to log in or register!");
    });
}