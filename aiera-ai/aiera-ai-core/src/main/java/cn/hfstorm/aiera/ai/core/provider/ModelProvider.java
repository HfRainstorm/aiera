package cn.hfstorm.aiera.ai.core.provider;

import cn.hfstorm.aiera.ai.core.chat.entity.ModelChat;
import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    VectorStore vectorStore;

    public ChatModel getModel(String modelId) {
        try {
            ModelChat modelChat = (ModelChat) context.getBean(modelId);
            return modelChat.getChatModel();
        } catch (Exception e) {
            throw new RuntimeException("没有匹配到模型，请检查模型配置！");
        }
    }


    public ChatClient getModelClient(ChatRequest.ChatCompletionRequest req) {

        // build chat client
        ChatClient chatClient = ChatClient.builder(getModel(req.modelId()))
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                        new QuestionAnswerAdvisor(vectorStore,
                                new SearchRequest.Builder().query(req.messages().get(0).content()).build())).build();

        return chatClient;
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
