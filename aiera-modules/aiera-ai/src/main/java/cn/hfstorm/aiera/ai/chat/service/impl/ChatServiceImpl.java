package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.ai.chat.util.ChatParamUtils;
import cn.hfstorm.aiera.ai.provider.ClientProvider;
import cn.hfstorm.aiera.common.ai.chat.domain.ChatRequest;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
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
    ClientProvider clientProvider;


    @Override
    public ChatClient.StreamResponseSpec streamChat(ChatRequest.ChatCompletionRequest request) throws ChatException {

        ChatReq chatReq = ChatParamUtils.buildAppChatReqByCompletionReq(request);
        return clientProvider.getClient(chatReq.getConversationId(), chatReq.getAigcModelId(), chatReq.getMessage())
                .prompt().user(chatReq.getMessage()).stream();
    }
}
