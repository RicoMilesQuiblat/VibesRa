package com.NeuralN.VibesRa.dto;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class StripeChargeDTO {
    private String sessionId;
    private String username;
    private Double amount;
    private Boolean success;
    private String message;
    public String chargeId;
    private String paymentStatus;
    private String url;
    private Map<String, String> additionalInfo = new HashMap<>();
}
