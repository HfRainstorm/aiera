package cn.hfstorm.aiera.ai.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * @author : hmy
 * @date : 2025/2/8 15:12
 */
public class VectorProviderRefreshEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -5594499106263316869L;

    private boolean result;

    public VectorProviderRefreshEvent(Object source, boolean result) {
        super(source);
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}