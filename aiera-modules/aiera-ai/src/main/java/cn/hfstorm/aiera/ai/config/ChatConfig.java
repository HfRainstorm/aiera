//package cn.hfstorm.aiera.ai.config;
//
//
//import org.springframework.ai.autoconfigure.ollama.OllamaChatProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ChatConfig {
//    // OpenAI 配置
//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(
//            prefix = "spring.ai.ollama.chat",
//            name = {"enabled"},
//            havingValue = "true",
//            matchIfMissing = true
//    )
//    public OllamaChatProperties openAiProperties() {
//        return new OllamaChatProperties();
//    }
//
////    // Anthropic 配置
////    @Bean
////    @ConfigurationProperties(prefix = "spring.ai.anthropic")
////    public AnthropicProperties anthropicProperties() {
////        return new AnthropicProperties();
////    }
//}
