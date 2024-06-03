package org.example.pa_project.controllers;

import org.example.pa_project.entities.Image;
import org.example.pa_project.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagesDatabase")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping()
    public ResponseEntity<List<Image>> getImages() {
        return ResponseEntity.ok(imageService.getImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable long id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }

    @GetMapping("/auctions_id/{auctionsId}")
    public List<Image> getImagesByAuctionsId(@PathVariable long auctionsId) {
        return imageService.getImagesByAuctionsId(auctionsId);
    }

    @PostMapping()
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        return ResponseEntity.ok(imageService.addImage(image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable long id, @RequestBody Image image) {
        return ResponseEntity.ok(imageService.updateImage(id, image.getExtension()));
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable long id) {
        imageService.deleteImage(id);
    }

    @DeleteMapping("/auctions_id/{auctionsId}")
    public void deleteImageByAuctionsId(@PathVariable long auctionsId) {
        imageService.deleteImageByAuctionsId(auctionsId);
    }
}
