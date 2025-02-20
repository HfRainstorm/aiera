package cn.hfstorm.aiera.common.ai.vector.db;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.enums.VectorStoreTypeEnum;
import cn.hfstorm.aiera.common.core.domain.R;
import cn.hfstorm.aiera.common.core.utils.Utils;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.collection.HasCollectionParam;
import lombok.extern.slf4j.Slf4j;

/**
 * milvus 数据库
 *
 * @author : hmy
 * @date : 2025/2/18
 */
@Slf4j
public class MilvusConnect implements VectorStoreConnect {

    @Override
    public String getDbType() {
        return VectorStoreTypeEnum.MILVUS.name();
    }

    /**
     * 测试连接
     *
     * @return
     * @throws Exception
     */
    @Override
    public R<Boolean> testVectorDbConnection(AigcEmbedStore aigcEmbedStore) {
        // 构造连接参数
        ConnectParam.Builder connectBuilder =
                ConnectParam.newBuilder()
                        .withHost(aigcEmbedStore.getHost())
                        .withPort(aigcEmbedStore.getPort())
                        .withAuthorization(Utils.blank2String(aigcEmbedStore.getUsername(), ""), Utils.blank2String(aigcEmbedStore.getPassword(), ""))
                        .withDatabaseName(aigcEmbedStore.getDatabaseName());

        HasCollectionParam collectionParam =
                HasCollectionParam.newBuilder().withCollectionName(aigcEmbedStore.getTableName()).build();


        MilvusServiceClient milvusServiceClient = null;
        R<Boolean> v = new R<>();
        try {
            milvusServiceClient = new MilvusServiceClient(connectBuilder.build());

            io.milvus.param.R<Boolean> connectResult = milvusServiceClient.withRetry(getRetryTimes()) // 重试次数
                    .withTimeout(getTimeouts(), getTimeoutUnit()) // 超时时间
                    .hasCollection(collectionParam);

            return buildResult(connectResult.getData(), Utils.exception2String(connectResult.getException()),
                    connectResult.getStatus());
        } catch (Exception e) {
            log.error("Milvus连接失败", e);
            buildResult(false, e.getMessage(), -1);
        } finally {
            if (null != milvusServiceClient) {
                milvusServiceClient.close();
            }
        }

        return v;
    }

    private static R<Boolean> buildResult(boolean data, String msg, int status) {
        R<Boolean> v = new R<>();

        v.setMsg(msg);
        v.setData(data);
        v.setCode(status);
        return v;
    }


}
