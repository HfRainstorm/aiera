package cn.hfstorm.aiera.ai.provider.embedstore.build;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.enums.EmbedErrorEnum;
import cn.hfstorm.aiera.common.ai.enums.VectorStoreTypeEnum;
import cn.hfstorm.aiera.common.ai.exception.EmbedException;
import cn.hfstorm.aiera.common.core.utils.Utils;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.collection.HasCollectionParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;

/**
 * @author : hmy
 * @date : 2025/2/20
 */
public class MilvusEmbedStoreBuildHandler extends BaseEmbedStoreBuildHandler {
    @Override
    public boolean whetherCurrentEmbedStore(AigcEmbedStore embedStore) {
        return VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(embedStore.getProvider());
    }

    @Override
    public String getBeanName() {
        return VectorStoreTypeEnum.MILVUS.name();
    }


    @Override
    public boolean basicCheck(AigcEmbedStore embedStore) {
        if (StringUtils.isBlank(embedStore.getHost()) || StringUtils.isBlank(embedStore.getDatabaseName())) {
            // change default base url
            throw new EmbedException(String.valueOf(EmbedErrorEnum.BASE_URL_IS_NULL.getErrorCode()),
                    EmbedErrorEnum.BASE_URL_IS_NULL.getErrorDesc(VectorStoreTypeEnum.MILVUS.name(),
                            embedStore.getName()));
        }
        return VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(embedStore.getProvider());
    }

    @Override
    public MilvusEmbedStoreBuildHandler buildVectorDbClientHandler(AigcEmbedStore embedStore) {
        // 构造连接参数
        ConnectParam.Builder connectBuilder =
                ConnectParam.newBuilder()
                        .withHost(embedStore.getHost())
                        .withPort(embedStore.getPort())
                        .withAuthorization(Utils.blank2String(embedStore.getUsername(), ""),
                                Utils.blank2String(embedStore.getPassword(), ""))
                        .withDatabaseName(embedStore.getDatabaseName());

        HasCollectionParam collectionParam =
                HasCollectionParam.newBuilder().withCollectionName(embedStore.getTableName()).build();

        MilvusServiceClient milvusServiceClient = new MilvusServiceClient(connectBuilder.build());
        milvusServiceClient.hasCollection(collectionParam);
        setVectorDbClient(milvusServiceClient);
        return this;
    }

    /**
     * 构建向量库
     *
     * @param embeddingModel
     * @return
     */
    @Override
    public VectorStore doBuildEmbedStore(EmbeddingModel embeddingModel) {
        return MilvusVectorStore.builder((MilvusServiceClient) getVectorDbClient(), embeddingModel)
                .indexType(IndexType.IVF_FLAT)
                .metricType(MetricType.COSINE)
                .batchingStrategy(new TokenCountBatchingStrategy())
                .initializeSchema(true)
                .build();
    }
}
