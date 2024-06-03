package org.example.pa_project.repositories;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.FinalBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinalBidRepository extends JpaRepository<FinalBid, Long> {
    FinalBid findByAuctionsId(long auctionsId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FinalBid b WHERE b.auctionsId = :auctionsId")
    void deleteByAuctionsId(@Param("auctionsId") long auctionsId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FinalBid b WHERE b.usersId = :usersId")
    void deleteByUsersId(@Param("usersId") long usersId);
}
