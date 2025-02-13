package cn.hfstorm.aiera.common.core.exception.user;

import java.io.Serial;

/**
 * 验证码错误异常类
 *
 * @author hmy
 */
public class CaptchaException extends UserException {
	@Serial
	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super("user.jcaptcha.error");
	}

	public CaptchaException(String msg) {
		super(msg);
	}
}
