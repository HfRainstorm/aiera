package cn.hfstorm.aiera.ai.admin.service.impl;

import cn.hfstorm.aiera.ai.admin.mapper.AigcVectorStoreMapper;
import cn.hfstorm.aiera.ai.admin.service.IAigcEmbedStoreService;
import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.vector.VectorStoreConnectFactory;
import cn.hfstorm.aiera.common.ai.vector.db.VectorStoreConnect;
import cn.hfstorm.aiera.common.core.domain.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : hmy
 * @date : 2025/2/17
 */
@Service
@RequiredArgsConstructor
public class AigcEmbedStoreServiceImpl extends ServiceImpl<AigcVectorStoreMapper, AigcEmbedStore> implements IAigcEmbedStoreService {
    @Override
    public R<Boolean> testVectorDbConnection(AigcEmbedStore data) {
        VectorStoreConnect vectorStoreConnect = VectorStoreConnectFactory.getVectorStoreConnect(data);
        if (null != vectorStoreConnect) {
            return vectorStoreConnect.testVectorDbConnection(data);
        }
        return R.fail("不支持的向量库类型");
    }
}