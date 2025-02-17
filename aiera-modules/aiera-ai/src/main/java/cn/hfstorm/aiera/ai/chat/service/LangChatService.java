package cn.hfstorm.aiera.ai.chat.service;

import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.domain.ChatRes;
import dev.langchain4j.service.TokenStream;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/17
 */
public interface LangChatService {

    Flux<ChatRes> chat(ChatReq req);

    Flux<ChatRes> singleChat(ChatReq req);

}
