package com.tamakara.booth.backend.service.user.service;

import com.tamakara.booth.backend.service.user.domain.entity.User;
import com.tamakara.booth.backend.service.user.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    String login(String code);

    UserVO getUserVO(Long userId, Long sellerId);

    Boolean pay(Long buyerId, Long sellerId, Double amount);
}
