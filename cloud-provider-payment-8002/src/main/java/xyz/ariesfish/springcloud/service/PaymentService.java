package xyz.ariesfish.springcloud.service;

import xyz.ariesfish.springcloud.entities.Payment;

public interface PaymentService {

    int create(Payment payment);
    Payment getPaymentById(Long id);
}
