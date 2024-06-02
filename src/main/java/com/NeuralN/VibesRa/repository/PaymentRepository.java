package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentHistory, UUID> {
}