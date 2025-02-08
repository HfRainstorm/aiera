package cn.hfstorm.aiera.common.ai;

import cn.hfstorm.aiera.common.ai.properties.AiEraChatProps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hmy
 *@date : 2025/2/8 10:33
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AiEraChatProps.class,})
@AllArgsConstructor
public class CoreAutoConfiguration {

}
