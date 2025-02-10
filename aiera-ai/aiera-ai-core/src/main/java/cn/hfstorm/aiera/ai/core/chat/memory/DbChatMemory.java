package cn.hfstorm.aiera.ai.core.chat.memory;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * 数据库存储的聊天记忆 todo
 *
 * @author : hmy
 * @date : 2025/2/10 16:15
 */
public class DbChatMemory implements ChatMemory {

    @Override
    public void add(String conversationId, List<Message> messages) {

    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return null;
    }

    @Override
    public void clear(String conversationId) {

    }
}
