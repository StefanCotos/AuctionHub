document.getElementById('auction-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    let bid_amount = document.getElementById('bid-amount');
    bid_amount = bid_amount.value;

    const urlParams = new URLSearchParams(window.location.search);
    const auctionId = parseInt(urlParams.get('id'));

    if (sessionStorage.getItem("token") === null) {
        alert("You need to log in or register!");
    } else {
        const data = {
            "price": bid_amount,
            "auctionsId": auctionId,
        };

        try {
            const response = await fetch(window.location.origin+'/bidsForm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${sessionStorage.getItem("token")}`
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
            } else {
                console.error('Error:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
});