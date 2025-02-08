package cn.hfstorm.aiera.ai.core.provider.build;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
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
    boolean whetherCurrentModel(AigcModel model);


    /**
     * basic check
     */
    boolean basicCheck(AigcModel model);

    /**
     * streaming chat build
     */
    ChatModel buildStreamingChat(AigcModel model);


}
