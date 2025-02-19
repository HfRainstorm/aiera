package cn.hfstorm.aiera.ai.api;

import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;

/**
 * @author : hmy
 * @date : 2025/2/19
 */
public interface AiEraModelApi {

    AigcModel getAigcModel();

    void setAigcModel(AigcModel aigcModel);

    /**
     * 模型类型
     * @return
     */
    String getModelProvider();
    /**
     * 清空模型，防止内存泄露
     */
    default void clear() {
        if (null != getAigcModel()) {
            setAigcModel(null);
        }
    }
}
