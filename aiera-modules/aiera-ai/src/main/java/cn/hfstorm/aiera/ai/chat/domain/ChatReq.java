package cn.hfstorm.aiera.ai.chat.domain;

import dev.langchain4j.model.input.Prompt;
import lombok.Data;
import lombok.experimental.Accessors;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author : hmy
 * @date : 2025/2/14
 */

@Data
@Accessors(chain = true)
public class ChatReq {

    private String appId;
    private String modelId;
    private String modelName;
    private String modelProvider;

    private String message;

    private String conversationId;

    private String userId;

    private String username;

    private String chatId;

    private String promptText;

    private String docsName;

    private String knowledgeId;
    private List<String> knowledgeIds = new ArrayList<>();

    private String docsId;

    private String url;

    private String role;

    private Prompt prompt;

    //    private StreamEmitter emitter;
    private FluxSink<ChatRes> fluxSink;
    private Executor executor;

}
