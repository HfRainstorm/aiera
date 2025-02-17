package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.core.Agent;
import cn.hfstorm.aiera.ai.chat.domain.ChatReq;
import cn.hfstorm.aiera.ai.chat.domain.ChatRes;
import cn.hfstorm.aiera.ai.chat.service.LangChatService;
import cn.hfstorm.aiera.ai.factory.ChatResAiSearvices;
import cn.hfstorm.aiera.ai.provider.ModelProvider;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author : hmy
 * @date : 2025/2/17
 */
@Slf4j
@Service
@AllArgsConstructor
public class LangChatServiceImpl implements LangChatService {

    private final ModelProvider provider;


    private AiServices<Agent> build(StreamingChatLanguageModel streamModel, ChatLanguageModel model, ChatReq req) {
        AiServices<Agent> aiServices =
                ChatResAiSearvices.builder(Agent.class).chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.builder().id(req.getConversationId())
                                .chatMemoryStore(new InMemoryChatMemoryStore())
                        .maxMessages(30)
                        .build());
        if (StrUtil.isNotBlank(req.getPromptText())) {
            aiServices.systemMessageProvider(memoryId -> req.getPromptText());
        }
        if (streamModel != null) {
            aiServices.streamingChatLanguageModel(streamModel);
        }
        if (model != null) {
            aiServices.chatLanguageModel(model);
        }
        return aiServices;
    }

    @Override
    public TokenStream chat(ChatReq req) {
        StreamingChatLanguageModel model = provider.stream(req.getModelId());
        if (StrUtil.isBlank(req.getConversationId())) {
            req.setConversationId(IdUtil.simpleUUID());
        }

        AiServices<Agent> aiServices = build(model, null, req);
//
//        if (StrUtil.isNotBlank(req.getKnowledgeId())) {
//            req.getKnowledgeIds().add(req.getKnowledgeId());
//        }
//
//        if (req.getKnowledgeIds() != null && !req.getKnowledgeIds().isEmpty()) {
//            Function<Query, Filter> filter = (query) -> metadataKey(KNOWLEDGE).isIn(req.getKnowledgeIds());
//            ContentRetriever contentRetriever =
//                    EmbeddingStoreContentRetriever.builder().embeddingStore(embeddingProvider.getEmbeddingStore(req.getKnowledgeIds())).embeddingModel(embeddingProvider.getEmbeddingModel(req.getKnowledgeIds())).dynamicFilter(filter).build();
//            aiServices.retrievalAugmentor(DefaultRetrievalAugmentor.builder().contentRetriever(contentRetriever).build());
//        }
        Agent agent = aiServices.build();
        return agent.stream(req.getConversationId(), req.getMessage());
    }

    @Override
    public Flux<ChatRes> singleChat(ChatReq req) {
        StreamingChatLanguageModel model = provider.stream(req.getModelId());
        if (StrUtil.isBlank(req.getConversationId())) {
            req.setConversationId(IdUtil.simpleUUID());
        }

        Agent agent = build(model, null, req).build();
//        if (req.getPrompt() == null) {
//            req.setPrompt(PromptUtil.build(req.getMessage(), req.getPromptText()));
//        }
        return null;
    }
}
