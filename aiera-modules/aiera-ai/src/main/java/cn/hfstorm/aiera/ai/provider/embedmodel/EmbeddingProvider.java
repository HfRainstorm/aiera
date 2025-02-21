package cn.hfstorm.aiera.ai.provider.embedmodel;

import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
import cn.hfstorm.aiera.ai.provider.model.ModelProviderFactory;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 注册向量数据库
 *
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmbeddingProvider {


    private final ApplicationContext context;
    private final SpringContextHolder contextHolder;
    private final AigcModelService aigcModelService;

    private ModelProviderFactory modelProviderFactory;

    /**
     * 获取嵌入模型
     *
     * @param chatReq
     * @return
     */
    public EmbeddingModel getEmbedModel(ChatReq chatReq) {

        if (context.containsBean(chatReq.getModelId())) {
            return (EmbeddingModel) context.getBean(chatReq.getModelId());
        } else {

            AigcModel model = aigcModelService.selectById(chatReq.getModelId());
            // Uninstall previously registered beans before registering them
            contextHolder.unregisterBean(chatReq.getModelId());

            EmbeddingModel embeddingModel = modelProviderFactory.embeddingHandler(model);
            if (null == embeddingModel) {
                throw new ChatException("没有匹配到模型，请检查模型配置！");
            }

            return embeddingModel;
        }
    }


}
