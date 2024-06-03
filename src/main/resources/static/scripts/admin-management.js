async function fetch_users() {
    try {
        const response = await fetch(window.location.origin + '/users');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const users = await response.json();
        const userContainer = document.getElementById('all-users');
        const userTr = document.createElement('tr');
        userTr.innerHTML = `
                        <th>ID</th>
                        <th>Name</th>
                        <th>Action</th>
                           `;
        userContainer.appendChild(userTr);
        for (const user of users) {
            const userTr = document.createElement('tr');
            userTr.innerHTML = `
                                    <td>${user.id}</td>
                                    <td>${user.username}</td>
                                    <td>
                                    <button onclick="removeUser(${user.id})">
                                        Remove User
                                    </button>
                                    </td>
                           `;
            userContainer.appendChild(userTr);
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function fetch_auctions() {
    try {
        const response = await fetch(window.location.origin + '/auctions');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const auctions = await response.json();
        const auctionContainer = document.getElementById('all-auctions');
        for (const auction of auctions) {
            const auctionLi = document.createElement('li');
            auctionLi.innerHTML = `
                                auction ID: ${auction.id}
                                <button onclick="removeAuction(${auction.id});">
                                    Remove auction
                                </button>
                           `;
            auctionContainer.appendChild(auctionLi);
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function removeUser(id) {
    const response1 = await fetch(window.location.origin + `/bids/users_id/${id}`, {method: 'DELETE'});
    if (response1.ok) {
        console.log("Bid removed!")
    } else {
        return null;
    }
    const response2 = await fetch(window.location.origin + `/auctions/users_id/${id}`);
    if (response2.ok) {
        const auctions = await response2.json();
        for (const auction of auctions){
            await removeAuction(auction.id)
        }
        console.log("Auctions removed!");
    } else {
        return null;
    }

    const response = await fetch(window.location.origin + `/users/${id}`, {method: 'DELETE'});
    if (response.ok) {
        alert("User removed!")
        setTimeout(function () {
            window.location.reload();
        }, 2000);
    } else {
        return null;
    }
}

async function removeAuction(id) {
    const response1 = await fetch(window.location.origin + `/imagesDatabase/auctions_id/${id}`, {method: 'DELETE'});
    if (response1.ok) {
        console.log("Image removed!")
    } else {
        return null;
    }
    const response2 = await fetch(window.location.origin + `/bids/auctions_id/${id}`, {method: 'DELETE'});
    if (response2.ok) {
        console.log("Bid removed!")
    } else {
        return null;
    }
    const response = await fetch(window.location.origin + `/auctions/${id}`, {method: 'DELETE'});
    if (response.ok) {
        alert("Auction removed!")
        setTimeout(function () {
            window.location.reload();
        }, 2000);
    } else {
        return null;
    }
}

window.onload = function () {
    fetch_users();
    fetch_auctions();
};
