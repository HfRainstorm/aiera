package cn.hfstorm.aiera.ai.core.service;

import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/8 13:32
 */
public interface ChatService {



    Flux<ChatResponse> chat(ChatRequest.ChatCompletionRequest req);

    Flux<String> singleChat(ChatRequest.ChatCompletionRequest req);
}
