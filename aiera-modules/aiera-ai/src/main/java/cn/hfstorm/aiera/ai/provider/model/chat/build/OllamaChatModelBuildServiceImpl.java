package cn.hfstorm.aiera.ai.provider.model.chat.build;

import cn.hfstorm.aiera.ai.provider.model.AiModelBuildService;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hfstorm.aiera.common.ai.enums.ProviderEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;

/**
 * ollama chat模型构建
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Slf4j
@Component
public class OllamaChatModelBuildServiceImpl implements ChatModelBuildService, AiModelBuildService<ChatModel> {

    @Override
    public ModelTypeEnum getModelType() {
        return currentModelType();
    }

    @Override
    public boolean whetherCurrentModel(AigcModel model) {
        return ProviderEnum.OLLAMA.name().equals(model.getProvider());
    }

    @Override
    public boolean basicCheck(AigcModel model) {
        if (StringUtils.isBlank(model.getBaseUrl())) {
//            model.setBaseUrl(ollamaConnectionProperties.getBaseUrl());
            // change default base url
//            throw new ServiceException(ChatErrorEnum.BASE_URL_IS_NULL.getErrorCode(),
//                    ChatErrorEnum.BASE_URL_IS_NULL.getErrorDesc(ProviderEnum.OLLAMA.name(), model.getType()));
        }
        return true;
    }

    @Override
    public ChatModel buildModel(AigcModel model) {
        try {
            // 构造ollama 模型
            OllamaApi ollamaApi = new OllamaApi(model.getBaseUrl());
            OllamaOptions ollamaOptions =
                    OllamaOptions.builder().model(model.getModel()).temperature(model.getTemperature()).build();
            return OllamaChatModel.builder().ollamaApi(ollamaApi).defaultOptions(ollamaOptions).build();
        } catch (Exception e) {
            log.error("Ollama streaming chat 配置报错", e);
            return null;
        }
    }

}
