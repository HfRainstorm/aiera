package cn.hfstorm.aiera.ai.chat.service;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.domain.ChatRes;
import dev.langchain4j.service.TokenStream;
import reactor.core.publisher.Flux;

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
//    ChatClient.StreamResponseSpec streamChat(ChatRequest.ChatCompletionRequest request);

    void chat(ChatReq req);
}
