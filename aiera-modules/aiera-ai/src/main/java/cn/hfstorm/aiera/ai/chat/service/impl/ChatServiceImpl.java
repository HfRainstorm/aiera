package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.ai.chat.util.ChatParamUtils;
import cn.hfstorm.aiera.ai.provider.ClientProvider;
import cn.hfstorm.aiera.common.ai.chat.domain.ChatRequest;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.ai.provider.ModelProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    ClientProvider modelProvider;


    @Override
    public ChatClient.StreamResponseSpec streamChat(ChatRequest.ChatCompletionRequest request) throws ChatException {

        ChatReq chatReq = ChatParamUtils.buildAppChatReqByCompletionReq(request);

//        return modelProvider.getClient(request.conversationId(), request.aigcModelId())
//                .prompt().user(chatReq.getMessage()).stream();

        return null;
    }
}
