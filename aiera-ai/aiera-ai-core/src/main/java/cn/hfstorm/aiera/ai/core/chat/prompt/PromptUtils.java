package cn.hfstorm.aiera.ai.core.chat.prompt;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.biz.enums.ModelTypeEnum;
import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * @author : hmy
 * @date : 2025/2/10 11:20
 */
public class PromptUtils {


    /**
     * 构造提示词
     *
     * @param model
     * @param request
     * @return
     */
    public static PromptBuildHandler buildPromptHandler(AigcModel model, ChatRequest.ChatCompletionRequest request) {
        String type = model.getType();
        if (ModelTypeEnum.CHAT.name().equals(type)) {
            return new ChatModelPromptBuildHandler();
        }

        return new ChatModelPromptBuildHandler();
    }

    /**
     * 构造提示词
     *
     * @param model
     * @param request
     * @return
     */
    public static Prompt generateChatPrompt(AigcModel model, ChatRequest.ChatCompletionRequest request) {
        PromptBuildHandler promptBuildHandler = buildPromptHandler(model, request);

        return promptBuildHandler.buildChatPrompt(model, request);
    }
}
