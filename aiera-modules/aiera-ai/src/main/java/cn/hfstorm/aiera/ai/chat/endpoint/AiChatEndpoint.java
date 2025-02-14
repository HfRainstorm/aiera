package cn.hfstorm.aiera.ai.chat.endpoint;

import cn.hfstorm.aiera.ai.annotation.OpenapiAuth;
import cn.hfstorm.aiera.ai.annotation.ValidChatMessage;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.common.ai.chat.consts.ChatParamConstant;
import cn.hfstorm.aiera.common.ai.chat.domain.ChatRequest;
import cn.hfstorm.aiera.common.log.annotation.Log;
import cn.hfstorm.aiera.common.log.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
    public Flux<String> completions(@RequestBody ChatRequest.ChatCompletionRequest req) {
        return chatService.streamChat(req).content().concatWith(Flux.just(ChatParamConstant.CHAT_COMPLETE_END_WITH));
    }

}
