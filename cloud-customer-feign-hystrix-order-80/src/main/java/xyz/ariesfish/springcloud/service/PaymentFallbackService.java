package xyz.ariesfish.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "****** PaymentFallbackService falls back paymentInfo_OK!";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "****** PaymentFallbackService falls back paymentInfo_TimeOut!";
    }
}
