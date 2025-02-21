package cn.hfstorm.aiera.ai.chat.domain;

import cn.hfstorm.aiera.ai.provider.model.build.ModelBuildHandler;
import cn.hfstorm.aiera.common.ai.domain.AigcModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.ai.chat.model.ChatModel;

/**
 * @author : hmy
 * @date : 2025/2/19
 */
@Data
@Builder
public class AigcChatModel {

    private ChatModel chatModel;

    private AigcModel aigcModel;

    private ModelBuildHandler modelBuildHandler;


}
