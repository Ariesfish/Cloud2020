package xyz.ariesfish.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    /**
     * Inject RestTemplate
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced // 开启服务提供者的LB规则释放负载均衡能力, 默认为轮询
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
