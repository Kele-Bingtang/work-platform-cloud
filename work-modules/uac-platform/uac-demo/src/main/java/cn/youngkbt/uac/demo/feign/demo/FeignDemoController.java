package cn.youngkbt.uac.demo.feign.demo;

import cn.youngkbt.core.http.Response;
import cn.youngkbt.uac.demo.feign.inter.NoticeFeign;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2024/10/24 23:49:29
 * @note
 */
@RestController
@RequestMapping("/demo/feign/notice")
@RequiredArgsConstructor
public class FeignDemoController {
    
    private final NoticeFeign noticeFeign;

    @GetMapping
    @Operation(summary = "发送邮件", description = "发送邮件（不支持附件）")
    public Response<NoticeFeign.NoticeInfoVO> sendMail(NoticeFeign.NoticeInfoDTO noticeInfoDTO) {
        return noticeFeign.sendMail(noticeInfoDTO);
    }
}
