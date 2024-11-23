package cn.youngkbt.gateway.filter;

import cn.youngkbt.utils.IdsUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 20:35:26
 * @note 转发认证全局过滤器(内部服务隔离)。原理：拦截请求，在请求头中添加 一个随机字符串，通过 Redis 存储，下游服务从 Redis 获取进行验证。
 */
@Component
public class GlobalInternalAuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String simpleUUID = IdsUtil.simpleUUID();
        ServerHttpRequest httpRequest = exchange.getRequest().mutate().header("gateway-token", simpleUUID).build();
        // 将 simpleUUID 存入 Redis，内网服务先进行校验

        return chain.filter(exchange.mutate().request(httpRequest).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
