package cn.hfstorm.aiera.ai.provider.model;

import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
import cn.hfstorm.aiera.common.ai.exception.AiModelException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import org.springframework.ai.model.Model;

/**
 * @author : hmy
 * @since : 2025/3/26
 */
public interface AiModelBuildService<T extends Model> {
    ModelTypeEnum getModelType();

    /**
     * 判断是不是当前模型
     *
     * @param model 模型请求参数
     * @return 是否是当前模型
     */
    boolean whetherCurrentModel(AigcModel model);

    /**
     * basic check
     *
     * @param model 模型请求参数
     * @return 校验结果
     */
    boolean basicCheck(AigcModel model);


    /**
     * 构建模型
     *
     * @param model 模型请求参数
     * @return 模型
     */
    T buildModel(AigcModel model);

    /**
     * 构建模型
     *
     * @param model
     * @return
     */
    default T doBuildModel(AigcModel model) {
        try {
            // 判断模型类型
            if (!whetherCurrentModel(model)) {
                return null;
            }
            // 基础校验
            if (!basicCheck(model)) {
                return null;
            }
            // 构建模型
            return buildModel(model);
        } catch (ServiceException e) {
            throw new AiModelException("build ai model exception", e);
        }
    }
}
