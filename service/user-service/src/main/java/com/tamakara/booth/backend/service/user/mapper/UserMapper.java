package com.tamakara.booth.backend.service.user.mapper;

import com.tamakara.booth.backend.service.user.domain.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User selectByOpenId(String openId) {
        return selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenId, openId)
        );
    }

}
