package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Booking savedBooking = bookingService.saveBooking(bookingDTO);
        if (savedBooking == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
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
