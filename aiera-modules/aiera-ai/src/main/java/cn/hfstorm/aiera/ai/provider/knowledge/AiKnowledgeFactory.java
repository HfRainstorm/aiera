package cn.hfstorm.aiera.ai.provider.knowledge;

import cn.hfstorm.aiera.ai.admin.service.IAigcEmbedStoreService;
import cn.hfstorm.aiera.ai.admin.service.IAigcKnowledgeService;
import cn.hfstorm.aiera.ai.admin.service.IAigcModelService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.ai.provider.embedstore.AiEmbeddingStoreFactory;
import cn.hfstorm.aiera.ai.provider.model.AiModelStoreFactory;
import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.domain.AigcKnowledge;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.hfstorm.aiera.ai.util.AiLocalCache.KNOWLEDGE_CACHEMAP;

/**
 * 知识库初始化
 *
 * @author hmy
 * @since 2025/2/15
 */
@Slf4j
@Component
@AllArgsConstructor
public class AiKnowledgeFactory {

    private final IAigcKnowledgeService knowledgeService;
    private final SpringContextHolder contextHolder;
    private final IAigcModelService modelService;
    private final IAigcEmbedStoreService embedStoreService;
    private AiEmbeddingStoreFactory aiEmbeddingStoreFactory;


    public void init() {
        KNOWLEDGE_CACHEMAP.clear();
        List<AigcKnowledge> list = knowledgeService.list();
        Map<String, List<AigcModel>> modelMap =
                modelService.list().stream().collect(Collectors.groupingBy(AigcModel::getId));
        Map<String, List<AigcEmbedStore>> storeMap =
                embedStoreService.list().stream().collect(Collectors.groupingBy(AigcEmbedStore::getId));
        list.forEach(know -> {
            if (null != know.getEmbedModelId()) {
                List<AigcModel> models = modelMap.get(know.getEmbedModelId());
                know.setEmbedModel(models == null ? null : models.get(0));
            }
            if (null != know.getEmbedStoreId()) {
                List<AigcEmbedStore> stores = storeMap.get(know.getEmbedStoreId());
                know.setEmbedStore(stores == null ? null : stores.get(0));
            }
            KNOWLEDGE_CACHEMAP.put(know.getId(), know);
        });
    }


    /**
     * 获取嵌入模型
     *
     * @param knowledgeId 知识库id
     * @return
     */
    public EmbeddingModel getEmbeddingModel(String knowledgeId) {
        if (containsKnowledge(knowledgeId)) {
            AigcKnowledge data = getKnowledge(knowledgeId);
            if (AiModelStoreFactory.containsEmbeddingModel(data.getEmbedModelId())) {
                return AiModelStoreFactory.getEmbeddingModel(data.getEmbedModelId());
            }
        }
        throw new ChatException("没有找到匹配的Embedding向量数据库");
    }

    /**
     * 获取嵌入模型
     *
     * @param knowledgeId 知识库id
     * @return
     */
    public VectorStore getVectorStore(String knowledgeId, EmbeddingModel embeddingModel) {
        if (containsKnowledge(knowledgeId)) {
            AigcKnowledge data = getKnowledge(knowledgeId);
            return aiEmbeddingStoreFactory.getVectorStore(data.getEmbedStoreId(), embeddingModel);
        }
        throw new ChatException("没有找到匹配的Embedding向量数据库");
    }


    public AigcKnowledge getKnowledge(String knowledgeId) {
        return KNOWLEDGE_CACHEMAP.get(knowledgeId);
    }

    public boolean containsKnowledge(String knowledgeId) {
        return KNOWLEDGE_CACHEMAP.containsKey(knowledgeId);
    }
}
