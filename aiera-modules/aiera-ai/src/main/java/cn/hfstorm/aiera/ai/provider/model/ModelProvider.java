package cn.hfstorm.aiera.ai.provider.model;

import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
public class ModelProvider {


    private final ApplicationContext context;
    private final AigcModelService aigcModelService;
    private final SpringContextHolder contextHolder;

    private AiModelStoreFactory aiModelStoreFactory;

    public ChatModel getChatClient(ChatReq chatReq) {

        if (context.containsBean(chatReq.getModelId())) {
            return (ChatModel) context.getBean(chatReq.getModelId());
        } else {

            AigcModel model = aigcModelService.selectById(chatReq.getModelId());

            if (Objects.equals(model.getBaseUrl(), "")) {
                model.setBaseUrl(null);
            }
            // Uninstall previously registered beans before registering them
            contextHolder.unregisterBean(chatReq.getModelId());

            ChatModel chatModel = aiModelStoreFactory.aiModelHandler(model);
            if (null == chatModel) {

                throw new ChatException("没有匹配到模型，请检查模型配置！");
            }

            return chatModel;
        }
    }


}
