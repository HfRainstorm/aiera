package cn.hfstorm.aiera.gateway.config;

import cn.hfstorm.aiera.gateway.handler.SentinelFallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 网关限流配置
 *
 * @author hmy
 */
@Configuration
public class GatewayConfig {
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
		return new SentinelFallbackHandler();
	}
}
