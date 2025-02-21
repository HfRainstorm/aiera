package cn.hfstorm.aiera.ai.provider.knowledge;

import cn.hfstorm.aiera.ai.biz.service.IAigcEmbedStoreService;
import cn.hfstorm.aiera.ai.biz.service.IAigcKnowledgeService;
import cn.hfstorm.aiera.ai.biz.service.IAigcModelService;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.common.ai.domain.AigcEmbedStore;
import cn.hfstorm.aiera.common.ai.domain.AigcKnowledge;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 知识库初始化
 *
 * @author hmy
 * @since 2025/2/15
 */
@Slf4j
@Component
@AllArgsConstructor
public class AiKnowledgeStoreInitialize implements ApplicationContextAware {

    private final IAigcKnowledgeService knowledgeService;
    private final SpringContextHolder contextHolder;
    private final IAigcModelService modelService;
    private final IAigcEmbedStoreService embedStoreService;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        init();
    }

    public void init() {
        List<AigcKnowledge> list = knowledgeService.list();
        Map<String, List<AigcModel>> modelMap =
                modelService.list().stream().collect(Collectors.groupingBy(AigcModel::getId));
        Map<String, List<AigcEmbedStore>> storeMap =
                embedStoreService.list().stream().collect(Collectors.groupingBy(AigcEmbedStore::getId));
        list.forEach(know -> {
            if (know.getEmbedModelId() != null) {
                List<AigcModel> models = modelMap.get(know.getEmbedModelId());
                know.setEmbedModel(models == null ? null : models.get(0));
            }
            if (know.getEmbedStoreId() != null) {
                List<AigcEmbedStore> stores = storeMap.get(know.getEmbedStoreId());
                know.setEmbedStore(stores == null ? null : stores.get(0));
            }
            contextHolder.registerBean(know.getId(), know);
        });
    }
}
