package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class StripeCheckoutDTO {
    private UUID checkoutId;
    private double totalPrice;
    private String name;
    private String description;
    private String coverImage;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String url;
    private String status;
    private UUID hotelId;
    private UUID userId;
}
