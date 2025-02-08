package cn.hfstorm.aiera.common.core.provider.build;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

/**
 * @author : hmy
 * @date : 2025/2/8 10:33
 */

@Slf4j
@Component
public class OpenAiModelBuildHandler implements ModelBuildHandler {

    @Override
    public boolean whetherCurrentModel(ChatModel model) {
        return false;
    }

    @Override
    public ChatModel buildStreamingChat(ChatModel model) {
        return null;
    }
}
