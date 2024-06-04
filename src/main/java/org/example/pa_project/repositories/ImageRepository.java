package org.example.pa_project.repositories;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for managing images.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Finds all images by an auction's ID.
     *
     * @param auctionsId the ID of the auction
     * @return a list of all images for the auction
     */
    List<Image> findByAuctionsId(long auctionsId);
    /**
     * Deletes all images by an auction's ID.
     *
     * @param auctionsId the ID of the auction
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.auctionsId = :auctionsId")
    void deleteByAuctionsId(@Param("auctionsId") long auctionsId);

}
