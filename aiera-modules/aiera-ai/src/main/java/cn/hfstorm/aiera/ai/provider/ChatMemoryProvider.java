package cn.hfstorm.aiera.ai.provider;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;

/**
 * chat memory provider
 * @author hmy
 */
public abstract class ChatMemoryProvider {

   public ChatMemory getChatMemory() {
        return new InMemoryChatMemory();
   }
}
