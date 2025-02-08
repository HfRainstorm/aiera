package cn.hfstorm.aiera.ai.biz.service;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.common.core.utils.QueryPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * AI Generated Content Service
 *
 * @author : hmy
 * @date : 2025/2/8 15:19
 */
public interface AigcModelService extends IService<AigcModel> {


    List<AigcModel> getChatModels();

    List<AigcModel> list(AigcModel data);

    Page<AigcModel> page(AigcModel data, QueryPage queryPage);

    AigcModel selectById(String id);
}
