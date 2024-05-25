package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.service.CloudinaryService;
import com.NeuralN.VibesRa.service.HotelRoomService;
import com.NeuralN.VibesRa.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageService imageService;

    @Autowired
    HotelRoomService hotelRoomService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Image>> getAllImages(){
        List<Image> list = imageService.getAllImages();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile, @RequestParam Long id) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image not found!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        HotelRoom hotelRoom = hotelRoomService.getById(id);

        Image image = new Image();
        image.setImageId((String) result.get("public_id"));
        image.setUrl((String) result.get("url"));
        image.setHotel(hotelRoom);

        imageService.saveImage(image);
        return new ResponseEntity<>("image ajoutée avec succès ! ", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        Optional<Image> imageOptional = Optional.ofNullable(imageService.getImageById(id));
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("Image does not exist!", HttpStatus.NOT_FOUND);
        }
        Image image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.deleteImageById(id);
        return new ResponseEntity<>("Image deleted successfully!", HttpStatus.OK);
    }

}
