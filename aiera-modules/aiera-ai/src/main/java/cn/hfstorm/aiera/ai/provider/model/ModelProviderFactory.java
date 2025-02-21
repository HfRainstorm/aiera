package cn.hfstorm.aiera.ai.provider.model;

import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.ai.provider.embedmodel.build.EmbedModelBuildHandler;
import cn.hfstorm.aiera.ai.provider.model.build.ModelBuildHandler;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : hmy
 * @date : 2025/2/21
 */
@Slf4j
@AllArgsConstructor
@Component
public class ModelProviderFactory {

    private final AigcModelService aigcModelService;
    private final SpringContextHolder contextHolder;
    private List<ModelBuildHandler> modelBuildHandlers;
    private List<EmbedModelBuildHandler> embedModelBuildHandlers;
    private List<AigcModel> modelStore = new ArrayList<>();


    public void init() {
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

        modelStore.forEach(i -> log.info("已成功注册模型：{} -- {}， 模型配置：{}", i.getProvider(), i.getType(), i));
    }


    /**
     * @param model
     */
    public ChatModel chatHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.CHAT.name().equals(type)) {
                return null;
            }
            for (ModelBuildHandler x : modelBuildHandlers) {
                ChatModel chatModel = x.buildStreamingChat(model);
                if (ObjectUtil.isNotEmpty(chatModel)) {
                    contextHolder.registerBean(model.getId(), chatModel);
                    modelStore.add(model);
                }

//                ChatLanguageModel languageModel = x.buildChatLanguageModel(model);
//                if (ObjectUtil.isNotEmpty(languageModel)) {
//                    contextHolder.registerBean(model.getId() + ModelConst.TEXT_SUFFIX, languageModel);
//                }
                return chatModel;
            }

        } catch (Exception e) {
            log.error("model 【 id: {} name: {}】streaming chat 配置报错", model.getId(), model.getName());
        }
        return null;
    }

    /**
     * 构造嵌入模型
     *
     * @param model
     */
    public EmbeddingModel embeddingHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.EMBEDDING.name().equals(type)) {
                return null;
            }
            embedModelBuildHandlers.forEach(x -> {
                EmbeddingModel embeddingModel = x.buildEmbedModel(model);
                if (ObjectUtil.isNotEmpty(embeddingModel)) {
                    contextHolder.registerBean(model.getId(), embeddingModel);
                    modelStore.add(model);
                }
            });

        } catch (Exception e) {
            log.error("model 【id{} name{}】 embedding 配置报错", model.getId(), model.getName());
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
