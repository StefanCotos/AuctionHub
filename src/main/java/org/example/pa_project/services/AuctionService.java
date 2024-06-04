package org.example.pa_project.services;


import jakarta.transaction.Transactional;
import org.example.pa_project.entities.Auction;
import org.example.pa_project.repositories.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * A class representing an authentication response which contains a token.
 * This is used in the security context of the application to manage
 * authentication tokens.
 */
@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    /**
     * Constructs an {@code AuctionService} with the specified auction repository.
     *
     * @param auctionRepository the auction repository
     */
    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }
    /**
     * Retrieves all auctions.
     *
     * @return a list of all auctions
     */
    public List<Auction> getAuctions() {
        return auctionRepository.findAll();
    }
    /**
     * Adds a new auction.
     *
     * @param auction the auction to add
     * @return the added auction
     */
    public Auction addAuction(Auction auction) {
        return auctionRepository.save(auction);
    }
    /**
     * Retrieves an auction by its ID.
     *
     * @param id the ID of the auction to retrieve
     * @return the retrieved auction, or {@code null} if no auction with the given ID exists
     */
    public Auction getAuction(long id) {
        return auctionRepository.findById(id).orElse(null);
    }
    /**
     * Retrieves all auctions by a user's ID.
     *
     * @param usersId the ID of the user whose auctions to retrieve
     * @return a list of all auctions by the user
     */
    public List<Auction> getAuctionByUsersId(long usersId) {
        return auctionRepository.findByUsersId(usersId);
    }
    /**
     * Updates the title of an auction.
     *
     * @param id the ID of the auction to update
     * @param title the new title
     * @return the updated auction, or {@code null} if no auction with the given ID exists
     */
    public Auction updateAuction(long id, String title) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction != null) {
            auction.setTitle(title);
            return auctionRepository.save(auction);
        }
        return null;
    }
    /**
     * Deletes an auction by its ID.
     *
     * @param id the ID of the auction to delete
     */
    public void deleteAuction(long id) {
        auctionRepository.deleteById(id);
    }

    /**
     * Deletes all auctions by a user's ID.
     *
     * @param usersId the ID of the user whose auctions to delete
     */
    @Transactional
    public void deleteAuctionByUsersId(long usersId) {
        auctionRepository.deleteByUsersId(usersId);
    }

}
