package org.example.pa_project.repositories;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.FinalBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing FinalBid entities.
 */
public interface FinalBidRepository extends JpaRepository<FinalBid, Long> {

    /**
     * Finds a FinalBid by the associated auction's ID.
     *
     * @param auctionsId the ID of the auction
     * @return the FinalBid associated with the specified auction ID, or null if not found
     */
    FinalBid findByAuctionsId(long auctionsId);

    /**
     * Deletes FinalBid entities by the associated auction's ID.
     *
     * @param auctionsId the ID of the auction
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM FinalBid b WHERE b.auctionsId = :auctionsId")
    void deleteByAuctionsId(@Param("auctionsId") long auctionsId);

    /**
     * Deletes FinalBid entities by the associated user's ID.
     *
     * @param usersId the ID of the user
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM FinalBid b WHERE b.usersId = :usersId")
    void deleteByUsersId(@Param("usersId") long usersId);
}
