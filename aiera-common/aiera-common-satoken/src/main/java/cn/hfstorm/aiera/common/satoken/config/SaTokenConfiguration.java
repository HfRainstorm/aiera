package cn.hfstorm.aiera.common.satoken.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpLogic;
import cn.hfstorm.aiera.common.core.factory.YmlPropertySourceFactory;
import cn.hfstorm.aiera.common.satoken.core.dao.PlusSaTokenDao;
import cn.hfstorm.aiera.common.satoken.core.service.SaPermissionImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * Sa-Token 配置
 *
 * @author hmy
 */
@AutoConfiguration
@PropertySource(value = "classpath:common-satoken.yml", factory = YmlPropertySourceFactory.class)
public class SaTokenConfiguration {

	@Bean
	public StpLogic getStpLogicJwt() {
		return new StpLogicJwtForSimple();
	}

	/**
	 * 权限接口实现(使用bean注入方便用户替换)
	 */
	@Bean
	public StpInterface stpInterface() {
		return new SaPermissionImpl();
	}

	/**
	 * 自定义dao层存储
	 */
	@Bean
	public SaTokenDao saTokenDao() {
		return new PlusSaTokenDao();
	}

}
