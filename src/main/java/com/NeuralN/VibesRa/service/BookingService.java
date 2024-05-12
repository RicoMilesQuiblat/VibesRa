package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(int bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return booking.orElse(null);
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(int bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public Booking updateBooking(int bookingId, Booking updatedBooking) {
        Booking existingBooking = getBookingById(bookingId);
        if (existingBooking == null) {
            return null; // or throw exception
        }
        updatedBooking.setBookingID(bookingId);
        return bookingRepository.save(updatedBooking);
    }
}
