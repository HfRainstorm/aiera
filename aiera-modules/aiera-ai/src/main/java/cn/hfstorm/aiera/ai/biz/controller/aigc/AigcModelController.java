package cn.hfstorm.aiera.ai.biz.controller.aigc;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hfstorm.aiera.common.ai.biz.domain.AigcModel;
import cn.hfstorm.aiera.ai.biz.service.IAigcModelService;
import cn.hfstorm.aiera.ai.event.ModelProviderRefreshEvent;
import cn.hfstorm.aiera.common.core.domain.R;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.common.log.annotation.Log;
import cn.hfstorm.aiera.common.log.enums.BusinessType;
import cn.hfstorm.aiera.common.mybatis.core.page.PageQuery;
import cn.hfstorm.aiera.common.security.web.controller.BaseController;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * aigc model
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/aigc/model")
public class AigcModelController extends BaseController {

    private final IAigcModelService modelService;
    private final SpringContextHolder contextHolder;

    @GetMapping("/list")
    public R<List<AigcModel>> list(AigcModel data) {
        return R.ok(modelService.list(data));
    }

    @GetMapping("/page")
    public R list(AigcModel data, PageQuery queryPage) {
        Page<AigcModel> iPage = modelService.page(data, queryPage);
        return R.ok(iPage);
    }

    @GetMapping("/{id}")
    public R<AigcModel> findById(@PathVariable String id) {
        return R.ok(modelService.selectById(id));
    }

    @PostMapping
    @Log(title = "添加模型", businessType = BusinessType.INSERT)
    @SaCheckPermission("aigc:model:add")
    public R add(@RequestBody AigcModel data) {
        if (StrUtil.isNotBlank(data.getApiKey()) && data.getApiKey().contains("*")) {
            data.setApiKey(null);
        }
        if (StrUtil.isNotBlank(data.getSecretKey()) && data.getSecretKey().contains("*")) {
            data.setSecretKey(null);
        }
        modelService.save(data);
        SpringContextHolder.publishEvent(new ModelProviderRefreshEvent(data));
        return R.ok();
    }

    @PutMapping
    @Log(title = "修改模型", businessType = BusinessType.UPDATE)
    @SaCheckPermission("aigc:model:update")
    public R update(@RequestBody AigcModel data) {
        if (StrUtil.isNotBlank(data.getApiKey()) && data.getApiKey().contains("*")) {
            data.setApiKey(null);
        }
        if (StrUtil.isNotBlank(data.getSecretKey()) && data.getSecretKey().contains("*")) {
            data.setSecretKey(null);
        }
        modelService.updateById(data);
        SpringContextHolder.publishEvent(new ModelProviderRefreshEvent(data));
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @Log(title = "删除模型", businessType = BusinessType.DELETE)
    @SaCheckPermission("aigc:model:delete")
    public R delete(@PathVariable String id) {
        modelService.removeById(id);

        // Delete dynamically registered beans, according to ID
        contextHolder.unregisterBean(id);
        return R.ok();
    }
}
