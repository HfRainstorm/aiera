package cn.hfstorm.aiera.ai.biz.mapper;

import cn.hfstorm.aiera.ai.biz.entity.AigcFileStorage;
import cn.hfstorm.aiera.common.core.annotation.DbChoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hmy
 * @since 2025/2/12
 */
@Mapper
@DbChoice
public interface AigcFileStorageMapper extends BaseMapper<AigcFileStorage> {

}
