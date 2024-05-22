package com.NeuralN.VibesRa.model;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.reflection.qual.GetConstructor;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import javax.management.ConstructorParameters;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelRoom hotel;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentHistory paymentHistory;

    private String checkInDate;
    private String checkOutDate;
    private int noOfRooms;
    private int noOfAdults;
    private int noOfChildren;
    private String bookingStatus;
    private int totalGuests;

    {
        this.bookingStatus = "PENDING";
        this.totalGuests = this.noOfAdults + this.noOfChildren;
    }

}
