package cn.hfstorm.aiera.ai.core.chat.prompt;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.biz.enums.ModelTypeEnum;
import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * @author : hmy
 * @date : 2025/2/10 10:37
 */
public interface PromptBuildHandler {



    Prompt buildChatPrompt(AigcModel model, ChatRequest.ChatCompletionRequest request);
}
