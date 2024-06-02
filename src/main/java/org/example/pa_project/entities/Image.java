package org.example.pa_project.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "images", schema = "pa_project", catalog = "")
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "extension")
    private String extension;
    @Basic
    @Column(name = "auctions_id")
    private int auctionsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getAuctionsId() {
        return auctionsId;
    }

    public void setAuctionsId(int auctionsId) {
        this.auctionsId = auctionsId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Image image = (Image) object;
        return id == image.id && auctionsId == image.auctionsId && Objects.equals(extension, image.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, extension, auctionsId);
    }
}
