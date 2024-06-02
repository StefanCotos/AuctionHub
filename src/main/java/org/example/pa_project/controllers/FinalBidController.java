package org.example.pa_project.controllers;

import org.example.pa_project.entities.FinalBid;
import org.example.pa_project.services.FinalBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
public class FinalBidController {
    private final FinalBidService finalBidService;

    @Autowired
    public FinalBidController(FinalBidService finalBidService) {
        this.finalBidService = finalBidService;
    }

    @GetMapping()
    public List<FinalBid> getBids() {
        return finalBidService.getFinalBids();
    }

    @GetMapping("/{id}")
    public FinalBid getBid(@PathVariable int id) {
        return finalBidService.getFinalBid(id);
    }

    @GetMapping("/auctions_id/{auctionsId}")
    public FinalBid getBidByAuctionId(@PathVariable int auctionsId) {
        return finalBidService.getFinalBidByAuctionId(auctionsId);
    }

    @PostMapping()
    public FinalBid addBid(@RequestBody FinalBid finalBid) {
        return finalBidService.addFinalBid(finalBid);
    }

    @PutMapping("/{id}")
    public FinalBid updateBid(@PathVariable int id, @RequestBody FinalBid finalBid) {
        return finalBidService.updateFinalBid(id, finalBid.getAuctionsId(), finalBid.getUsersId(), finalBid.getPrice());
    }

    @DeleteMapping("/{id}")
    public void deleteBid(@PathVariable int id) {
        finalBidService.deleteFinalBid(id);
    }
}
