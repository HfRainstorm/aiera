package cn.hfstorm.aiera.ai.provider.model;

import cn.hfstorm.aiera.ai.admin.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.model.Model;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.hfstorm.aiera.ai.util.AiLocalCache.EMBEDDING_MODEL_ID_TO_OBJ;
import static cn.hfstorm.aiera.ai.util.AiLocalCache.CHAT_MODEL_ID_TO_OBJ;

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
    private List<AigcModel> models = new ArrayList<>();

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

        models.forEach(aigcModel -> log.info("已成功注册Embedding模型：{} -- {}， 模型配置：{}",
                aigcModel.getProvider(), aigcModel.getType(), aigcModel.getId()));
        models.clear();
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
                T aiModel = x.doBuildModel(model);
                if (ObjectUtil.isNotEmpty(aiModel)) {
                    ModelTypeEnum modelType = x.getModelType();
                    contextHolder.registerBean(model.getId(), aiModel);

                    models.add(model);

                    // add cache
                    if (modelType.equals(ModelTypeEnum.CHAT)) {
                        CHAT_MODEL_ID_TO_OBJ.put(model.getId(), (ChatModel) aiModel);
                    }
                    if (modelType.equals(ModelTypeEnum.EMBEDDING)) {
                        EMBEDDING_MODEL_ID_TO_OBJ.put(model.getId(), (EmbeddingModel) aiModel);
                    }

                }
                return aiModel;
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


    public static EmbeddingModel getEmbeddingModel(String modelId) {
        return EMBEDDING_MODEL_ID_TO_OBJ.get(modelId);
    }

    public static boolean containsEmbeddingModel(String modelId) {
        return EMBEDDING_MODEL_ID_TO_OBJ.containsKey(modelId);
    }

    public static ChatModel getChatModel(String modelId) {
        return CHAT_MODEL_ID_TO_OBJ.get(modelId);
    }

    public static boolean containsChatModel(String modelId) {
        return CHAT_MODEL_ID_TO_OBJ.containsKey(modelId);
    }
}
