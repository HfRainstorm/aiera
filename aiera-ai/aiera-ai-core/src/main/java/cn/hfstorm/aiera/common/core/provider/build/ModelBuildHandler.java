package cn.hfstorm.aiera.common.core.provider.build;

import org.springframework.ai.chat.model.ChatModel;


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
    boolean whetherCurrentModel(ChatModel model);

    /**
     * streaming chat build
     */
    ChatModel buildStreamingChat(ChatModel model);


}
