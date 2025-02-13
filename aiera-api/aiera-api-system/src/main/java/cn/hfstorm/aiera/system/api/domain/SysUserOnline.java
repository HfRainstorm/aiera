package cn.hfstorm.aiera.system.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 当前在线会话
 *
 * @author hmy
 */

@Data
@NoArgsConstructor
public class SysUserOnline implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 设备类型
	 */
	private String deviceType;

	/**
	 * 登录IP地址
	 */
	private String ipaddr;

	/**
	 * 登录地址
	 */
	private String loginLocation;

	/**
	 * 浏览器类型
	 */
	private String browser;

	/**
	 * 操作系统
	 */
	private String os;

	/**
	 * 登录时间
	 */
	private Long loginTime;

}
