package cn.hfstorm.aiera.system.api;

/**
 * 配置服务
 *
 * @author hmy
 */
public interface RemoteConfigService {

	/**
	 * 获取注册开关
	 *
	 * @return true开启，false关闭
	 */
	boolean selectRegisterEnabled();

	String getConfigValue(String configKey);
}
