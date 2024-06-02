package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.StripeChargeDTO;
import com.NeuralN.VibesRa.dto.StripeCheckoutDTO;
import com.NeuralN.VibesRa.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeChargeDTO> createCheckoutSession(@RequestBody StripeCheckoutDTO stripeCheckoutDTO) {
        return ResponseEntity.ok(stripeService.createBillingPortalSession(stripeCheckoutDTO));
    }

    @GetMapping("/status/{sessionId}")
    public ResponseEntity<String> getCheckSessionStatus(@PathVariable String sessionId) {
        return ResponseEntity.ok(stripeService.checkSessionStatus(sessionId));
    }
}
