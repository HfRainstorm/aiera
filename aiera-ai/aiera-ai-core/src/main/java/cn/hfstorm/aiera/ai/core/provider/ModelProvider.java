package cn.hfstorm.aiera.ai.core.provider;

import cn.hfstorm.aiera.ai.core.chat.entity.ModelChat;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * model provider
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Component
@AllArgsConstructor
public class ModelProvider {


    private ApplicationContext context;

    public ChatModel getModel(String modelId) {
        try {
            ModelChat modelChat = (ModelChat) context.getBean(modelId);
            return modelChat.getChatModel();
        } catch (Exception e) {
            throw new RuntimeException("没有匹配到模型，请检查模型配置！");
        }
    }

    public ChatClient getModelClient(String modelId) {
        try {
            // TODO: deal with image model or other model
            ModelChat modelChat = (ModelChat) context.getBean(modelId);
            return modelChat.getChatClient();
        } catch (Exception e) {
            throw new RuntimeException("没有匹配到模型，请检查模型配置！");
        }
    }


}
