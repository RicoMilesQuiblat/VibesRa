package com.NeuralN.VibesRa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelRoom hotel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
