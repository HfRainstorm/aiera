package cn.hfstorm.aiera.ai.event;

import cn.hfstorm.aiera.ai.provider.model.AiModelStoreFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 模型刷新事件监听
 *
 * @author : hmy
 * @since : 2025/4/1
 */
@Slf4j
@Component
@AllArgsConstructor
public class ModelEventListener {


    private AiModelStoreFactory aiModelStoreFactory;

    @EventListener
    public void onModelRefreshEvent(ModelProviderRefreshEvent event) {
        log.info("refresh model provider beans begin. event: {}", event);
        if (event.isResult()) {
            aiModelStoreFactory.init();
        }
        log.info("refresh model provider beans success.");
    }
}
