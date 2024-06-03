package org.example.pa_project.services;


import jakarta.transaction.Transactional;
import org.example.pa_project.entities.Auction;
import org.example.pa_project.repositories.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<Auction> getAuctions() {
        return auctionRepository.findAll();
    }

    public Auction addAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public Auction getAuction(long id) {
        return auctionRepository.findById(id).orElse(null);
    }

    public List<Auction> getAuctionByUsersId(long usersId) {
        return auctionRepository.findByUsersId(usersId);
    }

    public Auction updateAuction(long id, String title) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction != null) {
            auction.setTitle(title);
            return auctionRepository.save(auction);
        }
        return null;
    }

    public void deleteAuction(long id) {
        auctionRepository.deleteById(id);
    }


    @Transactional
    public void deleteAuctionByUsersId(long usersId) {
        auctionRepository.deleteByUsersId(usersId);
    }

}
