package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}