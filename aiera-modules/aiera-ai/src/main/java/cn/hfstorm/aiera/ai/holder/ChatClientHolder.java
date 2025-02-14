package cn.hfstorm.aiera.ai.holder;

import cn.hfstorm.aiera.ai.provider.ModelProvider;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * chat client holder
 *
 * @author : hmy
 * @date : 2025/2/14
 */

@Component
@AllArgsConstructor
public class ChatClientHolder {

    private static Map<String, ChatClient> chatClientInstanctMap = new WeakConcurrentMap<>();
    private static Map<String, String> conversionId2ModelIdCacheMap = new WeakConcurrentMap<>();

    @Autowired
    private ModelProvider modelProvider;

    private ApplicationContext context;

    public ChatClient getChatClient(String aigcModelId) {
        return getChatClient(IdUtil.simpleUUID(), aigcModelId);
    }

    public ChatClient getChatClient(String conversionId, String aigcModelId) {

        // 未切换模型
        if (chatClientInstanctMap.containsKey(conversionId)
                && conversionId2ModelIdCacheMap.containsKey(conversionId)
                && conversionId2ModelIdCacheMap.get(conversionId).equals(aigcModelId)) {

            // 模型存在
            return chatClientInstanctMap.get(conversionId);

        }

        // conversion id
        boolean containsChatClient = context.containsBean(conversionId);
        if (containsChatClient) {
            return (ChatClient) context.getBean(conversionId);
        }

        // build chat client
        ChatModel model = modelProvider.getModel(aigcModelId);

        ChatClient chatClient = ChatClient.builder(model)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(
                                new InMemoryChatMemory())
//                            new QuestionAnswerAdvisor(vectorStore,
//                                    new SearchRequest.Builder().query(chatReq.getMessage()).build())
                ).build();

        chatClientInstanctMap.put(conversionId, chatClient);

        return chatClient;

    }
}
