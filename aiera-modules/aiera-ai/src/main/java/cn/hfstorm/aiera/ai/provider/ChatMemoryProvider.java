package cn.hfstorm.aiera.ai.provider;

import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

/**
 * chat memory provider
 * @author hmy
 */
public abstract class ChatMemoryProvider {

   public ChatMemoryStore getChatMemory() {
        return new InMemoryChatMemoryStore();
   }
}
