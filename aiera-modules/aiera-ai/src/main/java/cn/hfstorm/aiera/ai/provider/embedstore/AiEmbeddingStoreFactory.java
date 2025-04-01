package cn.hfstorm.aiera.ai.provider.embedstore;

import cn.hfstorm.aiera.ai.admin.service.IAigcEmbedStoreService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.ai.provider.embedstore.build.BaseEmbedStoreBuildHandler;
import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.hfstorm.aiera.ai.util.AiLocalCache.VECTOR_TYPE_TO_CLIENT_HANDLER;

/**
 * 向量数据库注册
 *
 * @author hmy
 * @since 2024/10/28
 */
@Slf4j
@Component
@AllArgsConstructor
public class AiEmbeddingStoreFactory {

    private final IAigcEmbedStoreService aigcEmbedStoreService;
    private List<BaseEmbedStoreBuildHandler> baseEmbedStoreBuildHandlers;
    private SpringContextHolder contextHolder;


    public void init() {
        VECTOR_TYPE_TO_CLIENT_HANDLER.clear();
        List<AigcEmbedStore> list = aigcEmbedStoreService.list();
        list.forEach(embed -> {
            try {
                BaseEmbedStoreBuildHandler vectorDbClientHandler = buildVectorDbClientHandler(embed);
                if (null != vectorDbClientHandler) {
                    VECTOR_TYPE_TO_CLIENT_HANDLER.putIfAbsent(embed.getId(), vectorDbClientHandler);
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.error("向量数据库初始化失败：[{}] --- [{}]，数据库配置信息：[{}]", embed.getName(), embed.getProvider(), embed);
            }
        });
    }


    /**
     * 构建向量数据库客户端
     *
     * @param embedStore
     * @return
     */
    public BaseEmbedStoreBuildHandler buildVectorDbClientHandler(AigcEmbedStore embedStore) {
        for (BaseEmbedStoreBuildHandler baseEmbedStoreBuildHandler : baseEmbedStoreBuildHandlers) {
            BaseEmbedStoreBuildHandler vectorDbClientHandler =
                    baseEmbedStoreBuildHandler.doBuildVectorDbClientHandler(embedStore);
            if (null != vectorDbClientHandler) {
                return vectorDbClientHandler;
            }
        }

        return null;
    }


    /**
     * 获取 VectorStore
     *
     * @param storeId        向量库数据id
     * @param embeddingModel 嵌入模型
     * @return
     */
    public VectorStore getVectorStore(String storeId, EmbeddingModel embeddingModel) {
        BaseEmbedStoreBuildHandler baseEmbedStoreBuildHandler = VECTOR_TYPE_TO_CLIENT_HANDLER.get(storeId);
        if (null == baseEmbedStoreBuildHandler) {
            throw new ChatException("没有匹配到向量数据库客户端，请检查配置！");
        }

        VectorStore vectorStore = baseEmbedStoreBuildHandler.buildEmbedStore(embeddingModel);
        if (null == vectorStore) {
            throw new ChatException("构建向量数据库失败，请检查配置！");
        }

        return vectorStore;
    }
}
