package cn.hfstorm.aiera.ai.chat.service;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import org.springframework.ai.chat.client.ChatClient;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
public interface IChatService {

    /**
     * 流式对话
     *
     * @param content
     * @return
     */
    ChatClient.StreamResponseSpec streamChat(ChatReq request);

//    void chat(ChatReq req);
}
