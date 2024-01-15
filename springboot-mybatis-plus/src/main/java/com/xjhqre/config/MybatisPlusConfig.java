package com.xjhqre.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.xjhqre.mybatis.SqlInjector;

/**
 * <p>
 * MybatisPlusConfig
 * <p>
 *
 * @author xjhqre
 * @since 1月 15, 2024
 */
@Configuration
// @AutoConfigureBefore({MybatisPlusAutoConfiguration.class}) // 在MybatisPlusAutoConfiguration前加载
public class MybatisPlusConfig {

    @Bean
    @ConditionalOnMissingBean
    public ISqlInjector sqlInjector() {
        return new SqlInjector();
    }
}
