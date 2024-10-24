package cn.youngkbt.uac.demo.feign.inter;

import cn.youngkbt.core.http.Response;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/10/25 00:27:01
 * @note
 */
@FeignClient(name = "notice-platform")
public interface NoticeFeign {
    
    @PostMapping("/mail")
    Response<NoticeInfoVO> sendMail(@RequestBody NoticeInfoDTO noticeInfoDTO);

    @Data
    class NoticeInfoVO {
        private String noticeId;
    }

    @Data
    class NoticeInfoDTO {
        /**
         * App ID
         */
        private String appId;
        /**
         * 接收者
         */
        private String to;
        /**
         * 抄送者
         */
        private String cc;
        /**
         * 密送者
         */
        private String bcc;
        /**
         * 发送人别称
         */
        private String fromName;
        /**
         * 主题
         */
        private String subject;
        /**
         * 正文
         */
        private String content;
        /**
         * 优先级
         */
        private Integer priority;
        /**
         * 分类
         */
        private String category;
        /**
         * 业务 ID
         */
        private String bizId;
        /**
         * 附件
         */
        private List<MultipartFile> fileList;
        /**
         * 是否显示自动提示
         */
        private Boolean showTip;
    }


}
