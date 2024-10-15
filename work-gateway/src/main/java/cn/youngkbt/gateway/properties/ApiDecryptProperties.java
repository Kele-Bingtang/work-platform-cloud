package cn.youngkbt.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 21:14:36
 * @note
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "api-decrypt")
public class ApiDecryptProperties {
    /**
     * 加密开关
     */
    private Boolean enabled;

    /**
     * 头部标识
     */
    private String headerFlag;
}
