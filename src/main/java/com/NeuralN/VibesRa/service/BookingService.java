package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.BookingDTO;
import com.NeuralN.VibesRa.dto.BookingStatusUpdateDTO;
import com.NeuralN.VibesRa.dto.ImageUploadDTO;
import com.NeuralN.VibesRa.dto.UserBookingDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.Image;
import com.NeuralN.VibesRa.model.PaymentHistory;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.*;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(UUID bookingID) {
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
            booking.setBookingStatus(paymentHistory.getSuccess() ? "Confirmed" : "Cancelled");
            return bookingRepository.save(booking);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void deleteBooking(UUID bookingID) {
        bookingRepository.deleteById(bookingID);
    }



    public Booking updateBooking(UUID bookingID, BookingDTO bookingDTO) {
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

    public List<Booking> getBookingsByHotelId(UUID hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }


    public List<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream().map(booking -> {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setBookingID(booking.getId());
            bookingDTO.setCheckInDate(booking.getCheckInDate());
            bookingDTO.setCheckOutDate(booking.getCheckOutDate());
            bookingDTO.setHotelID(booking.getHotel().getId());
            bookingDTO.setUserID(booking.getUser().getId());
            bookingDTO.setBookingStatus(booking.getBookingStatus());
            bookingDTO.setPaymentHistoryID(booking.getPaymentHistory().getId());
            return bookingDTO;
        }).toList();
    }

    public Booking updateStatus(Booking booking) {
        booking.setBookingStatus(booking.getPaymentHistory().getSuccess() ? "Confirmed" : "Cancelled");
        return bookingRepository.save(booking);
    }

    public List<UserBookingDTO> getBookingsByUserId(UUID userId) {
        List<Booking> bookings = bookingRepository.getBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            return List.of();
        }
        return updateBookingStatuses(bookings);
    }

    public List<UserBookingDTO> updateBookingStatuses(List<Booking> bookings) {
        
        return bookings.stream()
                .filter(Objects::nonNull)
                .filter(booking -> !Objects.equals(booking.getBookingStatus(), "COMPLETED"))
                .map(booking -> {
                    if (!Objects.equals(booking.getBookingStatus(), "CONFIRMED")) {
                        PaymentHistory paymentHistory = paymentHistoryRepository.getReferenceById(booking.getPaymentHistory().getId());
                        String status = paymentHistory.getStatus();
                        if (status.equals("open")) {
                            try {
                                Session session = Session.retrieve(paymentHistory.getStripeToken());
                                booking.setBookingStatus(session.getStatus().equals("complete") ? "CONFIRMED" : "CANCELLED");
                                System.out.println("Booking status updated to " + session.getStatus());
                                bookingRepository.save(booking);
                            } catch (StripeException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    return getUserBookingDTO(booking);
                })
                .collect(Collectors.toList());
    }

    private static UserBookingDTO getUserBookingDTO(Booking booking) {
        UserBookingDTO userBookingDTO = new UserBookingDTO();
        userBookingDTO.setId(booking.getId());
        userBookingDTO.setCheckInDate(booking.getCheckInDate());
        userBookingDTO.setCheckOutDate(booking.getCheckOutDate());
        userBookingDTO.setBookingStatus(booking.getBookingStatus());
        userBookingDTO.setCreatedAt(booking.getCreatedAt());
        userBookingDTO.setHotelRoom(booking.getHotel());
        userBookingDTO.setUser(booking.getUser());
        userBookingDTO.setPaymentHistory(booking.getPaymentHistory());
        return userBookingDTO;
    }
}
