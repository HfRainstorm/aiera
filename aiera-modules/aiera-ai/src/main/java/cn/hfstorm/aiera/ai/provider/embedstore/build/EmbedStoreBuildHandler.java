package cn.hfstorm.aiera.ai.provider.embedstore.build;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import org.springframework.ai.vectorstore.VectorStore;

/**
 * @author : hmy
 * @date : 2025/2/20
 */
public interface EmbedStoreBuildHandler {


    /**
     * 判断是不是当前模型
     */
    boolean whetherCurrentEmbedStore(AigcEmbedStore embedStore);

    /**
     * basic check
     */
    boolean basicCheck(AigcEmbedStore embedStore);

    /**
     * streaming chat build
     */
    VectorStore doBuildEmbedStore(AigcEmbedStore embedStore);

    default VectorStore buildEmbedStore(AigcEmbedStore embedStore) {
        try {
            if (!whetherCurrentEmbedStore(embedStore)) {
                return null;
            }
            if (!basicCheck(embedStore)) {
                return null;
            }
            return doBuildEmbedStore(embedStore);

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }
}
