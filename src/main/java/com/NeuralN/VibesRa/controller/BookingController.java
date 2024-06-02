package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.dto.BookingStatusUpdateDTO;
import com.NeuralN.VibesRa.dto.ImageUploadDTO;
import com.NeuralN.VibesRa.dto.UserBookingDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.BookingRepository;
import com.NeuralN.VibesRa.repository.ImageRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import com.NeuralN.VibesRa.service.BookingService;
import com.NeuralN.VibesRa.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
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
    public ResponseEntity<Booking> updateBooking(@RequestBody UUID bookingId, @RequestBody BookingDTO bookingDTO) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, bookingDTO);
        if (updatedBooking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}/delete")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/user")
    public ResponseEntity<List<UserBookingDTO>> getBookingsByUser(@RequestParam UUID id) {
        List<UserBookingDTO> bookings = bookingService.getBookingsByUserId(id);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/hotel")
    public ResponseEntity<List<BookingDTO>> getBookingsByHotelId(@RequestParam UUID hotelId) {
        List<Booking> bookings = bookingService.getBookingsByHotelId(hotelId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookingService.getBookingDTOs(bookings));
    }

    @PutMapping("/update-status")
    public ResponseEntity<Booking> updateBookingStatus(@RequestParam UUID bookingId) {

        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookingService.updateStatus(booking), HttpStatus.OK);
    }
}
