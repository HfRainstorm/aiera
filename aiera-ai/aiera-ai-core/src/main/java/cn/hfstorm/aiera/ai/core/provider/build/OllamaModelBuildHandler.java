package cn.hfstorm.aiera.ai.core.provider.build;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.core.constants.ProviderEnum;
import cn.hfstorm.aiera.common.ai.enums.ChatErrorEnum;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Slf4j
@Component
public class OllamaModelBuildHandler implements ModelBuildHandler {

    @Override
    public boolean whetherCurrentModel(AigcModel model) {
        return ProviderEnum.OLLAMA.name().equals(model.getProvider());
    }

    @Override
    public boolean basicCheck(AigcModel model) {
        if (StringUtils.isBlank(model.getBaseUrl())) {
            // change default base url
            throw new ServiceException(ChatErrorEnum.BASE_URL_IS_NULL.getErrorCode(),
                    ChatErrorEnum.BASE_URL_IS_NULL.getErrorDesc(ProviderEnum.OLLAMA.name(), model.getType()));
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
//            return OllamaStreamingChatModel
//                    .builder()
//                    .baseUrl(model.getBaseUrl())
//                    .modelName(model.getModel())
//                    .temperature(model.getTemperature())
//                    .topP(model.getTopP())
//                    .logRequests(true)
//                    .logResponses(true)
//                    .build();
            return null;
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Ollama streaming chat 配置报错", e);
            return null;
        }
    }
}
