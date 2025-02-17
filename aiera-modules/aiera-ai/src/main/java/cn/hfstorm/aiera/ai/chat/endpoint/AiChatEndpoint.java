package cn.hfstorm.aiera.ai.chat.endpoint;

import cn.hfstorm.aiera.ai.annotation.OpenapiAuth;
import cn.hfstorm.aiera.ai.annotation.ValidChatMessage;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.domain.ChatRes;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.common.ai.util.StreamEmitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : hmy
 * @date : 2025/2/14 9:50
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AiChatEndpoint {

    private final IChatService chatService;

    @OpenapiAuth()
    @ValidChatMessage
//    @Log(title = "模型对话",
//            businessType = BusinessType.OTHER)
    @PostMapping(value = "/chat/completions")
//    public Flux<String> completions(@RequestBody ChatRequest.ChatCompletionRequest req) {
//        return chatService.streamChat(req).content().concatWith(Flux.just(ChatParamConstant.CHAT_COMPLETE_END_WITH));
//    }
    public void chat(@RequestBody ChatReq req) {
        StreamEmitter emitter = new StreamEmitter();
        req.setEmitter(emitter);
//        req.setUserId(AuthUtil.getUserId());
//        req.setUsername(AuthUtil.getUsername());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        req.setExecutor(executor);
        emitter.streaming(executor, () -> {
            chatService.chat(req);
        });

    }
}
