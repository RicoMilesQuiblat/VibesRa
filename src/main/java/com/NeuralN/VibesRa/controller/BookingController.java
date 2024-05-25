package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.dto.ImageUploadDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.repository.ImageRepository;
import com.NeuralN.VibesRa.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{bookingID}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingID) {
        Booking booking = bookingService.getBookingById(bookingID);
        if (booking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            Booking savedBooking = bookingService.saveBooking(bookingDTO);
            if (savedBooking == null) {
                throw new Exception("Failed to save booking");
            }
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (SQLIntegrityConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Booking> updateBooking(@RequestBody Long bookingID, @RequestBody BookingDTO bookingDTO) {
        Booking updatedBooking = bookingService.updateBooking(bookingID, bookingDTO);
        if (updatedBooking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingID}/delete")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingID) {
        bookingService.deleteBooking(bookingID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
