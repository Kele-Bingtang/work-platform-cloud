package cn.youngkbt.sentinel.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.time.ZoneId;

/**
 * @author Kele-Bingtang
 * @date 2024/10/28 22:10:37
 * @note 自定义异常处理
 */
public class SentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, String s, BlockException e) throws Exception {
        String message = "未知异常";
        int status = 429;

        if (e instanceof FlowException) {
            message = "请求被限流了";
        } else if (e instanceof ParamFlowException) {
            message = "请求被热点参数限流";
        } else if (e instanceof DegradeException) {
            message = "请求被降级了";
        } else if (e instanceof AuthorityException) {
            message = "没有权限访问";
            status = 401;
        }

        long epochMilli = Instant.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print("{\"code\": " + 200 + ", \"message\": " + message + ", \"status\": \"success\", \"data\": null, \"timeStamp\": " + epochMilli + "}");
    }
}