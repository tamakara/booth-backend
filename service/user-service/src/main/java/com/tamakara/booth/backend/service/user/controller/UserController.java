package com.tamakara.booth.backend.service.user.controller;

import com.tamakara.booth.backend.service.user.domain.dto.RegisterFormDTO;
import com.tamakara.booth.backend.service.user.domain.vo.UserVO;
import com.tamakara.booth.backend.service.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "登录")
    @GetMapping("/login")
    public ResponseEntity<String> loginByWeChat(@RequestParam String code) {
        String token = userService.login(code);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "获取商家信息")
    @GetMapping("/vo/user")
    public ResponseEntity<UserVO> getUserVO(
            @RequestHeader(name = "X-USER-ID", required = false) Long userId,
            @RequestParam(name = "sellerId", required = false) Long sellerId
    ) {
        if (sellerId == null) sellerId = userId;
        UserVO userVO = userService.getUserVO(userId, sellerId);
        return ResponseEntity.ok(userVO);
    }

    @Operation(summary = "支付")
    @PostMapping("/pay")
    public ResponseEntity<Boolean> pay(
            @RequestParam("buyerId") Long buyerId,
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("amount") Double amount
    ) {
        Boolean result = userService.pay(buyerId, sellerId, amount);
        return ResponseEntity.ok(result);
    }
}