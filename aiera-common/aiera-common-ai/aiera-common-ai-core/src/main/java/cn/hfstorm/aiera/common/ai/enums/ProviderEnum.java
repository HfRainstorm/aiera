package cn.hfstorm.aiera.common.ai.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 供应商类型
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
@Getter
@AllArgsConstructor
public enum ProviderEnum {

    OPENAI("openai", "OpenAi"),
    AZURE_OPENAI("azureOpenai", "AzureOpenAi"),
    GEMINI("gemni", "Gemini"),
    OLLAMA("ollama", "Ollama"),
    CLAUDE("claude", "Claude"),
    Q_FAN("qFan", "QFan"),
    Q_WEN("qWen", "QWen"),
    ZHIPU("zhpu", "ZhiPu"),
    DEEPSEEK("deepseek", "DeepSeek"),
    ;

    /**
     * 供应商
     */
    private String supplier;
    /**
     * 描述
     */
    private String profiler;
}