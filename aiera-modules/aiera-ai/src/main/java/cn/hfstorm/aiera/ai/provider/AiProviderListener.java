package cn.hfstorm.aiera.ai.provider;


import cn.hfstorm.aiera.ai.event.ModelProviderRefreshEvent;
import cn.hfstorm.aiera.ai.provider.model.ModelProviderFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * model provider listener
 *
 * @author : hmy
 * @date : 2025/2/8 15:10
 */

@Slf4j
@Component
@AllArgsConstructor
public class AiProviderListener {
    private ModelProviderFactory modelProviderFactory;

//    private final AiModelProviderInitialize aiModelProviderInitialize;
//    private final VectorStoreInitialize vectorStoreInitialize;
//    private final EmbeddingStoreFactory embeddingStoreInitialize;

    @EventListener
    public void providerEvent(ModelProviderRefreshEvent event) {
        log.info("refresh model provider beans begin......");
        modelProviderFactory.init();
        log.info("refresh model provider beans success......");
    }
//    @EventListener
//    public void providerEvent(VectorProviderRefreshEvent event) {
//        log.info("refresh vector provider beans begin......");
//        vectorStoreInitialize.init();
//        log.info("refresh vector provider beans success......");
//    }
//
//    @EventListener
//    public void providerEvent(EmbeddingRefreshEvent event) {
//        log.info("refresh embedding beans begin......");
//        embeddingStoreInitialize.init();
//        log.info("refresh embedding beans success......");
//    }
}


