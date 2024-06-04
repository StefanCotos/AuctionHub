package org.example.pa_project.services;

import jakarta.transaction.Transactional;
import org.example.pa_project.entities.Image;
import org.example.pa_project.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing images.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * Constructs a new ImageService with the specified ImageRepository.
     *
     * @param imageRepository the image repository
     */
    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Retrieves all images.
     *
     * @return a list of all images
     */
    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    /**
     * Adds a new image.
     *
     * @param image the image to add
     * @return the added image
     */
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    /**
     * Retrieves an image by its ID.
     *
     * @param id the ID of the image
     * @return the image with the specified ID, or null if not found
     */
    public Image getImage(long id) {
        return imageRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves images by the ID of the auction they are associated with.
     *
     * @param auctionId the ID of the auction
     * @return a list of images associated with the specified auction ID
     */
    public List<Image> getImagesByAuctionsId(long auctionId) {
        return imageRepository.findByAuctionsId(auctionId);
    }

    /**
     * Updates the extension of an image.
     *
     * @param id        the ID of the image to update
     * @param extension the new extension
     * @return the updated image, or null if not found
     */
    public Image updateImage(long id, String extension) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image != null) {
            image.setExtension(extension);
            return imageRepository.save(image);
        }
        return null;
    }

    /**
     * Deletes an image by its ID.
     *
     * @param id the ID of the image to delete
     */
    public void deleteImage(long id) {
        imageRepository.deleteById(id);
    }

    /**
     * Deletes images associated with a specified auction ID.
     *
     * @param auctionsId the ID of the auction whose images to delete
     */
    @Transactional
    public void deleteImageByAuctionsId(long auctionsId) {
        imageRepository.deleteByAuctionsId(auctionsId);
    }
}
