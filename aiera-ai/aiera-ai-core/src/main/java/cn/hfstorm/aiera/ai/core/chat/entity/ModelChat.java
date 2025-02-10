package cn.hfstorm.aiera.ai.core.chat.entity;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

import java.io.Serializable;

/**
 * model chat relation
 *
 * @author : hmy
 * @date : 2025/2/10 16:43
 */

@Data
@Builder
public class ModelChat implements Serializable {

    private static final long serialVersionUID = 4686481161225929947L;

    private ChatModel chatModel;

    private ChatClient chatClient;

    private AigcModel aigcModel;

    private String modelId;

    private String modelType;
}
