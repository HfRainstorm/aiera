package cn.hfstorm.aiera.server.service.impl;

import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import cn.hfstorm.aiera.common.core.provider.ModelProvider;
import cn.hfstorm.aiera.server.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hmy
 * @date : 2025/2/8 13:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {


    @Autowired
    private ModelProvider modelProvider;

    @Override
    public ChatClient.StreamResponseSpec chat(ChatRequest.ChatCompletionRequest req) {
        System.out.println(req.modelName());
        ChatModel model = modelProvider.getModel(req.modelId());
        System.out.println(model);
//        ChatModel model = aiEraChatService.stream(req.modelId());


//
//
//        ChatOptions modelOptions =
//                OllamaOptions.builder()
//                        .modelName(req.modelName())
//                        .temperature(req.temperature())
//                        .topP(req.topP()).build();
//
//        List<Message> messages = chatRequest.messages().stream().map(msg -> {
//            switch (msg.role()) {
//                case ASSISTANT:
//                    return new AssistantMessage(msg.content());
//                case SYSTEM:
//                    return new SystemMessage(msg.content());
//                default:
//                    return new UserMessage(msg.content());
//            }
//        }).collect(Collectors.toList());
//
//        Prompt prompt = new Prompt(messages, modelOptions);

        return null;
    }

    @Override
    public ChatClient.StreamResponseSpec singleChat(ChatRequest.ChatCompletionRequest req) {
        return null;
    }
}
