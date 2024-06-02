package org.example.pa_project.repositories;

import org.example.pa_project.entities.FinalBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalBidRepository extends JpaRepository<FinalBid, Long> {
    FinalBid findByAuctionsId(long auctionsId);
}
