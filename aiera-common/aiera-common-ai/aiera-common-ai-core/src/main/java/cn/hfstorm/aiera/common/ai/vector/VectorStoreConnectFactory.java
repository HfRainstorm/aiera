package cn.hfstorm.aiera.common.ai.vector;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.enums.VectorStoreTypeEnum;
import cn.hfstorm.aiera.common.ai.vector.db.MilvusConnect;
import cn.hfstorm.aiera.common.ai.vector.db.VectorStoreConnect;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/18
 */
@Component
public class VectorStoreConnectFactory {


    /**
     * 获取向量库连接
     *
     * @param aigcEmbedStore
     * @return
     */
    public VectorStoreConnect getVectorStoreConnect(AigcEmbedStore aigcEmbedStore) {

        if (VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(aigcEmbedStore.getProvider())) {
            return new MilvusConnect();
        }

        return null;
    }

}
