package cn.hfstorm.aiera.common.ai.vector.db;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.core.domain.R;

import java.util.concurrent.TimeUnit;

/**
 * @author : hmy
 * @date : 2025/2/18
 */
public interface VectorStoreConnect {

    String getDbType();

    default int getRetryTimes() {
        return 5;
    }

    default int getTimeouts() {
        return 20;
    }

    default TimeUnit getTimeoutUnit() {
        return TimeUnit.SECONDS;
    }

    /**
     * 测试连通性
     *
     * @return
     * @throws Exception
     */
    R<Boolean> testVectorDbConnection(AigcEmbedStore aigcEmbedStore);
}
