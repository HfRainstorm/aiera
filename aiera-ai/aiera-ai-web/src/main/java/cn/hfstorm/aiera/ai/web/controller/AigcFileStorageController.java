package cn.hfstorm.aiera.ai.web.controller;

import cn.hfstorm.aiera.ai.biz.entity.AigcFileStorage;
import cn.hfstorm.aiera.ai.biz.service.AigcFileStorageService;
import cn.hfstorm.aiera.common.core.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * @author hmy
 * @since : 2025/2/12 17:20
 */
@RequestMapping("/aigc/filestore")
@RestController
@AllArgsConstructor
public class AigcFileStorageController {
    private final AigcFileStorageService aigcFileStorageService;

    @GetMapping("/list")
    public R list() {
//        List<AigcFileStorage> list = aigcFileStorageService.list(Wrappers.<AigcFileStorage>lambdaQuery().eq(AigcFileStorage::getUserId,
//                AuthUtil.getUserId()).orderByDesc(AigcFileStorage::getCreateTime));
        return R.ok(new ArrayList<>());
    }

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public R upload(MultipartFile file) {
        String userId = "1";
        return R.ok(aigcFileStorageService.upload(file, userId));
    }

    @PutMapping
    @Operation(summary = "更新文件资源")
    public R update(@RequestBody AigcFileStorage data) {
        aigcFileStorageService.updateById(data);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文件资源")
    public R delete(@PathVariable String id) {
//        aigcOssService.removeById(id);
        return R.ok();
    }
}
