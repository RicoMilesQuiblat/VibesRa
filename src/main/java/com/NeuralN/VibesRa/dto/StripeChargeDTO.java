package com.NeuralN.VibesRa.dto;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class StripeChargeDTO {
    private String stripeToken;
    private String username;
    private Double amount;
    private Boolean success;
    private String message;
    public String chargeId;
    private Map<String, String> additionalInfo = new HashMap<>();
}
