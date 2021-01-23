package com.kve.dubbo_provider_personal;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.kve")
public class DubboProviderPersonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderPersonalApplication.class, args);
    }

}
