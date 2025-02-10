package cn.hfstorm.aiera.ai.core.chat.prompt;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.common.ai.entity.ChatRequest;
import cn.hfstorm.aiera.common.core.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : hmy
 * @date : 2025/2/10 10:39
 */
@Slf4j
@Component
public class ChatModelPromptBuildHandler implements PromptBuildHandler {


    @Override
    public Prompt buildChatPrompt(AigcModel model, ChatRequest.ChatCompletionRequest request) {

        String systemText = """
                You are a helpful AI assistant that helps people find information.
                Your name is {name} and come from {company}.
                You should reply to the user's request with your name.
                """;

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);

        Message systemMessage = systemPromptTemplate.createMessage(
                Map.of("name", MessageUtils.message("model.chat.prompt.name"),
                        "company", MessageUtils.message("model.chat.prompt.name")));

        List<Message> requestMessages = request.messages().stream().map(message -> {
            if (null == message.role()) {
                return new UserMessage(message.content());
            }
            switch (message.role()) {
                case ASSISTANT:
                    return new AssistantMessage(message.content());
                case SYSTEM:
                    return new SystemMessage(message.content());
                default:
                    return new UserMessage(message.content());
            }
        }).collect(Collectors.toList());

        return new Prompt(Stream.concat(requestMessages.stream(), Stream.of(systemMessage)).collect(Collectors.toList()));
    }
}
