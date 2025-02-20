package cn.hfstorm.aiera.common.ai.vector;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.enums.VectorStoreTypeEnum;
import cn.hfstorm.aiera.common.ai.vector.db.MilvusConnect;
import cn.hfstorm.aiera.common.ai.vector.db.VectorStoreConnect;

/**
 * @author : hmy
 * @date : 2025/2/18
 */
public class VectorStoreConnectFactory {


    /**
     * 获取向量库连接
     *
     * @param aigcEmbedStore
     * @return
     */
    public static VectorStoreConnect getVectorStoreConnect(AigcEmbedStore aigcEmbedStore) {

        if (VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(aigcEmbedStore.getProvider())) {
            return new MilvusConnect();
        }

        return null;
    }

}
