package cn.hfstorm.aiera.ai.provider;

import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.ai.provider.build.ModelBuildHandler;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * model provider
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
@Slf4j
@Component
@AllArgsConstructor
public class ModelProvider extends ChatMemoryProvider{


    private final ApplicationContext context;
    private final ChatMemory chatMemory = getChatMemory();


    private final AigcModelService aigcModelService;
    private final SpringContextHolder contextHolder;
    private List<ModelBuildHandler> modelBuildHandlers;


    public ChatClient chatHandler(ChatReq chatReq, AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.CHAT.name().equals(type)) {
                return null;
            }
            for (ModelBuildHandler x : modelBuildHandlers) {
                // 构造chat model
                ChatModel chatModel = x.buildStreamingChat(model);

                ChatClient chatClient = ChatClient.builder(chatModel)
                        .defaultAdvisors(
                                new MessageChatMemoryAdvisor(chatMemory, chatReq.getConversationId(), 30)
//                        ,
//                        new QuestionAnswerAdvisor(vectorStore,
//                                new SearchRequest.Builder().query(chatReq.getMessage()).build())
                        ).build();

                if (ObjectUtil.isNotEmpty(chatClient)) {
                    contextHolder.registerBean(chatReq.getConversationId(), chatClient);
                    log.info("已成功注册模型：{} -- {}， 模型配置：{}", model.getProvider(), model.getType(), model);
                    return chatClient;
                }
            }
        } catch (Exception e) {
            log.error("model 【 id: {} name: {}】streaming chat 配置报错", model.getId(), model.getName());
        }
        return null;
    }

    public ChatClient getChatClient(ChatReq chatReq) {

        if (context.containsBean(chatReq.getConversationId())) {
            return (ChatClient) context.getBean(chatReq.getConversationId());
        } else {

            AigcModel model = aigcModelService.selectById(chatReq.getModelId());

            if (Objects.equals(model.getBaseUrl(), "")) {
                model.setBaseUrl(null);
            }
            // Uninstall previously registered beans before registering them
            contextHolder.unregisterBean(chatReq.getConversationId());

            ChatClient chatClient = chatHandler(chatReq, model);
            if (null == chatClient) {

                throw new ChatException("没有匹配到模型，请检查模型配置！");
            }

            return chatClient;
        }
    }


}
