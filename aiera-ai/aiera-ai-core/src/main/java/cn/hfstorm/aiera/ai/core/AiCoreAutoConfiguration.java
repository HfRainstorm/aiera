package cn.hfstorm.aiera.ai.core;

import cn.hfstorm.aiera.ai.core.properties.AiEraProps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tycoding
 * @since 2024/4/15
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AiEraProps.class,})
@AllArgsConstructor
public class AiCoreAutoConfiguration {

}
