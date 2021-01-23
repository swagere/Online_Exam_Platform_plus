package com.kve.dubbo_interface.config;

import com.kve.dubbo_interface.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * common
 */

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/login_api/login/id")
                .excludePathPatterns("/login_api/login/phone")
                .excludePathPatterns("/login_api/login/get/userInfo")
                .excludePathPatterns("/login_api/login/logout")
                .excludePathPatterns("/login_api/register/student")
                .excludePathPatterns("/login_api/register/teacher")
                .excludePathPatterns("/login_api/register/email/student")
                .excludePathPatterns("/login_api/register/email/teacher")
                .excludePathPatterns("/login_api/lostPassword/*");
    }
}
