package cn.hfstorm.aiera.common.ai.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hmy
 *@date : 2025/2/8 10:33
 */
@Data
@ConfigurationProperties(prefix = "aiera.chat")
public class AiEraChatProps {
}
