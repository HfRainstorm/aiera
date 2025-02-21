package cn.hfstorm.aiera.ai.provider.embedmodel.build;

import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import org.springframework.ai.embedding.EmbeddingModel;

/**
 * @author : hmy
 * @date : 2025/2/21 9:12
 */
public interface EmbedModelBuildHandler {


    /**
     * 判断是不是当前模型
     */
    boolean whetherCurrentModel(AigcModel aigcModel);

    /**
     * basic check
     */
    boolean basicCheck(AigcModel aigcModel);

    /**
     * streaming chat build
     */
    EmbeddingModel doBuildEmbedModel(AigcModel aigcModel);

    default EmbeddingModel buildEmbedModel(AigcModel aigcModel) {
        try {
//            if (!ModelTypeEnum.EMBEDDING.name().equalsIgnoreCase(aigcModel.getType())) {
//                return null;
//            }
            if (!whetherCurrentModel(aigcModel)) {
                return null;
            }
            if (!basicCheck(aigcModel)) {
                return null;
            }
            return doBuildEmbedModel(aigcModel);

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }
}
