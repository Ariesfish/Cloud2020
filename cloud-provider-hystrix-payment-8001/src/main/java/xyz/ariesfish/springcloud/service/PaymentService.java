package xyz.ariesfish.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        return "Thread Pool: " + Thread.currentThread().getName() +
                " paymentInfo_OK, id: " + id + "\t" + "successful!";
    }

    // 服务降级
    /**
     * 超时访问
     *   HystrixCommand:一旦调用服务方法失败并抛出了错误信息后,会自动调用@HystrixCommand标注好的 fallback method 调用类中的指定方法
     *   execution.isolation.thread.timeoutInMilliseconds:线程超时时间3秒钟
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "payment_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int sleepTime = 5;
        try {
            // Sleep for 5 seconds
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread Pool: " + Thread.currentThread().getName() +
                " paymentInfo_TimeOut, id: " + id + "\t" + " time out(seconds): " + sleepTime;
    }

    // 服务熔断
    /**
     * Fallback Method
     * @param id
     * @return
     */
    public String payment_TimeOutHandler(Integer id) {
        return "Thread Pool: " + Thread.currentThread().getName() +
                " system is busy, please try later. id: " + id + "\t" + "failed!";
    }

    /**
     * 在10秒窗口期中10次请求有6次是请求失败的,断路器将起作用
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到后跳闸
    }
    )
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("id can not be minus!");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "call OK, UUID: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "Id can not be minus, please try again. id: " + id;
    }
}
