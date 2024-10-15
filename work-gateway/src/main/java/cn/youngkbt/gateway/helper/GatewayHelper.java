package cn.youngkbt.gateway.helper;

import cn.hutool.core.util.ObjectUtil;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.gateway.filter.GlobalCacheRequestFilter;
import cn.youngkbt.utils.JacksonUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 21:06:31
 * @note
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GatewayHelper {
    /**
     * 获取原请求路径
     */
    public static String getOriginalRequestUrl(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = (URI) Optional.ofNullable(exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR)).orElse(request.getURI());
        return uri.toString();
    }

    /**
     * 是否是 Json 请求
     *
     * @param exchange HTTP 请求
     */
    public static boolean isJsonRequest(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        return StringUtils.startsWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param message  响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> responseWriter(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Response<Object> result = HttpResult.fail(500, message);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JacksonUtil.toJsonStr(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 从缓存中读取request内的body
     * <p>
     * 注意要求经过 {@link ServerWebExchangeUtils#cacheRequestBody(ServerWebExchange, Function)} 此方法创建缓存
     * 框架内已经使用 {@link GlobalCacheRequestFilter} 全局创建了body缓存
     *
     * @return body
     */
    public static String resolveBodyFromCacheRequest(ServerWebExchange exchange) {
        Object obj = exchange.getAttributes().get(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        DataBuffer buffer = (DataBuffer) obj;
        try (DataBuffer.ByteBufferIterator iterator = buffer.readableByteBuffers()) {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(iterator.next());
            return charBuffer.toString();
        }
    }
}
