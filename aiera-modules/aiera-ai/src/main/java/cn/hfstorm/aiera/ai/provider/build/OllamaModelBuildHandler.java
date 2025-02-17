package cn.hfstorm.aiera.ai.provider.build;

import cn.hfstorm.aiera.ai.provider.ModelProvider;
import cn.hfstorm.aiera.common.ai.biz.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ProviderEnum;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Slf4j
@Component
public class OllamaModelBuildHandler implements ModelBuildHandler {


    ModelProvider modelProvider;


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
    public StreamingChatLanguageModel doBuildStreamingChat(AigcModel model) {
        try {
            return OllamaStreamingChatModel.builder().baseUrl(model.getBaseUrl()).modelName(model.getModel()).temperature(model.getTemperature()).topP(model.getTopP()).logRequests(true).logResponses(true).build();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Ollama streaming chat 配置报错", e);
            return null;
        }
    }

}
