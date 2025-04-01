package cn.hfstorm.aiera.ai.chat.service;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
public interface IChatService {

    // 模拟数据库存储会话和消息

    /**
     * 流式对话
     *
     * @param request
     * @return
     */
    Flux<ServerSentEvent<String>> streamChat(ChatReq request);

//    void chat(ChatReq req);
}
