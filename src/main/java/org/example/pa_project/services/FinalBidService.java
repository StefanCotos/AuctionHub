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

@Service
public class FinalBidService {
    private final FinalBidRepository finalBidRepository;
    private final WebSocketService webSocketService;
    private final UserService userService;

    @Autowired
    public FinalBidService(FinalBidRepository finalBidRepository, WebSocketService webSocketService, UserService userService) {
        this.finalBidRepository = finalBidRepository;
        this.webSocketService = webSocketService;
        this.userService = userService;
    }

    public List<FinalBid> getFinalBids() {
        return finalBidRepository.findAll();
    }

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

    public FinalBid getFinalBid(long id) {
        return finalBidRepository.findById(id).orElse(null);
    }

    public FinalBid getFinalBidByAuctionsId(long auctionsId) {
        return finalBidRepository.findByAuctionsId(auctionsId);
    }

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

    public void deleteFinalBid(long id) {
        finalBidRepository.deleteById(id);
    }

    @Transactional
    public void deleteFinalBidByUsersId(long usersId) {
        finalBidRepository.deleteByUsersId(usersId);
    }

    @Transactional
    public void deleteFinalBidAuctionsId(long auctionsId) {
        finalBidRepository.deleteByAuctionsId(auctionsId);
    }
}
