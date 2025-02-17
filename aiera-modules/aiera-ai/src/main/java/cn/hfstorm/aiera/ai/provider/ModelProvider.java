package cn.hfstorm.aiera.ai.provider;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import lombok.AllArgsConstructor;
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


    public StreamingChatLanguageModel stream(String model) {
        try {
            return (StreamingChatLanguageModel) context.getBean(model);
        } catch (Exception e) {
            throw new RuntimeException("没有匹配到模型，请检查模型配置！");
        }
    }


}
