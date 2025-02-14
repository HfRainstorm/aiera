package cn.hfstorm.aiera.ai.provider;

import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
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


    private final ApplicationContext context;

    @Autowired
    VectorStore vectorStore;


    public ChatModel getModel(String aigcModelId) throws ChatException {
        try {
            return (ChatModel) context.getBean(aigcModelId);
        } catch (Exception e) {
            throw new ChatException("没有匹配到模型，请检查模型配置！");
        }
    }

}
