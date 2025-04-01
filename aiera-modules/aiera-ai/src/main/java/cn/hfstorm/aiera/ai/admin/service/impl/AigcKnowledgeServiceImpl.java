package cn.hfstorm.aiera.ai.admin.service.impl;

import cn.hfstorm.aiera.ai.admin.mapper.AigcDocsMapper;
import cn.hfstorm.aiera.ai.admin.mapper.AigcKnowledgeMapper;
import cn.hfstorm.aiera.ai.admin.service.IAigcKnowledgeService;
import cn.hfstorm.aiera.common.ai.domain.AigcDocs;
import cn.hfstorm.aiera.common.ai.domain.AigcKnowledge;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author hmy
 * @since 2025/2/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AigcKnowledgeServiceImpl extends ServiceImpl<AigcKnowledgeMapper, AigcKnowledge> implements IAigcKnowledgeService {

    private final AigcDocsMapper aigcDocsMapper;

    @Override
    @Transactional
    public void addDocs(AigcDocs data) {
        data.setCreateTime(new Date());
        aigcDocsMapper.insert(data);
    }

    @Override
    @Transactional
    public void updateDocs(AigcDocs data) {
        aigcDocsMapper.updateById(data);
    }

}

