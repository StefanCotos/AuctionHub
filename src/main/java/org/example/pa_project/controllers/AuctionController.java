package org.example.pa_project.controllers;

import org.example.pa_project.entities.Auction;
import org.example.pa_project.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {
    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping()
    public List<Auction> getAuctions() {
        return auctionService.getAuctions();
    }

    @GetMapping("/{id}")
    public Auction getAuction(@PathVariable int id) {
        return auctionService.getAuction(id);
    }

    @GetMapping("/users_id/{usersId}")
    public List<Auction> getAuctionByUsersId(@PathVariable int usersId) {
        return auctionService.getAuctionByUsersId(usersId);
    }

    @PostMapping()
    public ResponseEntity<Auction> addAuction(@RequestBody Auction auction) {
        Auction newAuction = auctionService.addAuction(auction);
        return ResponseEntity.ok(newAuction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuction(@PathVariable int id, @RequestBody Auction auction) {
        Auction updatedAuction = auctionService.updateAuction(id, auction.getTitle());
        return ResponseEntity.ok("Auction updated: " + updatedAuction.getTitle());
    }

    @DeleteMapping("/{id}")
    public void deleteAuction(@PathVariable int id) {
        auctionService.deleteAuction(id);
    }

    @DeleteMapping("/users_id/{usersId}")
    public void deleteAuctionByUsersId(@PathVariable int usersId) {
        auctionService.deleteAuctionByUsersId(usersId);
    }

}
