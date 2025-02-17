package cn.hfstorm.aiera.ai.provider.build;

import cn.hfstorm.aiera.common.ai.biz.domain.AigcModel;
import cn.hfstorm.aiera.common.ai.exception.ChatException;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;

/**
 * @author hmy
 */
public interface ModelBuildHandler {

    /**
     * 判断是不是当前模型
     */
    boolean whetherCurrentModel(AigcModel model);

    /**
     * basic check
     */
    boolean basicCheck(AigcModel model);

    /**
     * streaming chat build
     */
    StreamingChatLanguageModel doBuildStreamingChat(AigcModel model);

    default StreamingChatLanguageModel buildStreamingChat(AigcModel model) {
        try {
            if (!whetherCurrentModel(model)) {
                return null;
            }
            if (!basicCheck(model)) {
                return null;
            }
            return doBuildStreamingChat(model);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ChatException("build ai model exception", e);
        }
    }
//
//    @Override
//    default ChatClient doBuildChatClient(AigcModel model) {
//        ChatModel chatModel = buildStreamingChat(model);
//        // build chat client
//        return ChatClient.builder(chatModel)
//                .defaultAdvisors(
//                        new MessageChatMemoryAdvisor(
//                                new InMemoryChatMemory())
////                        ,
////                        new QuestionAnswerAdvisor(vectorStore,
////                                new SearchRequest.Builder().query(chatReq.getMessage()).build())
//                ).build();
//    }
//
//    /**
//     * embedding config
//     */
//    EmbeddingModel doBuildEmbedding(AigcModel model);
//
//    default EmbeddingModel buildEmbedding(AigcModel model) {
//        try {
//            if (!whetherCurrentModel(model)) {
//                return null;
//            }
//            if (!basicCheck(model)) {
//                return null;
//            }
//            return doBuildEmbedding(model);
//        } catch (ServiceException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new ChatException("build ai model exception", e);
//        }
//    }
//
//    /**
//     * chat build
//     */
//    ChatModel buildChatLanguageModel(AigcModel model);
//
//
//    /**
//     * image config
//     */
//    ImageModel buildImage(AigcModel model);

}
