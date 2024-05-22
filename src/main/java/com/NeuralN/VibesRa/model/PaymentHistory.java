package com.NeuralN.VibesRa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "room_id")
    private HotelRoom room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double amount;
    private Boolean success;
    private String message;
}
