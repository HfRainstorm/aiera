package cn.hfstorm.aiera.ai.chat.util;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.common.ai.chat.domain.ChatRequest;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

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
                .setConversationId(StrUtil.isBlank(request.conversationId()) ? IdUtil.simpleUUID() : request.conversationId())
//                .setRole(message.role().name())
                .setAigcModelId(request.aigcModelId())
//                .setPromptText(request.getPrompt())
                .setKnowledgeIds(request.aigcKnowledgeIds());
    }
}
