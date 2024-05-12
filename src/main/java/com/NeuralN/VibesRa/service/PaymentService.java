package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Payment;
import com.NeuralN.VibesRa.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(int paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.orElse(null);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(int paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    public Payment updatePayment(int paymentId, Payment updatedPayment) {
        Payment existingPayment = getPaymentById(paymentId);
        if (existingPayment == null) {
            return null; // or throw exception
        }
        updatedPayment.setPaymentId(paymentId);
        return paymentRepository.save(updatedPayment);
    }
}
