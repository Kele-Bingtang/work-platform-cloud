package cn.youngkbt.gateway.filter;

import cn.youngkbt.gateway.helper.GatewayHelper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 21:22:54
 * @note 全局缓存获取 body 请求数据（解决流不能重复读取问题）
 */
@Component
public class GlobalCacheRequestFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 只缓存 json 类型请求
        if (!GatewayHelper.isJsonRequest(exchange)) {
            return chain.filter(exchange);
        }
        return ServerWebExchangeUtils.cacheRequestBody(exchange, (serverHttpRequest) -> {
            if (serverHttpRequest == exchange.getRequest()) {
                return chain.filter(exchange);
            }
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
