package cn.hfstorm.aiera.ai.provider.model.chat.build;

import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;

/**
 * @author hmy
 */
public interface ChatModelBuildService {

    default ModelTypeEnum currentModelType() {
        return ModelTypeEnum.CHAT;
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
