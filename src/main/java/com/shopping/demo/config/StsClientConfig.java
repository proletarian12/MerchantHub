package com.shopping.demo.config;

import com.aliyun.credentials.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StsClientConfig {

    /**
     * 初始化凭据客户端，单例模式
     * 使用环境变量中的 AccessKey
     */
    @Bean
    public Client credentialClient() {
        return new Client();
    }
}