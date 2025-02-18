package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.ai.provider.ModelProvider;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ChatServiceImpl implements IChatService {

    @Autowired
    ModelProvider modelProvider;

    @Override
    public ChatClient.StreamResponseSpec streamChat(ChatReq request) throws ChatException {
        return modelProvider.getChatClient(request).prompt().user(request.getMessage()).stream();

    }
}
