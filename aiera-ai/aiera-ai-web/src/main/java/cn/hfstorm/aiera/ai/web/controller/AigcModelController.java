package cn.hfstorm.aiera.ai.web.controller;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.biz.event.ProviderRefreshEvent;
import cn.hfstorm.aiera.ai.biz.service.AigcModelService;
import cn.hfstorm.aiera.common.core.component.SpringContextHolder;
import cn.hfstorm.aiera.common.core.utils.MybatisUtil;
import cn.hfstorm.aiera.common.core.utils.QueryPage;
import cn.hfstorm.aiera.common.core.utils.R;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : hmy
 * @date : 2025/2/8 16:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/aigc/model")
public class AigcModelController {


    private final AigcModelService modelService;
    private final SpringContextHolder contextHolder;

    @GetMapping("/list")
    public R<List<AigcModel>> list(AigcModel data) {
        return R.ok(modelService.list(data));
    }

    @GetMapping("/page")
    public R list(AigcModel data, QueryPage queryPage) {
        Page<AigcModel> iPage = modelService.page(data, queryPage);
        return R.ok(MybatisUtil.getData(iPage));
    }

    @GetMapping("/{id}")
    public R<AigcModel> findById(@PathVariable("id") String id) {
        return R.ok(modelService.selectById(id));
    }

    @PostMapping
    @Operation(summary = "添加模型")
    public R add(@RequestBody AigcModel data) {
        if (StrUtil.isNotBlank(data.getApiKey()) && data.getApiKey().contains("*")) {
            data.setApiKey(null);
        }
        if (StrUtil.isNotBlank(data.getSecretKey()) && data.getSecretKey().contains("*")) {
            data.setSecretKey(null);
        }
        modelService.save(data);
        SpringContextHolder.publishEvent(new ProviderRefreshEvent(data));
        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改模型")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "成功的响应",
                    content = @Content(schema = @Schema(implementation = R.class)))
    })
    public R update(@RequestBody AigcModel data) {
        if (StrUtil.isNotBlank(data.getApiKey()) && data.getApiKey().contains("*")) {
            data.setApiKey(null);
        }
        if (StrUtil.isNotBlank(data.getSecretKey()) && data.getSecretKey().contains("*")) {
            data.setSecretKey(null);
        }
        modelService.updateById(data);
        SpringContextHolder.publishEvent(new ProviderRefreshEvent(data));
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除模型")
    public R delete(@PathVariable("id") String id) {
        modelService.removeById(id);

        // Delete dynamically registered beans, according to ID
        contextHolder.unregisterBean(id);
        return R.ok();
    }
}
