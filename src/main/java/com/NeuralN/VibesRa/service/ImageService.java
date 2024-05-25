package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }

    public void deleteAllImagesByHotelID(Long hotelID) {
        imageRepository.deleteByHotelId(hotelID);
    }

    public List<Image> getImagesByHotelID(Long hotelID) {
        return imageRepository.findByHotelId(hotelID);
    }

    public List<Image> saveImages(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public boolean existsById(Long id) {
        return imageRepository.existsById(id);
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
