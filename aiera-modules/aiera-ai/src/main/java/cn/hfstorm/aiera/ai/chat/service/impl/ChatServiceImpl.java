package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.domain.ChatRes;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.ai.chat.service.LangChatService;
import cn.hfstorm.aiera.common.ai.util.StreamEmitter;
import dev.langchain4j.model.output.TokenUsage;
import dev.langchain4j.service.TokenStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements IChatService {

    private final LangChatService langChatService;

    @Override
    public void chat(ChatReq req) {
        StreamEmitter emitter = req.getEmitter();
        long startTime = System.currentTimeMillis();
        StringBuilder text = new StringBuilder();

        // save user message
        req.setRole("user");

        try {
            langChatService.chat(req).onNext(e -> {
                text.append(e);
                emitter.send(new ChatRes(e));
            }).onComplete((e) -> {
                TokenUsage tokenUsage = e.tokenUsage();
                ChatRes res = new ChatRes(tokenUsage.totalTokenCount(), startTime);
                emitter.send(res);
                emitter.complete();

                // save assistant message
//                        req.setMessage(text.toString());
//                        req.setRole(RoleEnum.ASSISTANT.getName());
            }).onError((e) -> {
                emitter.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            emitter.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
//
//        return langChatService
//                .chat(req);
//
    }

//    @Autowired
//    ClientProvider clientProvider;

//
//    @Override
//    public ChatClient.StreamResponseSpec streamChat(ChatRequest.ChatCompletionRequest request) throws ChatException {
//
//        ChatReq chatReq = ChatParamUtils.buildAppChatReqByCompletionReq(request);
//        return clientProvider.getClient(chatReq.getConversationId(), chatReq.getAigcModelId(), chatReq.getMessage())
//                .prompt().user(chatReq.getMessage()).stream();
//    }
}
