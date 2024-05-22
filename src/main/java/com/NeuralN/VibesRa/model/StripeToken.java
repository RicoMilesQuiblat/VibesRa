package com.NeuralN.VibesRa.model;

import lombok.Data;

@Data
public class StripeToken {
    private String token;
    private String username;
    private boolean success;
}
