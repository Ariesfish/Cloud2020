package xyz.ariesfish.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.ariesfish.springcloud.entities.Payment;

@Mapper // 推荐用此注解，而不是Repository
public interface PaymentDao {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
