package com.tamakara.booth.backend.common.client;

import com.tamakara.booth.backend.common.domain.pojo.WxSessionResponse;
import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "wx-auth", url = "https://api.weixin.qq.com", configuration = WxAuthClient.FeignTestConfiguration.class)
public interface WxAuthClient {
    @GetMapping("/sns/jscode2session")
    WxSessionResponse code2Session(
            @RequestParam("appid") String appid,
            @RequestParam("secret") String secret,
            @RequestParam("js_code") String jsCode,
            @RequestParam("grant_type") String grantType
    );

    class FeignTestConfiguration {
        @Bean
        public Decoder textPlainDecoder() {
            return new SpringDecoder(() -> new HttpMessageConverters(new CustomMappingJackson2HttpMessageConverter()));
        }
    }

    class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        @Override
        public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
            super.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        }
    }
}
