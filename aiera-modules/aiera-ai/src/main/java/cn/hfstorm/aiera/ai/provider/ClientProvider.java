package cn.hfstorm.aiera.ai.provider;

import cn.hfstorm.aiera.ai.config.properties.ChatConfigProperties;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * client provider
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Component
@AllArgsConstructor
public class ClientProvider extends ChatMemoryProvider {


    private final ApplicationContext context;

    @Autowired
    ModelProvider modelProvider;

    private final ChatMemory chatMemory = getChatMemory();

    @Autowired
    ChatConfigProperties chatConfig;


    /**
     * 获取 chatClient
     *
     * @param conversionId 用于区分上下文，提供给chatMemory使用
     * @param aigcModelId  注册的模型id
     * @param message      用户消息
     * @return
     */
    public ChatClient getClient(String conversionId, String aigcModelId, String message) {

        ChatModel model = modelProvider.getModel(aigcModelId);
        if (model == null) {
            throw new ChatException("没有匹配到模型，请检查模型配置！");
        }

        if (StrUtil.isBlank(conversionId)) {
            conversionId = IdUtil.simpleUUID();
        }
        // 使用InMemoryChatMemory进行内存存储记忆 sessionId根据id找对应的记录,只需要最近30条
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = new MessageChatMemoryAdvisor(chatMemory, conversionId, chatConfig.getChatHistoryWindowSize());

        return ChatClient.builder(model)
                .defaultAdvisors(
                        messageChatMemoryAdvisor
//                        new QuestionAnswerAdvisor(vectorStore,
//                                new SearchRequest.Builder().query(message).build())
                )
                .build();

    }

}
