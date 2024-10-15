package cn.youngkbt.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 20:39:52
 * @note
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "gateway.log")
public class LogProperties {
    /**
     * 请求日志是否开启
     */
    private Boolean enabled;
}
