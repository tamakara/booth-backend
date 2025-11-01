package com.tamakara.booth.backend.service.order;

import com.tamakara.booth.backend.common.client.ItemClient;
import com.tamakara.booth.backend.common.client.UserClient;
import com.tamakara.booth.backend.common.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {ItemClient.class, UserClient.class},
        defaultConfiguration = FeignConfig.class
)
@MapperScan("com.tamakara.booth.backend.service.order.mapper")
@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
