package cn.hfstorm.aiera.ai.core.provider;

import cn.hfstorm.aiera.ai.biz.event.ProviderRefreshEvent;
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

    @Autowired
    ProviderInitialize providerInitialize;

    @EventListener
    public void providerEvent(ProviderRefreshEvent event) {
        log.info("refresh provider beans begin......");
        providerInitialize.init();
        log.info("refresh provider beans success......");
    }

}
