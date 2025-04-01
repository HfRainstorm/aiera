package cn.hfstorm.aiera.ai.admin.service;

import cn.hfstorm.aiera.common.ai.domain.AigcDocs;
import cn.hfstorm.aiera.common.ai.domain.AigcKnowledge;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hmy
 * @since 2025/2/15
 */
public interface IAigcKnowledgeService extends IService<AigcKnowledge> {

    void addDocs(AigcDocs data);

    void updateDocs(AigcDocs data);

}

