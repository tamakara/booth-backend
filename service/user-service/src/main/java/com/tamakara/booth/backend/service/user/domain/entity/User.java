package com.tamakara.booth.backend.service.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "open_id")
    private String openId;

    @TableField(value = "is_online")
    private Boolean isOnline;

    @TableField(value = "is_open")
    private Boolean isOpen;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "password")
    private String password;

    @TableField(value = "username")
    private String username;

    @TableField(value = "introduction")
    private String introduction;

    @TableField(value = "announcement")
    private String announcement;

    @TableField(value = "followers")
    private Long followers;

    @TableField(value = "avatar_file_id")
    private Long avatarFileId;

    @TableField(value = "balance")
    private Double balance;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

    @TableField(value = "last_login_at")
    private Instant LastLoginAt;

    public User() {
        this.phone = "";
        this.password = "";
        this.isOnline = false;
        this.isOpen = false;
        this.username = "";
        this.introduction = "";
        this.announcement = "";
        this.followers = 0L;
        this.avatarFileId = 1L;
        this.balance = 0.0;
    }
}
