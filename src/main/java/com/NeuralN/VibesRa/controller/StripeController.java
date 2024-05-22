package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.dto.StripeChargeDTO;
import com.NeuralN.VibesRa.dto.StripeTokenDTO;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.service.PaymentHistoryService;
import com.NeuralN.VibesRa.service.StripeService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
@AllArgsConstructor
public class StripeController {

    @Autowired
    private final StripeService stripeService;

    @Autowired
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping("/card/token")
    @ResponseBody
    public ResponseEntity<StripeTokenDTO> createToken(@RequestBody StripeTokenDTO stripeTokenDTO) {
        StripeTokenDTO response = stripeService.createCardToken(stripeTokenDTO);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/charge")
    @ResponseBody
    public StripeChargeDTO charge(StripeChargeDTO chargeRequest, HotelRoom room, User user) throws StripeException {
        paymentHistoryService.savePaymentHistory(chargeRequest, room, user);
        return ResponseEntity.ok(stripeService.charge(chargeRequest)).getBody();
    }
}
