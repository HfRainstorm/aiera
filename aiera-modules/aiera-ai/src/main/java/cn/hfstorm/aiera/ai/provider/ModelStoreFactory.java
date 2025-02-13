package cn.hfstorm.aiera.ai.provider;

import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hfstorm.aiera.common.ai.provider.build.ModelBuildHandler;
import cn.hfstorm.aiera.common.core.consts.ModelConst;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hmy
 */
@Configuration
@Slf4j
public class ModelStoreFactory {

    @Autowired
    private AigcModelService aigcModelService;
    @Autowired
    private List<ModelBuildHandler> modelBuildHandlers;

    private final List<AigcModel> modelStore = new ArrayList<>();
    private final Map<String, ChatModel> streamingChatMap = new ConcurrentHashMap<>();
    private final Map<String, ChatModel> chatLanguageMap = new ConcurrentHashMap<>();
    private final Map<String, EmbeddingModel> embeddingModelMap = new ConcurrentHashMap<>();
    private final Map<String, ImageModel> imageModelMap = new ConcurrentHashMap<>();

    @Async
    @PostConstruct
    public void init() {
        modelStore.clear();
        streamingChatMap.clear();
        chatLanguageMap.clear();
        embeddingModelMap.clear();
        imageModelMap.clear();

        List<AigcModel> list = aigcModelService.list();
        list.forEach(model -> {
            if (Objects.equals(model.getBaseUrl(), "")) {
                model.setBaseUrl(null);
            }

            chatHandler(model);
            embeddingHandler(model);
            imageHandler(model);
        });

        modelStore.forEach(i -> log.info("已成功注册模型：{} -- {}， 模型配置：{}", i.getProvider(), i.getType(), i));
    }

    private void chatHandler(AigcModel model) {
        try {
            String type = model.getType();
            if (!ModelTypeEnum.CHAT.name().equals(type)) {
                return;
            }
            modelBuildHandlers.forEach(x -> {
                ChatModel streamingChatLanguageModel = x.buildStreamingChat(model);
                if (ObjectUtil.isNotEmpty(streamingChatLanguageModel)) {
                    streamingChatMap.put(model.getId(), streamingChatLanguageModel);
                    modelStore.add(model);
                }

                ChatModel languageModel = x.buildChatLanguageModel(model);
                if (ObjectUtil.isNotEmpty(languageModel)) {
                    chatLanguageMap.put(model.getId() + ModelConst.TEXT_SUFFIX, languageModel);
                }
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
            modelBuildHandlers.forEach(x -> {
                EmbeddingModel embeddingModel = x.buildEmbedding(model);
                if (ObjectUtil.isNotEmpty(embeddingModel)) {
                    embeddingModelMap.put(model.getId(), embeddingModel);
                    modelStore.add(model);
                }
            });

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
            modelBuildHandlers.forEach(x -> {
                ImageModel imageModel = x.buildImage(model);
                if (ObjectUtil.isNotEmpty(imageModel)) {
                    imageModelMap.put(model.getId(), imageModel);
                    modelStore.add(model);
                }
            });
        } catch (Exception e) {
            log.error("model 【id{} name{}】 image 配置报错", model.getId(), model.getName());
        }
    }

    public ChatModel getStreamingChatModel(String modelId) {
        return streamingChatMap.get(modelId);
    }

    public boolean containsStreamingChatModel(String modelId) {
        return streamingChatMap.containsKey(modelId);
    }

    public ChatModel getChatLanguageModel(String modelId) {
        return chatLanguageMap.get(modelId + ModelConst.TEXT_SUFFIX);
    }

    public boolean containsChatLanguageModel(String modelId) {
        return chatLanguageMap.containsKey(modelId + ModelConst.TEXT_SUFFIX);
    }

    public EmbeddingModel getEmbeddingModel(String modelId) {
        return embeddingModelMap.get(modelId);
    }

    public boolean containsEmbeddingModel(String modelId) {
        return embeddingModelMap.containsKey(modelId);
    }

    public ImageModel getImageModel(String modelId) {
        return imageModelMap.get(modelId);
    }

    public boolean containsImageModel(String modelId) {
        return imageModelMap.containsKey(modelId);
    }
}
