package cn.gavin.cafehouse.customer;

import cn.gavin.cafehouse.customer.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy
public class CustomerServiceApplication {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.custom()
                .setConnectionTimeToLive(30, TimeUnit.SECONDS)
                .evictIdleConnections(30, TimeUnit.SECONDS)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .disableAutomaticRetries()
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
                .build();
    }

//    @Bean
//    public HttpComponentsClientHttpRequestFactory requestFactory() {
//        PoolingHttpClientConnectionManager connectionManager =
//                new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
//        connectionManager.setMaxTotal(200);
//        connectionManager.setDefaultMaxPerRoute(20);
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setConnectionManager(connectionManager)
//                .evictIdleConnections(30, TimeUnit.SECONDS)
//                .disableAutomaticRetries()
//                // 有 Keep-Alive 认里面的值，没有的话永久有效
//                //.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
//                // 换成自定义的
//                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory(httpClient);
//
//        return requestFactory;
//    }
//
//    @LoadBalanced
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder
//                .setConnectTimeout(Duration.ofMillis(1000))
//                .setReadTimeout(Duration.ofMillis(5000))
//                .requestFactory(this::requestFactory)
//                .build();
//    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
        return builder -> {
            builder.indentOutput(true);
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }
}
