package cn.hfstorm.aiera.ai.util;

import cn.hfstorm.aiera.ai.provider.embedstore.build.BaseEmbedStoreBuildHandler;
import cn.hfstorm.aiera.common.ai.domain.AigcKnowledge;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : hmy
 * @since : 2025/4/1
 */
public class AiLocalCache {


    /**
     * 模型id与模型对象配置信息缓存
     */
    public static Map<String, ChatModel> CHAT_MODEL_ID_TO_OBJ = new ConcurrentHashMap<>();
    public static Map<String, EmbeddingModel> EMBEDDING_MODEL_ID_TO_OBJ = new ConcurrentHashMap<>();


    /**
     * 向量数据库类型 映射向量数据库客户端
     */
    public static Map<String, BaseEmbedStoreBuildHandler> VECTOR_TYPE_TO_CLIENT_HANDLER = new ConcurrentHashMap<>();


    /**
     * 知识库id与知识库对象缓存
     */
    public static Map<String, AigcKnowledge> KNOWLEDGE_CACHEMAP = new ConcurrentHashMap<>();
}
