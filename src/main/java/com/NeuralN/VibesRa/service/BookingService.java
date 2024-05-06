package com.NeuralN.VibesRa.service;


import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.Room;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.BookingRepository;
import com.NeuralN.VibesRa.repository.RoomRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;


    public Booking createBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    public Booking getBookingByID(int bookingid){
        return bookingRepository.findById(bookingid).get();
    }
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingByUserID(int userid){
        User user = userRepository.findById(userid).get();
        return bookingRepository.findByUser(user);
    }

    public Booking getBookingByRoomID(int roomid){
        return bookingRepository.findByRoom(roomRepository.findById(roomid).get());
    }

    public Booking deleteBooking(int bookingid){
        Optional<Booking> booking = bookingRepository.findById(bookingid);
        if(booking.isPresent()){
            bookingRepository.delete(booking.get());
            return booking.get();
        }
        return null;
    }



}
