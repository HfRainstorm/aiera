package cn.hfstorm.aiera.ai.provider;


import cn.hfstorm.aiera.common.ai.event.ProviderRefreshEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProviderListener {

    private final ModelStoreFactory providerInitialize;
//    private final EmbeddingStoreFactory embeddingStoreInitialize;

    @EventListener
    public void providerEvent(ProviderRefreshEvent event) {
        log.info("refresh provider beans begin......");
        providerInitialize.init();
        log.info("refresh provider beans success......");
    }
//
//    @EventListener
//    public void providerEvent(EmbeddingRefreshEvent event) {
//        log.info("refresh embedding beans begin......");
//        embeddingStoreInitialize.init();
//        log.info("refresh embedding beans success......");
//    }
}


