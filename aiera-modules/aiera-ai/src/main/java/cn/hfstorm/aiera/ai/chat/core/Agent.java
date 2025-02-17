package cn.hfstorm.aiera.ai.chat.core;

import cn.hfstorm.aiera.ai.chat.domain.ChatRes;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/17
 */
public interface Agent {

    Flux<ChatRes> stream(@MemoryId String id, @UserMessage String message);

    String text(@MemoryId String id, @UserMessage String message);
}
