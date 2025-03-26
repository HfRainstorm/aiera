package cn.hfstorm.aiera.ai.provider.model.embedding.build;

import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;

/**
 * @author : hmy
 * @date : 2025/2/21 9:12
 */
public interface EmbedModelBuildService {

    /**
     * 当前模型类型
     *
     * @return
     */
    default ModelTypeEnum currentModelType() {
        return ModelTypeEnum.EMBEDDING;
    }
}
