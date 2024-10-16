package cn.youngkbt.gateway.filter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.youngkbt.gateway.helper.GatewayHelper;
import cn.youngkbt.gateway.properties.ApiDecryptProperties;
import cn.youngkbt.gateway.properties.LogProperties;
import cn.youngkbt.utils.JacksonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 20:39:05
 * @note 日志全局过滤器
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GlobalLogFilter implements GlobalFilter, Ordered {

    private final LogProperties logProperties;
    private final ApiDecryptProperties apiDecryptProperties;

    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果不开启日志功能
        if (Objects.isNull(logProperties.getEnabled()) || !logProperties.getEnabled()) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();
        // 获取请求 URI
        String originalRequestUrl = GatewayHelper.getOriginalRequestUrl(exchange);
        String url = request.getMethod().name() + " | " + originalRequestUrl;

        // 打印日志
        printLog(exchange, url);

        // 计算请求耗时
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                long executeTime = (System.currentTimeMillis() - startTime);
                log.info("结束请求，URL「{}」，耗时「{}」毫秒", url, executeTime);
            }
        }));
    }

    /**
     * 打印日志
     *
     * @param exchange 请求信息
     * @param url      请求的 Url
     */
    private void printLog(ServerWebExchange exchange, String url) {
        ServerHttpRequest request = exchange.getRequest();
        // 打印请求参数
        if (GatewayHelper.isJsonRequest(exchange)) {
            if (apiDecryptProperties.getEnabled() && ObjectUtil.isNotNull(request.getHeaders().getFirst(apiDecryptProperties.getHeaderFlag()))) {
                log.info("开始请求 => URL「{}」,参数类型【encrypt】", url);
            } else {
                String jsonParam = GatewayHelper.resolveBodyFromCacheRequest(exchange);
                log.info("开始请求，URL「{}」，参数类型【json】，参数:「{}」", url, jsonParam);
            }
        } else {
            MultiValueMap<String, String> parameterMap = request.getQueryParams();
            if (MapUtil.isNotEmpty(parameterMap)) {
                String parameters = JacksonUtil.toJsonStr(parameterMap);
                log.info("开始请求，URL「{}」，参数类型【param】，参数:「{}」", url, parameters);
            } else {
                log.info("开始请求，URL「{}」，无参数", url);
            }
        }
    }

    @Override
    public int getOrder() {
        // 日志处理器在负载均衡器之后执行 负载均衡器会导致线程切换 无法获取上下文内容
        // 如需在日志内操作线程上下文 例如获取登录用户数据等 可以打开下方注释代码
        // return ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER - 1;
        return Ordered.LOWEST_PRECEDENCE;
    }
}
