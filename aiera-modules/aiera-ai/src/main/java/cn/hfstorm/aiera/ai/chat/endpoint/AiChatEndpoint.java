package cn.hfstorm.aiera.ai.chat.endpoint;

import cn.hfstorm.aiera.ai.annotation.OpenapiAuth;
import cn.hfstorm.aiera.ai.annotation.ValidChatMessage;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.service.IChatService;
import cn.hfstorm.aiera.common.ai.consts.ChatParamConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

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
    @PostMapping(value = "/chat/completions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> completions(@RequestBody ChatReq request) {
        return chatService.streamChat(request);
    }
}
