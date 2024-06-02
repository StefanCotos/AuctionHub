package org.example.pa_project.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "final_bids", schema = "pa_project", catalog = "")
public class FinalBid {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "price")
    private int price;
    @Basic
    @Column(name = "auctions_id")
    private int auctionsId;
    @Basic
    @Column(name = "users_id")
    private int usersId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAuctionsId() {
        return auctionsId;
    }

    public void setAuctionsId(int auctionsId) {
        this.auctionsId = auctionsId;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FinalBid finalBid = (FinalBid) object;
        return id == finalBid.id && price == finalBid.price && auctionsId == finalBid.auctionsId && usersId == finalBid.usersId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, auctionsId, usersId);
    }
}
