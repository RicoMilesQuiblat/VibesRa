package com.NeuralN.VibesRa.service;


import com.NeuralN.VibesRa.dto.StripeChargeDTO;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.PaymentHistory;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import com.NeuralN.VibesRa.repository.PaymentHistoryRepository;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoryService {
    PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistory savePaymentHistory(StripeChargeDTO stripeChargeDTO, HotelRoom room, User user) throws StripeException {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setAmount(stripeChargeDTO.getAmount());
        paymentHistory.setSuccess(stripeChargeDTO.getSuccess());
        paymentHistory.setMessage(stripeChargeDTO.getMessage());
        paymentHistory.setRoom(room);
        paymentHistory.setUser(user);
        return paymentHistoryRepository.save(paymentHistory);
    }

}


