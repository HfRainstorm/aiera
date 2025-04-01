package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.ai.provider.model.ModelProvider;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements IChatService {

    @Autowired
    ModelProvider modelProvider;

    /**
     * 模拟数据库存储会话和消息
     */
    private final ChatMemory chatMemory = new InMemoryChatMemory();

    /**
     * 流式问答
     *
     * @param request
     * @return
     * @throws ChatException
     */
    @Override
    public Flux<ServerSentEvent<String>> streamChat(ChatReq request) throws ChatException {
        var messageChatMemoryAdvisor = new MessageChatMemoryAdvisor(chatMemory, request.getConversationId(), 10);

        ChatModel chatModel = modelProvider.getChatClient(request);
        return ChatClient.create(chatModel)
                .prompt().user(request.getMessage()).advisors(messageChatMemoryAdvisor)
                .stream()
                .content()
                .map(chatResponse -> ServerSentEvent.builder(chatResponse)
                        .event("message")
                        .build());

    }
}
