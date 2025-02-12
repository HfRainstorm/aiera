package cn.hfstorm.aiera.ai.core.provider.build;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;


/**
 * modelName builder
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
public interface ModelBuildHandler {

    /**
     * 判断是不是当前模型
     */
    boolean whetherCurrentModel(AigcModel model);


    /**
     * basic check
     */
    boolean basicCheck(AigcModel model);

    /**
     * streaming chat build
     */
    ChatModel doBuildStreamingChat(AigcModel model);

    default ChatModel buildStreamingChat(AigcModel model) {
        try {
            if (!whetherCurrentModel(model)) {
                return null;
            }
            if (!basicCheck(model)) {
                return null;
            }
            return doBuildStreamingChat(model);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }

    EmbeddingModel doBuildEmbedding(AigcModel model);
    default EmbeddingModel buildEmbedding(AigcModel model) {
        try {
            if (!whetherCurrentModel(model)) {
                return null;
            }
            if (!basicCheck(model)) {
                return null;
            }
            return doBuildEmbedding(model);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }


}
