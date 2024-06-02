package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.StripeChargeDTO;
import com.NeuralN.VibesRa.dto.StripeCheckoutDTO;
import com.NeuralN.VibesRa.dto.StripeTokenDTO;
import com.NeuralN.VibesRa.model.Booking;
import com.NeuralN.VibesRa.model.PaymentHistory;
import com.NeuralN.VibesRa.repository.BookingRepository;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import com.NeuralN.VibesRa.repository.PaymentHistoryRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.TokenCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Bean
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Product createProduct(String name, String description, String coverImage) {
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(name)
                        .setDescription(description)
                        .addImage(coverImage)
                        .build();
        try {
            System.out.println("Creating product: " + name + " - " + description);
            return Product.create(params);
        } catch (StripeException e) {
            System.err.println("StripeService (createProduct): " + e);
            throw new RuntimeException("Failed to create product: " + e.getMessage());
        }
    }


    public Price createPrice(StripeCheckoutDTO stripeCheckoutDTO) {
        Product product = createProduct(stripeCheckoutDTO.getName(), stripeCheckoutDTO.getDescription(), stripeCheckoutDTO.getCoverImage());
        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setProduct(product.getId())
                        .setUnitAmount((long) stripeCheckoutDTO.getTotalPrice() * 100)
                        .setCurrency("php")
                        .build();

        try {
            System.out.println("Creating price: " + product.getId() + params.getUnitAmount() + " " + params.getCurrency());
            return Price.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create price: " + e.getMessage());
        }
    }

    public StripeChargeDTO createBillingPortalSession(StripeCheckoutDTO stripeCheckoutDTO) {
        try {
            Price price = createPrice(stripeCheckoutDTO);
            if (stripeCheckoutDTO.getHotelId() == null || stripeCheckoutDTO.getUserId() == null) {
                throw new IllegalArgumentException("Price ID cannot be null or empty.");
            }

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/bookings")
                    .setCancelUrl("http://localhost:3000/hotel/" + stripeCheckoutDTO.getHotelId())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPrice(price.getId())
                                    .setQuantity(1L)
                                    .build()
                    ).build();
            System.out.println("Creating session: " + params.getMode() + " " + params.getSuccessUrl() + " " + params.getCancelUrl());
            Session session = Session.create(params);

            StripeChargeDTO stripeChargeDTO = new StripeChargeDTO();
            stripeChargeDTO.setSessionId(session.getId());
            stripeChargeDTO.setSuccess(true);
            stripeChargeDTO.setMessage("Session created successfully.");
            stripeChargeDTO.setUrl(session.getUrl());
            stripeChargeDTO.setAmount(stripeCheckoutDTO.getTotalPrice());
            stripeChargeDTO.setPaymentStatus(session.getStatus());
            System.out.println(stripeChargeDTO.getMessage());
            System.out.println(session.getId());
            System.out.println(session.getUrl());

            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setAmount(stripeCheckoutDTO.getTotalPrice());
            paymentHistory.setSuccess(Objects.equals(session.getStatus(), "complete"));
            paymentHistory.setMessage(Objects.equals(session.getStatus(), "open") ? "Session created successfully." : "Session creation failed.");
            paymentHistory.setStatus(session.getStatus());
            paymentHistory.setStripeToken(session.getId());
            paymentHistory.setRoom(hotelRoomRepository.findById(stripeCheckoutDTO.getHotelId()).orElse(null));
            paymentHistory.setUser(userRepository.findById(stripeCheckoutDTO.getUserId()).orElse(null));
            paymentHistoryRepository.save(paymentHistory);

            Booking booking = new Booking();
            booking.setBookingStatus(Objects.equals(paymentHistory.getStatus(), "open") ? "PENDING" : "CONFIRMED");
            booking.setCheckInDate(stripeCheckoutDTO.getStartDate());
            booking.setCheckOutDate(stripeCheckoutDTO.getEndDate());
            booking.setHotel(hotelRoomRepository.findById(stripeCheckoutDTO.getHotelId()).orElse(null));
            booking.setUser(userRepository.findById(stripeCheckoutDTO.getUserId()).orElse(null));
            booking.setPaymentHistory(paymentHistory);
            bookingRepository.save(booking);
            return stripeChargeDTO;
        } catch (StripeException e) {
            System.err.println("StripeService (createBillingPortalSession): " + e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String checkSessionStatus(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            return session.getStatus();
        } catch (StripeException e) {
            System.err.println("StripeService (checkStripeSession): " + e);
            throw new RuntimeException(e.getMessage());
        }
    }





}
