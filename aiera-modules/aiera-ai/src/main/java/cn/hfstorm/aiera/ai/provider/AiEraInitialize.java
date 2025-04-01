package cn.hfstorm.aiera.ai.provider;

import cn.hfstorm.aiera.ai.provider.embedstore.AiEmbeddingStoreFactory;
import cn.hfstorm.aiera.ai.provider.knowledge.AiKnowledgeFactory;
import cn.hfstorm.aiera.ai.provider.model.AiModelStoreFactory;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

/**
 * @author : hmy
 * @since : 2025/4/1
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class AiEraInitialize {
    private AiModelStoreFactory aiModelStoreFactory;
    private AiEmbeddingStoreFactory aiEmbeddingStoreFactory;
    private AiKnowledgeFactory aiKnowledgeFactory;

    @Async
    @PostConstruct
    public void init() {
        log.info("ai knowledge start initialize.");
        aiKnowledgeFactory.init();
        log.info("ai knowledge initialize end.");

        log.info("ai model start initialize.");
        aiModelStoreFactory.init();
        log.info("ai model initialize end.");


        log.info("ai embed store start initialize.");
        aiEmbeddingStoreFactory.init();
        log.info("ai embed store initialize end.");
    }
}
