package cn.hfstorm.aiera.ai.biz.service;

import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * aigc model 服务层
 */
public interface IAigcModelService extends IService<AigcModel> {

    List<AigcModel> getChatModels();

    List<AigcModel> getImageModels();

    List<AigcModel> getEmbeddingModels();

    List<AigcModel> list(AigcModel data);

    Page<AigcModel> page(AigcModel data, PageQuery queryPage);

    AigcModel selectById(String id);

}
