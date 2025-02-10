package cn.hfstorm.aiera.server.api;

import cn.hfstorm.aiera.common.ai.annotation.ValidChatMessage;
import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import cn.hfstorm.aiera.server.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author : hmy
 * @date : 2025/2/7 17:20
 */
@Slf4j
@RequestMapping("/v1")
@RestController
@AllArgsConstructor
public class ApiChatEndpoint {
    private final ChatService chatService;

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message",
            defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", message);
    }

    @ValidChatMessage
    @PostMapping("/chat/completions")
    public Flux<ChatResponse> chatCompletionsWithResponse(@RequestBody ChatRequest.ChatCompletionRequest chatRequest) {
        return chatService.chat(chatRequest);
    }

    @ValidChatMessage
    @PostMapping("/chat/single/completions")
    public Flux<String> chatCompletionsWithStr(@RequestBody ChatRequest.ChatCompletionRequest chatRequest) {
        return chatService.singleChat(chatRequest);
    }

}
