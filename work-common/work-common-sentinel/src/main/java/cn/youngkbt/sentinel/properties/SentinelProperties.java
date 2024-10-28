package cn.youngkbt.sentinel.properties;

import com.alibaba.cloud.sentinel.SentinelConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2024/10/29 00:23:07
 * @note
 */
@Data
@ConfigurationProperties(prefix = SentinelConstants.PROPERTY_PREFIX)
public class SentinelProperties {
    private boolean origin;
    private boolean globalException;
}
