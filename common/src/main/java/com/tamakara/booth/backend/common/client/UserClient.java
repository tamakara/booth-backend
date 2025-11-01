package com.tamakara.booth.backend.common.client;

import com.tamakara.booth.backend.common.config.FeignConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @PostMapping("/pay")
    ResponseEntity<Boolean> pay(
            @RequestParam("buyerId") Long buyerId,
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("amount") Double amount
    );

}
