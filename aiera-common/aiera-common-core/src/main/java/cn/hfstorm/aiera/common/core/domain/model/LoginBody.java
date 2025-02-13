package cn.hfstorm.aiera.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录对象
 *
 * @author hmy
 */
@Data
@NoArgsConstructor
public class LoginBody {

	/**
	 * 授权类型
	 */
	@NotBlank(message = "{auth.grant.type.not.blank}")
	private String grantType;

	/**
	 * 验证码
	 */
	private String code;

	/**
	 * 唯一标识
	 */
	private String uuid;

}
