package com.tamakara.booth.backend.common.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WxSessionResponse {
    @JsonProperty("session_key")
    private String sessionKey;
    private String unionid;
    private String errmsg;
    private String openid;
    private Integer errcode;
}
