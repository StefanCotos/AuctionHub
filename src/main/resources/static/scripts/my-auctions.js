async function fetchAuctions() {
    try {
        const response = await fetch(window.location.origin+`/auctions/users_id/${await getId(sessionStorage.getItem("username"))}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const auctions = await response.json();
        const auctionContainer = document.getElementById('container');
        for (const auction of auctions) {
            const auctionDiv = document.createElement('div');
            auctionDiv.className = 'container__auction';
            // auctionDiv.id = `auction-${auction.id}`;
            auctionDiv.innerHTML = `
                                <h2>auction ID: ${auction.id}</h2>
                                <p>Expire at: ${auction.deadline}</p>
                                <p>Status: Active</p>
                                <p>Description: ${auction.description}</p>
                                <br>
                           `;
            auctionContainer.appendChild(auctionDiv);
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function getId(username) {
    const response = await fetch(window.location.origin+`/users/username/${username}`);
    if (response.ok) {
        const user = await response.json();
        return user.id;
    } else {
        return null;
    }
}

window.onload = function () {
    fetchAuctions();
};