package cn.hfstorm.aiera.ai.provider;


import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.ai.provider.build.ModelBuildHandler;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hutool.core.util.ObjectUtil;
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
import java.util.Objects;


/**
 * model provider initialize
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class AiModelProviderInitialize implements ApplicationContextAware {

    private final AigcModelService aigcModelService;
    private final SpringContextHolder contextHolder;
    private List<ModelBuildHandler> modelBuildHandlers;
    private List<AigcModel> modelStore = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        init();
    }

    @Async
    public void init() {
        modelStore = new ArrayList<>();

        List<AigcModel> list = aigcModelService.list();
        list.forEach(model -> {
            if (Objects.equals(model.getBaseUrl(), "")) {
                model.setBaseUrl(null);
            }
            // Uninstall previously registered beans before registering them
            contextHolder.unregisterBean(model.getId());

            chatHandler(model);
            embeddingHandler(model);
            imageHandler(model);
        });

//        modelStore.forEach(i -> log.info("已成功注册模型：{} -- {}， 模型配置：{}", i.getProvider(), i.getType(), i));
    }

    private void chatHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.CHAT.name().equals(type)) {
                return;
            }
            modelBuildHandlers.forEach(x -> {
                ChatModel chatModel = x.buildStreamingChat(model);
                if (ObjectUtil.isNotEmpty(chatModel)) {
                    contextHolder.registerBean(model.getId(), chatModel);
                    modelStore.add(model);
                }

//                ChatLanguageModel languageModel = x.buildChatLanguageModel(model);
//                if (ObjectUtil.isNotEmpty(languageModel)) {
//                    contextHolder.registerBean(model.getId() + ModelConst.TEXT_SUFFIX, languageModel);
//                }
            });
        } catch (Exception e) {
            log.error("model 【 id: {} name: {}】streaming chat 配置报错", model.getId(), model.getName());
        }
    }

    private void embeddingHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.EMBEDDING.name().equals(type)) {
                return;
            }
//            modelBuildHandlers.forEach(x -> {
//                EmbeddingModel embeddingModel = x.buildEmbedding(model);
//                if (ObjectUtil.isNotEmpty(embeddingModel)) {
//                    contextHolder.registerBean(model.getId(), embeddingModel);
//                    modelStore.add(model);
//                }
//            });

        } catch (Exception e) {
            log.error("model 【id{} name{}】 embedding 配置报错", model.getId(), model.getName());
        }
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

