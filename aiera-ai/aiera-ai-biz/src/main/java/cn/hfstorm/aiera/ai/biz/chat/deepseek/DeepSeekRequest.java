package cn.hfstorm.aiera.ai.biz.chat.deepseek;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * DeepSeek request
 *
 * @author HanLucas
 * @Date 2025/2/8 19:00
 */
@Data
@Builder
public class DeepSeekRequest {

    /**
     * 模型名称
     */
    private String model;

    /**
     * 消息列表
     */
    private List<Message> messages;

    @Data
    @Builder
    public static class Message {

        /**
         * 角色
         */
        private String role;

        /**
         * 内容
         */
        private String content;

    }

}
