package cn.youngkbt.sentinel.config;

import cn.youngkbt.sentinel.auth.HeaderOriginParser;
import cn.youngkbt.sentinel.exception.SentinelExceptionHandler;
import cn.youngkbt.sentinel.properties.SentinelProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Kele-Bingtang
 * @date 2024/10/28 22:06:15
 * @note
 */
@AutoConfiguration
@ConditionalOnProperty(value = "spring.cloud.sentinel.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SentinelProperties.class)
public class SentinelConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "spring.cloud.sentinel.origin", havingValue = "true")
    public HeaderOriginParser headerOriginParser() {
        return new HeaderOriginParser();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "spring.cloud.sentinel.globalException", havingValue = "true", matchIfMissing = true)
    public SentinelExceptionHandler sentinelExceptionHandler() {
        return new SentinelExceptionHandler();
    }
}
