package cn.hfstorm.aiera.ai.biz.service;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.core.domain.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : hmy
 * @date : 2025/2/17 13:29
 */
public interface IAigcEmbedStoreService extends IService<AigcEmbedStore> {

    /**
     * 测试向量数据库连接
     *
     * @param data
     * @return
     */
    R<Boolean> testVectorDbConnection(AigcEmbedStore data);
}
