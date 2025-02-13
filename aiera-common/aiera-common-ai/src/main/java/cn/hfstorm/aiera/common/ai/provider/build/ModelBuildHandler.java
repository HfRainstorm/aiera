package cn.hfstorm.aiera.common.ai.provider.build;

import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;

/**
 * @author hmy
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

    /**
     * embedding config
     */
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

    /**
     * chat build
     */
    ChatModel buildChatLanguageModel(AigcModel model);


    /**
     * image config
     */
    ImageModel buildImage(AigcModel model);

}
