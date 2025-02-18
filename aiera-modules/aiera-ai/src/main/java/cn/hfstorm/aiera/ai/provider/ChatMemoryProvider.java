package cn.hfstorm.aiera.ai.provider;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;

/**
 * @author : hmy
 * @date : 2025/2/18
 */
public abstract class ChatMemoryProvider {

    public ChatMemory getChatMemory() {
        return new InMemoryChatMemory();
    }
}