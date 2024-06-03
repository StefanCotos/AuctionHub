const urlParams = new URLSearchParams(window.location.search);
const auctionId = parseInt(urlParams.get('id'));

async function fetchAuctions() {
    try {
        const response = await fetch(window.location.origin + `/auctions/${auctionId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const auction = await response.json();
        const auctionIdShow = document.getElementById('auction-id');
        auctionIdShow.innerHTML = `<h1>auction with ID: ${auction.id}</h1>`
        const auctionDetailsContainer = document.getElementById('grid__right-card1');
        auctionDetailsContainer.innerHTML = `
                                        <h2>${auction.title}</h2>
                                        <br>
                                        <h2>
                                            <span id="price-${auction.id}">Starting price: ${auction.initialPrice}$</span>
                                        </h2>
                                        <h2>
                                            <span id="bidder-${auction.id}"></span>
                                        </h2>
                                        <br>
                                        <h2>Description</h2>
                                        <p>
                                            ${auction.description}
                                        </p>
                                        <br>
                                        <h4>Other Information</h4>
                                        <p>
                                            Expires at:
                                            <br>
                                            Date: ${auction.deadline}
                                            <br>
                                            Time: ${auction.time}
                                        </p>
                                        <br>
                                        <h4>Seller</h4>
                                        <p>
                                            <span id="user-${auction.id}"></span>
                                        </p>
                                        `;
        const price = await getBid(auction.id);
        if (price) {
            const priceElement = document.getElementById(`price-${auction.id}`);
            if (priceElement) {
                priceElement.textContent = "Current bid: " + price + "$";
            }
        }
        const userName = await getUsername(auction.usersId);
        const userElement = document.getElementById(`user-${auction.id}`);
        if (userElement) {
            userElement.textContent = userName;
        }
        const bidder = await getBidder(auction.id);
        const bidderElement = document.getElementById(`bidder-${auction.id}`);
        if (bidderElement) {
            bidderElement.textContent = "Current bidder: " + bidder;
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

document.addEventListener('DOMContentLoaded', async function () {
    const auctionDetailsContainer = document.getElementById("images-slide");
    try {
        const response = await fetch(window.location.origin + `/imagesDatabase/auctions_id/${auctionId}`);
        if (!response.ok) {
            const auctionDiv = document.createElement('div');
            auctionDiv.innerHTML = `
                <div class="mySlides fade">
                    <img src="https://res.cloudinary.com/hbhejxvqj/image/upload/v1717421652/null.jpg" alt="Auction images" class="slides__image-card">
                </div>
            `;
            auctionDetailsContainer.innerHTML=auctionDiv.innerHTML;
        }
        const auctions = await response.json();
        // const auctionDetailsContainer = document.getElementById("images-slide");
        for (const auction of auctions) {
            const auctionDiv = document.createElement('div');
            auctionDiv.innerHTML = `
                <div class="mySlides fade">
                    <img src="${auction.extension}" alt="Auction images" class="slides__image-card">
                </div>
            `;
            auctionDetailsContainer.appendChild(auctionDiv);
        }
        fetchAuctions();
        setTimeout(function () {
        }, 2000)
        updatePrice();
        showSlides()
    } catch (error) {
        const auctionDiv = document.createElement('div');
        auctionDiv.innerHTML = `
                <div class="mySlides fade">
                    <img src="https://res.cloudinary.com/hbhejxvqj/image/upload/v1717421652/null.jpg" alt="Auction images" class="slides__image-card">
                </div>
            `;
        auctionDetailsContainer.innerHTML=auctionDiv.innerHTML;
    }
});

let slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("mySlides fade");

    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    slides[slideIndex - 1].removeAttribute("style");
}

async function getBid(id) {
    try {
        const response = await fetch(window.location.origin + `/bids/auctions_id/${id}`);
        if (response.ok) {
            const bid = await response.json();
            return bid.price;
        } else {
            return null;
        }
    } catch (error) {
        return null;
    }
}

async function getUsername(id) {
    const response = await fetch(window.location.origin + `/users/${id}`);
    if (response.ok) {
        const user = await response.json();
        return user.username;
    } else {
        return null;
    }
}

async function getBidder(id) {
    try {
        const response = await fetch(window.location.origin + `/bids/auctions_id/${id}`);
        if (response.ok) {
            const bid = await response.json();
            return getUsername(bid.usersId);
        } else {
            return null;
        }
    } catch (error) {
        return "none";
    }
}


function updatePrice() {
    let socket = new SockJS('/ws');
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            message = message.body.split('_');
            let messagesDiv = document.getElementById('price-' + message[0]);
            let existingPrice = messagesDiv.textContent;
            existingPrice = existingPrice.split(" ");
            let newPrice = message[1].split(" ");
            existingPrice = parseInt(existingPrice[2]);
            newPrice = parseInt(newPrice[2]);
            if (existingPrice >= newPrice) {
                alert('The placed bid is lower or equal than the current one, please enter a higher bid!');
            } else {
                let current_bidder = document.getElementById('bidder-' + message[0]);
                current_bidder.textContent = "Current bidder: " + message[2];
                messagesDiv.textContent = message[1];
            }
        })
    });
}

//
// window.onload = function () {
//     fetchAuctions();
//     setTimeout(function () {
//     }, 2000)
//     updatePrice();
// };
