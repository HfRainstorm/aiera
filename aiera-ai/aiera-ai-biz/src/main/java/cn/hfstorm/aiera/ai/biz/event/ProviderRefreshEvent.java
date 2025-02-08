package cn.hfstorm.aiera.ai.biz.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author : hmy
 * @date : 2025/2/8 15:12
 */
public class ProviderRefreshEvent extends ApplicationEvent {
    private static final long serialVersionUID = 2608324904040141991L;

    public ProviderRefreshEvent(Object source) {
        super(source);
    }
}
