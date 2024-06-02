package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public void deleteImageById(UUID id) {
        imageRepository.deleteById(id);
    }

    public void deleteAllImagesByHotelID(UUID hotelID) {
        imageRepository.deleteByHotelId(hotelID);
    }

    public List<Image> getImagesByHotelID(UUID hotelID) {
        return imageRepository.findByHotelId(hotelID);
    }

    public List<Image> saveImages(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public boolean existsById(UUID id) {
        return imageRepository.existsById(id);
    }

    public Image getImageById(UUID id) {
        return imageRepository.findById(id).orElse(null);
    }
}
