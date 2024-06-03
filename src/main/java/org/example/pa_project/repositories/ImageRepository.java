package org.example.pa_project.repositories;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByAuctionsId(long auctionsId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.auctionsId = :auctionsId")
    void deleteByAuctionsId(@Param("auctionsId") long auctionsId);

}
