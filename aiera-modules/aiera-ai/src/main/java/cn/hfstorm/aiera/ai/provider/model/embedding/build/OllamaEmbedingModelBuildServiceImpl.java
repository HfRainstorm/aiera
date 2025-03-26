package cn.hfstorm.aiera.ai.provider.model.embedding.build;

import cn.hfstorm.aiera.ai.provider.model.AiModelBuildService;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.EmbeddingTypeEnum;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/21 9:12
 */
@Component
public class OllamaEmbedingModelBuildServiceImpl implements EmbedModelBuildService, AiModelBuildService<EmbeddingModel> {


    @Override
    public ModelTypeEnum getModelType() {
        return currentModelType();
    }

    @Override
    public boolean whetherCurrentModel(AigcModel aigcModel) {
        return EmbeddingTypeEnum.OLLAMA.name().equalsIgnoreCase(aigcModel.getProvider());
    }

    @Override
    public boolean basicCheck(AigcModel aigcModel) {
        return true;
    }

    @Override
    public EmbeddingModel buildModel(AigcModel aigcModel) {
        OllamaApi ollamaApi = new OllamaApi(aigcModel.getBaseUrl());
        OllamaOptions ollamaOptions =
                OllamaOptions.builder()
                        .model(aigcModel.getModel())
                        .temperature(Double.valueOf(aigcModel.getDimension())).build();
        return OllamaEmbeddingModel.builder().ollamaApi(ollamaApi).defaultOptions(ollamaOptions).build();
    }
}
