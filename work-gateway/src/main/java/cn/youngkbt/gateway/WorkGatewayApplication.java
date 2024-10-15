package cn.youngkbt.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * @author Kele-Bingtang
 * @date 2024/10/15 19:49:12
 * @note
 */
@SpringBootApplication
public class WorkGatewayApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WorkGatewayApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  网关启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
