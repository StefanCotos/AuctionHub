async function fetchAuctions() {
    try {
        const response = await fetch(window.location.origin+'/auctions');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const auctions = await response.json();
        const auctionContainer = document.getElementById('container__grid');
        for (const auction of auctions) {
            const auctionDiv = document.createElement('div');
            auctionDiv.className = 'grid__card';
            auctionDiv.id = `auction-${auction.id}`;
            auctionDiv.innerHTML = `
                                <div class="card__img">
                                    <a href="auction-info.html?id=${auction.id}">
                                        <img src="${await getPhoto(auction.id)}" alt="Image with an auction">
                                    </a>
                                </div>
                                <h2>${auction.title}</h2>
                                <h2>
                                    <span id="price-${auction.id}">Starting price: ${auction.initialPrice}$</span>
                                </h2>
                                <h4>Description</h4>
                                <p>
                                    ${auction.description}
                                </p>
                                    <h4>Check to see more information</h4>
                                <h4>Seller</h4>
                                <p>
                                    <span id="user-${auction.id}"></span>
                                </p>
                           `;
            auctionContainer.appendChild(auctionDiv);
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
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function getBid(id) {
    try {
        const response = await fetch(window.location.origin+`/bids/auctions_id/${id}`);
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
    const response = await fetch(window.location.origin+`/users/${id}`);
    if (response.ok) {
        const user = await response.json();
        return user.username;
    } else {
        return null;
    }
}

async function getPhoto(id) {
    try {
        const response = await fetch(window.location.origin+`/imagesDatabase/auctions_id/${id}`);
        if (response.ok) {
            let image = await response.json();
            image = image[0];
            return image.extension;
        } else {
            return "https://res.cloudinary.com/hbhejxvqj/image/upload/v1717421652/null.jpg";
        }
    } catch (error) {
        return "https://res.cloudinary.com/hbhejxvqj/image/upload/v1717421652/null.jpg";
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
                messagesDiv.textContent = message[1];
            }
        })
    });
}

window.onload = function () {
    fetchAuctions();
    setTimeout(function () {
    }, 2000)
    updatePrice();
};

// window.onload = fetchAuctions;