package com.NeuralN.VibesRa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.reflection.qual.GetConstructor;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.data.annotation.CreatedDate;

import javax.management.ConstructorParameters;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelRoom hotel;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentHistory paymentHistory;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingStatus;

    @CreatedDate
    private LocalDate createdAt = LocalDate.now();

}
