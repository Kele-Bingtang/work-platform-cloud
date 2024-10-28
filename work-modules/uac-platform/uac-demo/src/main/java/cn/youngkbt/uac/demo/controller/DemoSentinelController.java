package cn.youngkbt.uac.demo.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2024/10/28 21:39:21
 * @note
 */
@RestController
@RequestMapping("/demo/sentinel")
public class DemoSentinelController {

    @SentinelResource(value = "sayHelloA", blockHandler = "sayHelloBlockHandler", fallback = "sayHelloFallback")
    @GetMapping("/sayHelloA")
    public Response<String> sayHelloA(@RequestParam(required = false) String msg) {
        return HttpResult.okMessage("Hi Sentinel A，" + msg);
    }

    @SentinelResource(value = "sayHelloB", fallback = "sayHelloFallback")
    @GetMapping("/sayHelloB")
    public Response<String> sayHelloB(@RequestParam(required = false) String msg) {
        return HttpResult.okMessage("Hi Sentinel B，" + msg);
    }

    /**
     * BlockException 异常的回调（Sentinel 的限流、熔断、热点、授权等规则都会产生 BlockException），参数和资源的参数一致，额外多出 BlockException
     */
    public Response<String> sayHelloBlockHandler(String msg, BlockException blockException) {
        return HttpResult.failMessage("限流规则 " + blockException.getMessage() + " ==> msg：" + msg);
    }

    /**
     * 非 BlockException 异常的回调，参数和资源的参数一致，额外多出 Throwable（可以捕获所有异常）
     */
    public Response<String> sayHelloFallback(String msg, Throwable throwable) {
        return HttpResult.error("熔断规则 " + throwable.getMessage() + " ==> msg：" + msg);
    }

}