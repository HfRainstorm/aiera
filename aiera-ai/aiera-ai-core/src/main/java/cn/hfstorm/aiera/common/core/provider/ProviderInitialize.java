package cn.hfstorm.aiera.common.core.provider;

import cn.hfstorm.aiera.common.core.provider.build.ModelBuildHandler;
import cn.hfstorm.aiera.common.core.component.SpringContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;

/**
 * model provider initialize
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class ProviderInitialize implements ApplicationContextAware {
    //    private final AigcModelService aigcModelService;
    private final SpringContextHolder contextHolder;
    private List<ModelBuildHandler> modelBuildHandlers;
    private List<ChatModel> modelStore = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        init();
    }

    @Async
    public void init() {
        modelStore = new ArrayList<>();

        List<ChatModel> list = new ArrayList<>();
        list.forEach(model -> {
            System.out.println(model);
        });

    }

}
