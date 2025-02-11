package cn.hfstorm.aiera.common.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hmy
 * @date : 2025/2/8 10:33
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class AiEraProjectProps {
    /**
     * project version
     */
    public String version;

    /**
     * project url
     */
    public String url;
}
