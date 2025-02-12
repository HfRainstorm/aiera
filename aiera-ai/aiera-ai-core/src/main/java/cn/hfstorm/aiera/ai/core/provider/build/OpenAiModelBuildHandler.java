package cn.hfstorm.aiera.ai.core.provider.build;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.core.enums.ProviderEnum;
import cn.hfstorm.aiera.common.ai.enums.ChatErrorEnum;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Slf4j
@Component
public class OpenAiModelBuildHandler implements ModelBuildHandler {

    @Override
    public boolean whetherCurrentModel(AigcModel model) {
        String provider = model.getProvider();
        return ProviderEnum.OPENAI.name().equals(provider) ||
                ProviderEnum.GEMINI.name().equals(provider) ||
                ProviderEnum.CLAUDE.name().equals(provider) ||
                ProviderEnum.AZURE_OPENAI.name().equals(provider) ||
                ProviderEnum.DOUYIN.name().equals(provider) ||
                ProviderEnum.YI.name().equals(provider) ||
                ProviderEnum.SILICON.name().equals(provider) ||
                ProviderEnum.DEEPSEEK.name().equals(provider) ||
                ProviderEnum.SPARK.name().equals(provider);
    }

    @Override
    public boolean basicCheck(AigcModel model) {
        String apiKey = model.getApiKey();
        if (StrUtil.isBlank(apiKey)) {
            throw new ServiceException(ChatErrorEnum.API_KEY_IS_NULL.getErrorCode(),
                    ChatErrorEnum.API_KEY_IS_NULL.getErrorDesc(model.getProvider().toUpperCase(), model.getType()));
        }
        return true;
    }

    @Override
    public ChatModel doBuildStreamingChat(AigcModel model) {
        try {
            if (!whetherCurrentModel(model)) {
                return null;
            }
            if (!basicCheck(model)) {
                return null;
            }
//            return OpenAiStreamingChatModel
//                    .builder()
//                    .apiKey(model.getApiKey())
//                    .baseUrl(model.getBaseUrl())
//                    .modelName(model.getModel())
//                    .maxTokens(model.getResponseLimit())
//                    .temperature(model.getTemperature())
//                    .logRequests(true)
//                    .logResponses(true)
//                    .topP(model.getTopP())
//                    .build();
            return null;
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(model.getProvider() + " Streaming Chat 模型配置报错", e);
            return null;
        }
    }

    @Override
    public EmbeddingModel doBuildEmbedding(AigcModel model) {
        return null;
    }
}
