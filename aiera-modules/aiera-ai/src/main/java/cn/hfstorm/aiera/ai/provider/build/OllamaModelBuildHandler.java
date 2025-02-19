package cn.hfstorm.aiera.ai.provider.build;

import cn.hfstorm.aiera.ai.chat.domain.AigcChatModel;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ProviderEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Slf4j
@Component
public class OllamaModelBuildHandler implements ModelBuildHandler {

    private OllamaApi ollamaApi;
    private OllamaOptions ollamaOptions;

    private OllamaChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public boolean whetherCurrentModel(AigcModel model) {
        return ProviderEnum.OLLAMA.name().equals(model.getProvider());
    }

    @Override
    public boolean basicCheck(AigcModel model) {
        if (StringUtils.isBlank(model.getBaseUrl())) {
//            model.setBaseUrl(ollamaConnectionProperties.getBaseUrl());
            // change default base url
//            throw new ServiceException(ChatErrorEnum.BASE_URL_IS_NULL.getErrorCode(),
//                    ChatErrorEnum.BASE_URL_IS_NULL.getErrorDesc(ProviderEnum.OLLAMA.name(), model.getType()));
        }
        return true;
    }

    @Override
    public ChatClient.StreamResponseSpec doStreamChat(ChatReq req) {
//        OllamaApi.ChatRequest request = OllamaApi.ChatRequest.builder(req.getModelId()).stream(true) // streaming
//                .messages(List.of(OllamaApi.Message.builder(OllamaApi.Message.Role.USER)
//                        .content(req.getMessage()).build())).options(ollamaOptions).build();
//        return this.ollamaApi.streamingChat(request).concatMap(chatResponse -> {
//            ChatResponse res = new ChatResponse(chatResponse.model());
//        });
        return chatClient.prompt().user(req.getMessage()).stream();
    }

    @Override
    public AigcChatModel doBuildChatModel(AigcModel model) {
        try {


            // 构造ollama 模型
            ollamaApi = new OllamaApi(model.getBaseUrl());
            ollamaOptions =
                    OllamaOptions.builder().model(model.getModel()).temperature(model.getTemperature()).build();
            chatModel =
                    OllamaChatModel.builder().ollamaApi(ollamaApi).defaultOptions(ollamaOptions).build();
            chatClient = ChatClient.builder(chatModel)
                    .defaultAdvisors(
                            new MessageChatMemoryAdvisor(new InMemoryChatMemory()) // chat-memory advisor
                    )
                    .build();
            return AigcChatModel.builder().aigcModel(model).chatModel(chatModel).modelBuildHandler(this).build();
        } catch (Exception e) {
            log.error("Ollama streaming chat 配置报错", e);
            return null;
        }
    }

}
