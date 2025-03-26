package cn.hfstorm.aiera.ai.provider.model;

import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.model.Model;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : hmy
 * @date : 2025/2/21
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class AiModelStoreFactory {

    private final AigcModelService aigcModelService;
    private final SpringContextHolder contextHolder;
    private List<AiModelBuildService> chatModelBuildServices;
    private List<AigcModel> modelStore = new ArrayList<>();


    @Async
    @PostConstruct
    public void init() {
        List<AigcModel> list = aigcModelService.list();
        list.forEach(model -> {
            if (Objects.equals(model.getBaseUrl(), "")) {
                model.setBaseUrl(null);
            }
            // Uninstall previously registered beans before registering them
            contextHolder.unregisterBean(model.getId());

            aiModelHandler(model);
            imageHandler(model);
        });

        modelStore.forEach(i -> log.info("已成功注册模型：{} -- {}， 模型配置：{}", i.getProvider(), i.getType(), i));
    }


    /**
     * @param model
     */
    public <T extends Model> T aiModelHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.CHAT.name().equals(type)
                    && !ModelTypeEnum.EMBEDDING.name().equals(type)) {
                return null;
            }
            for (AiModelBuildService<T> x : chatModelBuildServices) {
                T chatModel = x.doBuildModel(model);
                if (ObjectUtil.isNotEmpty(chatModel)) {
                    contextHolder.registerBean(model.getId(), chatModel);
                    modelStore.add(model);
                }
                return chatModel;
            }

        } catch (Exception e) {
            log.error("model 【 id: {} name: {}】streaming chat 配置报错", model.getId(), model.getName());
        }
        return null;
    }

    private void imageHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.TEXT_IMAGE.name().equals(type)) {
                return;
            }
//            modelBuildHandlers.forEach(x -> {
//                ImageModel imageModel = x.buildImage(model);
//                if (ObjectUtil.isNotEmpty(imageModel)) {
//                    contextHolder.registerBean(model.getId(), imageModel);
//                    modelStore.add(model);
//                }
//            });
        } catch (Exception e) {
            log.error("model 【id{} name{}】 image 配置报错", model.getId(), model.getName());
        }
    }
}
