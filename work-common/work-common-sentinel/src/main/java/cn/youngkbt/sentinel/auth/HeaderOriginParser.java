package cn.youngkbt.sentinel.auth;

import cn.youngkbt.utils.StringUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Kele-Bingtang
 * @date 2024/10/28 22:02:08
 * @note 自定义限流的 Origin 解析器，只有 header 携带 origin: gateway 时才有效（Gateway 网关自动携带），否则不允许执行 Sentinel 的鉴权规则
 */
public class HeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 获取请求头
        String origin = request.getHeader("origin");
        if (StringUtil.hasEmpty(origin)) {
            // 返回任意不存在于授权规则中流控应用的值则代表进行限流
            return "blank";
        }
        // Sentinel 放行条件：返回 null 或者授权规则中流控应用的值等于 origin
        return origin;
    }
}