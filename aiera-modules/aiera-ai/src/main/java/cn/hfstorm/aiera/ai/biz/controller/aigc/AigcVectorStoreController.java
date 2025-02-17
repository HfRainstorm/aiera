//package cn.hfstorm.aiera.ai.biz.controller.aigc;
//
//
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import cn.hfstorm.aiera.ai.biz.service.IAigcVectorStoreService;
//import cn.hfstorm.aiera.ai.event.VectorProviderRefreshEvent;
//import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
//import cn.hfstorm.aiera.common.ai.biz.domain.AigcVectorStore;
//import cn.hfstorm.aiera.common.core.domain.R;
//import cn.hfstorm.aiera.common.log.annotation.Log;
//import cn.hfstorm.aiera.common.mybatis.core.page.PageQuery;
//import cn.hfstorm.aiera.common.mybatis.core.util.MybatisUtils;
//import cn.hfstorm.aiera.common.security.web.controller.BaseController;
//import cn.hutool.core.lang.Dict;
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * aigc model
// */
//@Validated
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/aigc/vectorstore")
//public class AigcVectorStoreController extends BaseController {
//
//    private final IAigcVectorStoreService vectorStoreService;
//    private final SpringContextHolder contextHolder;
//
//    @GetMapping("/list")
//    public R<List<AigcVectorStore>> list(AigcVectorStore data) {
//        List<AigcVectorStore> list = vectorStoreService.list(Wrappers.lambdaQuery());
//        list.forEach(this::hide);
//        return R.ok(list);
//    }
//
//    @GetMapping("/page")
//    public R<Dict> page(AigcVectorStore embedStore, PageQuery queryPage) {
//        IPage<AigcVectorStore> page = vectorStoreService.page(MybatisUtils.wrap(embedStore, queryPage),
//                Wrappers.lambdaQuery());
//        page.getRecords().forEach(this::hide);
//        return R.ok(MybatisUtils.getData(page));
//    }
//
//    @GetMapping("/{id}")
//    public R<AigcVectorStore> findById(@PathVariable String id) {
//        AigcVectorStore store = vectorStoreService.getById(id);
//        hide(store);
//        return R.ok(store);
//    }
//
//    @PostMapping
//    @Log(title = "新增向量库")
//    @SaCheckPermission("aigc:embed-store:add")
//    public R<AigcVectorStore> add(@RequestBody AigcVectorStore data) {
//        if (StrUtil.isNotBlank(data.getPassword()) && data.getPassword().contains("*")) {
//            data.setPassword(null);
//        }
//        vectorStoreService.save(data);
//        SpringContextHolder.publishEvent(new VectorProviderRefreshEvent(data));
//        return R.ok();
//    }
//
//    @PutMapping
//    @Log(title = "修改向量库")
//    @SaCheckPermission("aigc:embed-store:update")
//    public R update(@RequestBody AigcVectorStore data) {
//        if (StrUtil.isNotBlank(data.getPassword()) && data.getPassword().contains("*")) {
//            data.setPassword(null);
//        }
//        vectorStoreService.updateById(data);
//        SpringContextHolder.publishEvent(new VectorProviderRefreshEvent(data));
//        return R.ok();
//    }
//
//    @DeleteMapping("/{id}")
//    @Log(title = "删除向量库")
//    @SaCheckPermission("aigc:embed-store:delete")
//    public R delete(@PathVariable String id) {
//        AigcVectorStore store = vectorStoreService.getById(id);
//        if (store != null) {
//            vectorStoreService.removeById(id);
//            contextHolder.unregisterBean(id);
//        }
//        return R.ok();
//    }
//
//    private void hide(AigcVectorStore data) {
//        if (data == null || StrUtil.isBlank(data.getPassword())) {
//            return;
//        }
//        String key = StrUtil.hide(data.getPassword(), 0, data.getPassword().length());
//        data.setPassword(key);
//    }
//}
