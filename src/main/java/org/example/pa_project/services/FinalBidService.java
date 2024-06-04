package org.example.pa_project.services;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.FinalBid;
import org.example.pa_project.entities.User;
import org.example.pa_project.repositories.FinalBidRepository;
import org.example.pa_project.webSocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service class for managing final bids.
 */
@Service
public class FinalBidService {
    private final FinalBidRepository finalBidRepository;
    private final WebSocketService webSocketService;
    private final UserService userService;
    /**
     * Constructs a {@code FinalBidService} with the specified final bid repository, web socket service, and user service.
     *
     * @param finalBidRepository the final bid repository
     * @param webSocketService the web socket service
     * @param userService the user service
     */
    @Autowired
    public FinalBidService(FinalBidRepository finalBidRepository, WebSocketService webSocketService, UserService userService) {
        this.finalBidRepository = finalBidRepository;
        this.webSocketService = webSocketService;
        this.userService = userService;
    }
    /**
     * Retrieves all final bids.
     *
     * @return a list of all final bids
     */
    public List<FinalBid> getFinalBids() {
        return finalBidRepository.findAll();
    }
    /**
     * Adds a new final bid.
     *
     * @param finalBid the final bid to add
     * @return the added final bid
     */
    public FinalBid addFinalBid(FinalBid finalBid) {
        User user;
        FinalBid existingBid = finalBidRepository.findByAuctionsId(finalBid.getAuctionsId());
        if (existingBid != null) {
            if (existingBid.getPrice() >= finalBid.getPrice()) {
                user = userService.getUser(existingBid.getUsersId());
                webSocketService.notifyClients(existingBid.getAuctionsId() + "_Current bid: " + existingBid.getPrice() + "$_" + user.getUsername());
                return existingBid;
            }
            finalBidRepository.delete(existingBid);
        }
        user = userService.getUser(finalBid.getUsersId());
        webSocketService.notifyClients(finalBid.getAuctionsId() + "_Current bid: " + finalBid.getPrice() + "$_" + user.getUsername());
        return finalBidRepository.save(finalBid);
    }
    /**
     * Retrieves a final bid by its ID.
     *
     * @param id the ID of the final bid to retrieve
     * @return the retrieved final bid, or {@code null} if no final bid with the given ID exists
     */
    public FinalBid getFinalBid(long id) {
        return finalBidRepository.findById(id).orElse(null);
    }
    /**
     * Retrieves a final bid by its auction ID.
     *
     * @param auctionsId the ID of the auction to retrieve the final bid for
     * @return the retrieved final bid, or {@code null} if no final bid for the given auction ID exists
     */
    public FinalBid getFinalBidByAuctionsId(long auctionsId) {
        return finalBidRepository.findByAuctionsId(auctionsId);
    }
    /**
     * Updates a final bid.
     *
     * @param id the ID of the final bid to update
     * @param auctionsId the new auction ID
     * @param userId the new user ID
     * @param price the new price
     * @return the updated final bid, or throws a {@code RuntimeException} if no final bid with the given ID exists
     */
    public FinalBid updateFinalBid(long id, int auctionsId, int userId, int price) {
        Optional<FinalBid> finalBid = finalBidRepository.findById(id);
        if (finalBid.isPresent()) {
            finalBid.get().setAuctionsId(auctionsId);
            finalBid.get().setUsersId(userId);
            finalBid.get().setPrice(price);
            return finalBidRepository.save(finalBid.get());
        } else {
            throw new RuntimeException("FinalBid not found for the id: " + id);
        }
    }
    /**
     * Deletes a final bid by its ID.
     *
     * @param id the ID of the final bid to delete
     */
    public void deleteFinalBid(long id) {
        finalBidRepository.deleteById(id);
    }
    /**
     * Deletes all final bids by a user's ID.
     *
     * @param usersId the ID of the user whose final bids to delete
     */
    @Transactional
    public void deleteFinalBidByUsersId(long usersId) {
        finalBidRepository.deleteByUsersId(usersId);
    }
    /**
     * Deletes all final bids by an auction's ID.
     *
     * @param auctionsId the ID of the auction whose final bids to delete
     */
    @Transactional
    public void deleteFinalBidAuctionsId(long auctionsId) {
        finalBidRepository.deleteByAuctionsId(auctionsId);
    }
}
