package cn.hfstorm.aiera.common.ai.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * @author : hmy
 * @date : 2025/2/8 15:12
 */
public class ProviderRefreshEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = 2608324904040141991L;

    public ProviderRefreshEvent(Object source) {
        super(source);
    }
}