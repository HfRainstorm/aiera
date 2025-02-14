package cn.hfstorm.aiera.ai.chat.util;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.common.ai.chat.domain.ChatRequest;
import cn.hfstorm.aiera.common.ai.exception.ChatException;

import java.util.List;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
public class ChatParamUtils {


    /**
     * 根据请求参数构建应用ChatReq
     *
     * @param request
     * @return
     */
    public static ChatReq buildAppChatReqByCompletionReq(ChatRequest.ChatCompletionRequest request) {
        List<ChatRequest.ChatCompletionMessage> messages = request.messages();
        if (messages == null || messages.isEmpty()) {
            throw new ChatException("聊天消息为空，或者没有配置模型信息");
        }

        ChatRequest.ChatCompletionMessage message = messages.get(0);

        return new ChatReq()
                .setMessage(message.content())
//                .setRole(message.role().name())
                .setAigcModelId(request.aigcModelId())
//                .setPromptText(request.getPrompt())
                .setKnowledgeIds(request.aigcKnowledgeIds());
    }
}
