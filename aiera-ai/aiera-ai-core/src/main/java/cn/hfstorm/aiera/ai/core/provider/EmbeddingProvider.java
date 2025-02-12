package cn.hfstorm.aiera.ai.core.provider;

import cn.hfstorm.aiera.ai.core.enums.ProviderEnum;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author : hmy
 * @date : 2025/2/8 10:32
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmbeddingProvider {

    private final ApplicationContext context;
//
//    public static DocumentSplitter splitter(String modelName, String modelProvider) {
//        if (ProviderEnum.OPENAI.name().equals(modelProvider)) {
//            return DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(modelName));
//        }
//        return DocumentSplitters.recursive(100, 0);
//    }
//
//    public EmbeddingModel getEmbeddingModel(List<String> knowledgeIds) {
//        List<String> storeIds = new ArrayList<>();
//        knowledgeIds.forEach(id -> {
//            if (context.containsBean(id)) {
//                AigcKnowledge data = (AigcKnowledge) context.getBean(id);
//                if (data.getEmbedModelId() != null) {
//                    storeIds.add(data.getEmbedModelId());
//                }
//            }
//        });
//        if (storeIds.isEmpty()) {
//            throw new ChatException("知识库缺少Embedding Model配置，请先检查配置");
//        }
//
//        HashSet<String> filterIds = new HashSet<>(storeIds);
//        if (filterIds.size() > 1) {
//            throw new ChatException("存在多个不同Embedding Model的知识库，请先检查配置");
//        }
//
//        return (EmbeddingModel) context.getBean(storeIds.get(0));
//    }
//
//    public EmbeddingModel getEmbeddingModel(String knowledgeId) {
//        if (context.containsBean(knowledgeId)) {
//            AigcKnowledge data = (AigcKnowledge) context.getBean(knowledgeId);
//            if (context.containsBean(data.getEmbedModelId())) {
//                return (EmbeddingModel) context.getBean(data.getEmbedModelId());
//            }
//        }
//        throw new ChatException("没有找到匹配的Embedding向量数据库");
//    }
//
//    public EmbeddingStore<TextSegment> getEmbeddingStore(String knowledgeId) {
//        if (context.containsBean(knowledgeId)) {
//            AigcKnowledge data = (AigcKnowledge) context.getBean(knowledgeId);
//            if (context.containsBean(data.getEmbedStoreId())) {
//                return (EmbeddingStore<TextSegment>) context.getBean(data.getEmbedStoreId());
//            }
//        }
//        throw new ChatException("没有找到匹配的Embedding向量数据库");
//    }
//
//    public EmbeddingStore<TextSegment> getEmbeddingStore(List<String> knowledgeIds) {
//        List<String> storeIds = new ArrayList<>();
//        knowledgeIds.forEach(id -> {
//            if (context.containsBean(id)) {
//                AigcKnowledge data = (AigcKnowledge) context.getBean(id);
//                if (data.getEmbedStoreId() != null) {
//                    storeIds.add(data.getEmbedStoreId());
//                }
//            }
//        });
//        if (storeIds.isEmpty()) {
//            throw new ChatException("知识库缺少Embedding Store配置，请先检查配置");
//        }
//
//        HashSet<String> filterIds = new HashSet<>(storeIds);
//        if (filterIds.size() > 1) {
//            throw new ChatException("存在多个不同Embedding Store数据源的知识库，请先检查配置");
//        }
//
//        return (EmbeddingStore<TextSegment>) context.getBean(storeIds.get(0));
//    }

}
