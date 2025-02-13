package cn.hfstorm.aiera.gateway.filter;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.same.SaSameUtil;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 转发认证过滤器(内部服务外网隔离)
 *
 * @author hmy
 */
@Component
public class ForwardAuthFilter implements GlobalFilter {
	@SneakyThrows
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 未开启配置则直接跳过
		if (!SaManager.getConfig().getCheckSameToken()) {
			return chain.filter(exchange);
		}
		ServerHttpRequest newRequest = exchange
				.getRequest()
				.mutate()
				// 为请求追加 Same-Token 参数
				.header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken())
				.build();
		ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
		/**
		 * 网关转发特殊字符处理
		 */
		// newExchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, exchange.getRequest().getURI());
		URI targetUri = newExchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
		String originUriString = targetUri.toString();
		if (originUriString.contains("%25") || originUriString.contains("%23")) {
			//将编码后的 %23 替换为 #，重新用这个字符串生成 URI
			URI replaced = new URI(originUriString.replace("%25", "%").replace("%23", "#"));
			newExchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, replaced);
		}
		return chain.filter(newExchange);
	}

}

