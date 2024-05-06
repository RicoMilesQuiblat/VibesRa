package com.NeuralN.VibesRa.controller;


import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Room;
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

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(Booking booking){
        Booking newBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Booking> getBookingByID(@RequestParam int bookingid){
        Booking booking = bookingService.getBookingByID(bookingid);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Booking>> getBookingByUserID(@RequestParam int userid){
        List<Booking> bookings = bookingService.getBookingByUserID(userid);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Booking> getBookingByRoomID(@RequestParam int roomid){
        Booking booking = bookingService.getBookingByRoomID(roomid);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Booking> deleteBooking(@RequestParam int bookingid){
        Booking booking = bookingService.deleteBooking(bookingid);
        return  new ResponseEntity<>(booking, HttpStatus.OK);
    }

}
