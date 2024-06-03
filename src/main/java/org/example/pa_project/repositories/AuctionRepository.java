package org.example.pa_project.repositories;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByUsersId(long usersId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Auction a WHERE a.usersId = :usersId")
    void deleteByUsersId(@Param("usersId") long usersId);
}
