package com.tamakara.booth.backend.service.user;

import com.tamakara.booth.backend.common.client.FileClient;
import com.tamakara.booth.backend.common.client.WxAuthClient;
import com.tamakara.booth.backend.common.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {FileClient.class, WxAuthClient.class},
        defaultConfiguration = FeignConfig.class
)
@MapperScan("com.tamakara.booth.backend.service.user.mapper")
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
