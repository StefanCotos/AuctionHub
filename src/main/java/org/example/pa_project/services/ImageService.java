package org.example.pa_project.services;

import org.example.pa_project.entities.Image;
import org.example.pa_project.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    public Image getImage(long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public List<Image> getImagesByAuctionsId(long auctionId) {
        return imageRepository.findByAuctionsId(auctionId);
    }

    public Image updateImage(long id, String extension) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image != null) {
            image.setExtension(extension);
            return imageRepository.save(image);
        }
        return null;
    }

    public void deleteImage(long id) {
        imageRepository.deleteById(id);
    }
}
