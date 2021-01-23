package com.kve.dubbo_interface.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * common
 */


@Configuration
    public class DozerBeanMapperConfigure {
        @Bean
        public DozerBeanMapper mapper() {
            DozerBeanMapper mapper = new DozerBeanMapper();
            return mapper;
        }
    }