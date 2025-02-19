package cn.hfstorm.aiera.ai.api;

import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ProviderEnum;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author : hmy
 * @date : 2025/2/19
 */
public class AiEraOllamaApi extends OllamaApi implements AiEraModelApi {

    private AigcModel aigcModel;

    public AiEraOllamaApi(String baseUrl, AigcModel aigcModel) {
        super(baseUrl);
        this.aigcModel = aigcModel;
    }

    public AiEraOllamaApi(String baseUrl, AigcModel aigcModel, RestClient.Builder restClientBuilder,
                          WebClient.Builder webClientBuilder) {
        super(baseUrl, restClientBuilder, webClientBuilder);
        this.aigcModel = aigcModel;
    }


    @Override
    public AigcModel getAigcModel() {
        return aigcModel;
    }

    @Override
    public void setAigcModel(AigcModel aigcModel) {
        this.aigcModel = aigcModel;
    }

    @Override
    public String getModelProvider() {
        return ProviderEnum.OLLAMA.name();
    }
}