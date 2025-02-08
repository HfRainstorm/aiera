package cn.hfstorm.aiera.server.service;

import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;

/**
 * @author : hmy
 * @date : 2025/2/8 13:32
 */
public interface ChatService {



    ChatClient.StreamResponseSpec chat(ChatRequest.ChatCompletionRequest req);

    ChatClient.StreamResponseSpec singleChat(ChatRequest.ChatCompletionRequest req);
}
