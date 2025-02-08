package cn.hfstorm.aiera.ai.core.provider;

import lombok.AllArgsConstructor;
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
            return (ChatModel) context.getBean(modelId);
        } catch (Exception e) {
            throw new RuntimeException("没有匹配到模型，请检查模型配置！");
        }
    }


}
