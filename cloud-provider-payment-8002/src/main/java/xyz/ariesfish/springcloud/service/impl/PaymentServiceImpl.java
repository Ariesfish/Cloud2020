package xyz.ariesfish.springcloud.service.impl;

import org.springframework.stereotype.Service;
import xyz.ariesfish.springcloud.dao.PaymentDao;
import xyz.ariesfish.springcloud.entities.Payment;
import xyz.ariesfish.springcloud.service.PaymentService;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
