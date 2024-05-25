package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.dto.ImageUploadDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.model.PaymentHistory;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private ImageRepository imageRepository;


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long bookingID) {
        return bookingRepository.findById(bookingID).orElse(null);
    }

    public Booking saveBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = new Booking();

            User user = userRepository.findById(bookingDTO.getUserID()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            HotelRoom hotel = hotelRoomRepository.findById(bookingDTO.getHotelID()).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
            PaymentHistory paymentHistory = paymentHistoryRepository.findById(bookingDTO.getPaymentHistoryID()).orElseThrow(() -> new IllegalArgumentException("Payment history not found"));

            if (paymentHistory == null) {
                throw new IllegalArgumentException("Payment history does not exist");
            }

            if (paymentHistory.getUser().getId() != user.getId()) {
                throw new IllegalArgumentException("Payment history does not belong to the user");
            }

            if (user == null || hotel == null) {
                throw new IllegalArgumentException("User or hotel does not exist");
            }

            booking.setUser(user);
            booking.setHotel(hotel);
            booking.setCheckOutDate(bookingDTO.getCheckOutDate());
            booking.setCheckInDate(bookingDTO.getCheckInDate());
            booking.setNoOfAdults(bookingDTO.getNoOfAdults());
            booking.setNoOfChildren(bookingDTO.getNoOfChildren());
            booking.setNoOfRooms(bookingDTO.getNoOfRooms());
            booking.setBookingStatus(paymentHistory.getSuccess() ? "Confirmed" : "Cancelled");


            return bookingRepository.save(booking);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void deleteBooking(Long bookingID) {
        bookingRepository.deleteById(bookingID);
    }


    public Booking updateBooking(Long bookingID, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingID).orElse(null);

        if (booking == null) {
            return null;
        }

        User user = userRepository.findById(bookingDTO.getBookingID()).orElse(null);

        if (user == null) {
            return null;
        }

        HotelRoom hotel = hotelRoomRepository.findById(bookingDTO.getHotelID()).orElse(null);

        if (hotel == null) {
            return null;
        }

        booking.setUser(user);
        booking.setHotel(hotel);

        return bookingRepository.save(booking);
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
