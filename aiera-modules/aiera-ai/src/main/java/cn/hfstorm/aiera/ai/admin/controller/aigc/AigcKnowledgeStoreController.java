package cn.hfstorm.aiera.ai.admin.controller.aigc;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hfstorm.aiera.ai.admin.mapper.AigcDocsMapper;
import cn.hfstorm.aiera.ai.admin.service.IAigcEmbedStoreService;
import cn.hfstorm.aiera.ai.admin.service.IAigcKnowledgeService;
import cn.hfstorm.aiera.ai.admin.service.IAigcModelService;
import cn.hfstorm.aiera.ai.provider.knowledge.AiKnowledgeFactory;
import cn.hfstorm.aiera.common.ai.domain.AigcDocs;
import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.domain.AigcKnowledge;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.core.domain.R;
import cn.hfstorm.aiera.common.log.annotation.Log;
import cn.hfstorm.aiera.common.mybatis.core.page.PageQuery;
import cn.hfstorm.aiera.common.mybatis.core.util.MybatisUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * aigc model
 *
 * @author hmy
 */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/aigc/knowledge")
public class AigcKnowledgeStoreController {

    private final IAigcKnowledgeService kbService;
    private final AigcDocsMapper docsMapper;
    private final IAigcEmbedStoreService embedStoreService;
    private final IAigcModelService modelService;
    private final AiKnowledgeFactory knowledgeStore;

    @GetMapping("/list")
    public R<List<AigcKnowledge>> list(AigcKnowledge data) {
        List<AigcKnowledge> list =
                kbService.list(Wrappers.<AigcKnowledge>lambdaQuery().orderByDesc(AigcKnowledge::getCreateTime));
        build(list);
        return R.ok(list);
    }

    private void build(List<AigcKnowledge> records) {
        Map<String, List<AigcEmbedStore>> embedStoreMap =
                embedStoreService.list().stream().collect(Collectors.groupingBy(AigcEmbedStore::getId));
        Map<String, List<AigcModel>> embedModelMap =
                modelService.list().stream().collect(Collectors.groupingBy(AigcModel::getId));
        Map<String, List<AigcDocs>> docsMap =
                docsMapper.selectList(Wrappers.lambdaQuery()).stream().collect(Collectors.groupingBy(AigcDocs::getKnowledgeId));
        records.forEach(item -> {
            List<AigcDocs> docs = docsMap.get(item.getId());
            if (docs != null) {
                item.setDocsNum(docs.size());
                item.setTotalSize(docs.stream().filter(d -> d.getSize() != null).mapToLong(AigcDocs::getSize).sum());
            }
            if (item.getEmbedModelId() != null) {
                List<AigcModel> list = embedModelMap.get(item.getEmbedModelId());
                item.setEmbedModel(list == null ? null : list.get(0));
            }
            if (item.getEmbedStoreId() != null) {
                List<AigcEmbedStore> list = embedStoreMap.get(item.getEmbedStoreId());
                item.setEmbedStore(list == null ? null : list.get(0));
            }
        });
    }

    @GetMapping("/page")
    public R list(AigcKnowledge data, PageQuery queryPage) {
        Page<AigcKnowledge> page = new Page<>(queryPage.getPageNum(), queryPage.getPageSize());
        LambdaQueryWrapper<AigcKnowledge> queryWrapper = Wrappers.<AigcKnowledge>lambdaQuery()
                .like(!StrUtil.isBlank(data.getName()), AigcKnowledge::getName, data.getName())
                .orderByDesc(AigcKnowledge::getCreateTime);
        Page<AigcKnowledge> iPage = kbService.page(page, queryWrapper);

        build(iPage.getRecords());

        return R.ok(MybatisUtils.getData(iPage));
    }

    @GetMapping("/{id}")
    public R<AigcKnowledge> findById(@PathVariable String id) {
        AigcKnowledge knowledge = kbService.getById(id);
        if (knowledge.getEmbedStoreId() != null) {
            knowledge.setEmbedStore(embedStoreService.getById(knowledge.getEmbedStoreId()));
        }
        if (knowledge.getEmbedModelId() != null) {
            knowledge.setEmbedModel(modelService.getById(knowledge.getEmbedModelId()));
        }
        return R.ok(knowledge);
    }

    @PostMapping
    @Log(title = "新增知识库")
    @SaCheckPermission("aigc:knowledge:add")
    public R add(@RequestBody AigcKnowledge data) {
        data.setCreateTime(String.valueOf(System.currentTimeMillis()));
        kbService.save(data);
        knowledgeStore.init();
        return R.ok();
    }
//
//    @PutMapping
//    @Log(title = "更新知识库")
//    @SaCheckPermission("aigc:knowledge:update")
//    public R update(@RequestBody AigcKnowledge data) {
//        kbService.updateById(data);
//        knowledgeStore.init();
//        return R.ok();
//    }
//
//    @DeleteMapping("/{id}")
//    @Log(title = "删除知识库")
//    @SaCheckPermission("aigc:knowledge:delete")
//    public R delete(@PathVariable String id) {
//        kbService.removeKnowledge(id);
//        knowledgeStore.init();
//        return R.ok();
//    }
}