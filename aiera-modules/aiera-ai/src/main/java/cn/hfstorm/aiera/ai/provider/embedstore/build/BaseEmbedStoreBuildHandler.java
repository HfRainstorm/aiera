package cn.hfstorm.aiera.ai.provider.embedstore.build;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;

/**
 * @author : hmy
 * @date : 2025/2/20
 */
public abstract class BaseEmbedStoreBuildHandler {

    private Object vectorDbClient;

    /**
     * 判断是不是当前模型
     */
    abstract boolean whetherCurrentEmbedStore(AigcEmbedStore embedStore);
    /**
     * 判断是不是当前模型
     */
    public abstract String getBeanName();


    /**
     * basic check
     */
    protected abstract boolean basicCheck(AigcEmbedStore embedStore);

    /**
     * streaming chat build
     */
    protected abstract BaseEmbedStoreBuildHandler buildVectorDbClientHandler(AigcEmbedStore embedStore);

    /**
     * streaming chat build
     */
    protected abstract VectorStore doBuildEmbedStore(EmbeddingModel embeddingModel);

    public BaseEmbedStoreBuildHandler doBuildVectorDbClientHandler(AigcEmbedStore embedStore) {
        try {
            if (!whetherCurrentEmbedStore(embedStore)) {
                return null;
            }
            if (!basicCheck(embedStore)) {
                return null;
            }
            return buildVectorDbClientHandler(embedStore);

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }


    public VectorStore buildEmbedStore(EmbeddingModel embeddingModel) {
        try {
            return doBuildEmbedStore(embeddingModel);
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }

    public Object getVectorDbClient() {
        return vectorDbClient;
    }

    protected void setVectorDbClient(Object vectorDbClient) {
        this.vectorDbClient = vectorDbClient;
    }
}
