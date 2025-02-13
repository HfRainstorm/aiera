package cn.hfstorm.aiera.system.api;


import cn.hfstorm.aiera.system.api.domain.vo.RemoteDictDataVo;

import java.util.List;

/**
 * 字典服务
 *
 * @author hmy
 */
public interface RemoteDictService {

	/**
	 * 根据字典类型查询字典数据
	 *
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	List<RemoteDictDataVo> selectDictDataByType(String dictType);
}
