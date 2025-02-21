package cn.hfstorm.aiera.ai.provider.embedstore.build;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.enums.EmbedErrorEnum;
import cn.hfstorm.aiera.common.ai.enums.VectorStoreTypeEnum;
import cn.hfstorm.aiera.common.ai.exception.EmbedException;
import cn.hfstorm.aiera.common.core.utils.Utils;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.collection.HasCollectionParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.vectorstore.VectorStore;

/**
 * @author : hmy
 * @date : 2025/2/20
 */
public class MilvusEmbedStoreBuildHandler implements EmbedStoreBuildHandler {
    @Override
    public boolean whetherCurrentEmbedStore(AigcEmbedStore embedStore) {
        return VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(embedStore.getProvider());
    }

    @Override
    public boolean basicCheck(AigcEmbedStore embedStore) {
        if (StringUtils.isBlank(embedStore.getHost()) || StringUtils.isBlank(embedStore.getDatabaseName())) {
            // change default base url
            throw new EmbedException(String.valueOf(EmbedErrorEnum.BASE_URL_IS_NULL.getErrorCode()),
                    EmbedErrorEnum.BASE_URL_IS_NULL.getErrorDesc(VectorStoreTypeEnum.MILVUS.name(), embedStore.getName()));
        }
        return VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(embedStore.getProvider());
    }

    @Override
    public VectorStore doBuildEmbedStore(AigcEmbedStore aigcEmbedStore) {
        // 构造连接参数
        ConnectParam.Builder connectBuilder =
                ConnectParam.newBuilder()
                        .withHost(aigcEmbedStore.getHost())
                        .withPort(aigcEmbedStore.getPort())
                        .withAuthorization(Utils.blank2String(aigcEmbedStore.getUsername(), ""), Utils.blank2String(aigcEmbedStore.getPassword(), ""))
                        .withDatabaseName(aigcEmbedStore.getDatabaseName());

        HasCollectionParam collectionParam =
                HasCollectionParam.newBuilder().withCollectionName(aigcEmbedStore.getTableName()).build();

          new MilvusServiceClient(connectBuilder.build()).hasCollection(collectionParam);

          return null;
    }
}
