package cn.hfstorm.aiera.ai.core.provider.build;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.core.enums.ProviderEnum;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.autoconfigure.ollama.OllamaConnectionProperties;
import org.springframework.ai.chat.model.ChatModel;
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

    OllamaConnectionProperties ollamaConnectionProperties;

    @Override
    public boolean whetherCurrentModel(AigcModel model) {
        return ProviderEnum.OLLAMA.name().equals(model.getProvider());
    }

    @Override
    public boolean basicCheck(AigcModel model) {
        if (StringUtils.isBlank(model.getBaseUrl())) {
            model.setBaseUrl(ollamaConnectionProperties.getBaseUrl());
            // change default base url
//            throw new ServiceException(ChatErrorEnum.BASE_URL_IS_NULL.getErrorCode(),
//                    ChatErrorEnum.BASE_URL_IS_NULL.getErrorDesc(ProviderEnum.OLLAMA.name(), model.getType()));
        }
        return true;
    }

    @Override
    public ChatModel buildStreamingChat(AigcModel model) {
        try {
            if (!whetherCurrentModel(model)) {
                return null;
            }
            if (!basicCheck(model)) {
                return null;
            }

            // 构造ollama 模型
            OllamaApi ollamaApi = new OllamaApi(model.getBaseUrl());
            OllamaOptions ollamaOptions = OllamaOptions.builder()
                    .model(model.getModel())
                    .temperature(model.getTemperature())
                    .build();

            return OllamaChatModel.builder().ollamaApi(ollamaApi).defaultOptions(ollamaOptions).build();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Ollama streaming chat 配置报错", e);
            return null;
        }
    }
}
