package com.tamakara.booth.backend.service.user.service.impl;

import com.tamakara.booth.backend.common.client.FileClient;
import com.tamakara.booth.backend.common.client.WxAuthClient;
import com.tamakara.booth.backend.common.domain.pojo.WxSessionResponse;
import com.tamakara.booth.backend.common.util.JwtUtil;
import com.tamakara.booth.backend.service.user.domain.dto.RegisterFormDTO;
import com.tamakara.booth.backend.service.user.domain.entity.User;
import com.tamakara.booth.backend.service.user.domain.vo.UserVO;
import com.tamakara.booth.backend.service.user.mapper.UserMapper;
import com.tamakara.booth.backend.service.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final FileClient fileClient;
    private final WxAuthClient wxAuthClient;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Override
    @Transactional
    public String login(String code) {
        WxSessionResponse wxSessionResponse = wxAuthClient.code2Session(appid, secret, code, "authorization_code");

        if (wxSessionResponse == null || wxSessionResponse.getOpenid() == null) {
            throw new RuntimeException("微信登录失败：" + wxSessionResponse.getErrmsg());
        }

        User user = userMapper.selectByOpenId(wxSessionResponse.getOpenid());
        if (user == null) {
            user = new User();
            user.setOpenId(wxSessionResponse.getOpenid());
            user.setCreatedAt(Instant.now());
            userMapper.insert(user);
        }

        user.setLastLoginAt(Instant.now());
        user.setIsOnline(true);
        userMapper.updateById(user);

        return JwtUtil.generateJWT(user.getId(), 3600 * 24);
    }

    @Override
    @Transactional(readOnly = true)
    public UserVO getUserVO(Long userId, Long sellerId) {
        User user = userMapper.selectById(sellerId);
        if (user == null) throw new RuntimeException("用户不存在");

        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);

        String avatarUrl = fileClient.getFileURL(user.getAvatarFileId()).getBody();
        vo.setAvatarUrl(avatarUrl);

        vo.setIsCurrentUser(sellerId.equals(userId));
        if (!vo.getIsCurrentUser()) {
            vo.setPhone(null);
            vo.setBalance(null);
            vo.setCreatedAt(null);
        }

        return vo;
    }

    @Override
    @Transactional
    public Boolean pay(Long buyerId, Long sellerId, Double amount) {
        User buyer = userMapper.selectById(buyerId);
        User seller = userMapper.selectById(sellerId);
        if (buyer == null || seller == null) {
            throw new RuntimeException("用户不存在");
        }

        if (buyer.getBalance() < amount) {
            throw new RuntimeException("余额不足");
        }

        buyer.setBalance(buyer.getBalance() - amount);
        seller.setBalance(seller.getBalance() + amount);
        userMapper.updateById(buyer);
        userMapper.updateById(seller);

        return true;
    }
}
