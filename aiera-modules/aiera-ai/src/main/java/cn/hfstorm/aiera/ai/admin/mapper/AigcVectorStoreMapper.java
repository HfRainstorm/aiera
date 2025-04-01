package cn.hfstorm.aiera.ai.admin.mapper;

import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ai模型
 *
 * @author hmy
 */
@Mapper
public interface AigcVectorStoreMapper extends BaseMapper<AigcEmbedStore> {
}
