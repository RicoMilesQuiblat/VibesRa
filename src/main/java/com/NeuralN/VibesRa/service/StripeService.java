package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.StripeChargeDTO;
import com.NeuralN.VibesRa.dto.StripeTokenDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.TokenCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Bean
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


    public StripeTokenDTO createCardToken(StripeTokenDTO stripeTokenDTO) {
        try {
            TokenCreateParams params =
                    TokenCreateParams.builder()
                            .setCard(
                                    TokenCreateParams.Card.builder()
                                            .setNumber("6200000000000005")
                                            .setExpMonth("6")
                                            .setExpYear("2034")
                                            .setCvc("314")
                                            .build()
                            )
                            .build();

            Token token = Token.create(params);

            if (token != null && token.getId() != null) {
                stripeTokenDTO.setToken(token.getId());
                stripeTokenDTO.setSuccess(true);
            } else {
                stripeTokenDTO.setSuccess(false);
            }
            return stripeTokenDTO;
        } catch (StripeException e) {
            System.err.println("StripeService (createCardToken): " + e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public StripeChargeDTO charge(StripeChargeDTO chargeRequest) {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (chargeRequest.getAmount() * 100));
            chargeParams.put("currency", "php");
            chargeParams.put("description", "Payment for id " + chargeRequest.getAdditionalInfo().getOrDefault("ID_TAG", ""));
            chargeParams.put("source", chargeRequest.getStripeToken());
            Map<String, String> initialMetadata = new HashMap<>();
            initialMetadata.put("id", chargeRequest.getChargeId());
            initialMetadata.putAll(chargeRequest.getAdditionalInfo());
            chargeParams.put("metadata", initialMetadata);
            Charge charge = Charge.create(chargeParams);
            chargeRequest.setMessage(charge.getOutcome().getSellerMessage());

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(chargeRequest.getAmount().longValue() * 100)
                            .setCurrency("php")
                            .setDescription("Payment for id " + chargeRequest.getAdditionalInfo().getOrDefault("ID_TAG", ""))
                            .putMetadata("id", chargeRequest.getChargeId())
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .putMetadata("id", chargeRequest.getChargeId())
                            .putAllMetadata(chargeRequest.getAdditionalInfo())
                            .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            paymentIntent.setSource(chargeRequest.getStripeToken());

            if(charge.getPaid()) {
                chargeRequest.setSuccess(true);
                chargeRequest.setChargeId(charge.getId());
            }
            return chargeRequest;
        } catch (StripeException e) {
            System.err.println("StripeService (charge)"+ e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
