package cn.hfstorm.aiera.ai.core.service.impl;

import cn.hfstorm.aiera.ai.biz.service.AigcModelService;
import cn.hfstorm.aiera.ai.core.chat.prompt.PromptUtils;
import cn.hfstorm.aiera.ai.core.provider.ModelProvider;
import cn.hfstorm.aiera.ai.core.service.ChatService;
import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/8 13:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {


    @Autowired
    ModelProvider modelProvider;

    @Autowired
    AigcModelService aigcModelService;

    @Override
    public Flux<ChatResponse> chat(ChatRequest.ChatCompletionRequest req) {
        ChatClient chatClient = modelProvider.getModelClient(req.modelId());

        return chatClient.prompt(PromptUtils.generateChatPrompt(aigcModelService.selectById(req.modelId()), req)).stream().chatResponse();
    }

    @Override
    public Flux<String> singleChat(ChatRequest.ChatCompletionRequest req) {
        ChatModel model = modelProvider.getModel(req.modelId());
//
//        return model.stream(PromptUtils.generateChatPrompt(aigcModelService.selectById(req.modelId()), req))
//                .map(response -> (response.getResult() == null || response.getResult().getOutput() == null
//                || response.getResult().getOutput().getText() == null) ? ""
//                : response.getResult().getOutput().getText());
        String content = req.messages().get(0).content();
        ChatClient chatClient = modelProvider.getModelClient(req);

        return chatClient.prompt().user(content).stream().content();
    }
}
