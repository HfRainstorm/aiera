package cn.hfstorm.aiera.auth.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hfstorm.aiera.auth.domain.vo.LoginVo;
import cn.hfstorm.aiera.auth.form.PasswordLoginBody;
import cn.hfstorm.aiera.auth.service.IAuthStrategy;
import cn.hfstorm.aiera.auth.service.SysLoginService;
import cn.hfstorm.aiera.auth.properties.CaptchaProperties;
import cn.hfstorm.aiera.auth.properties.EncryptorProperties;
import cn.hfstorm.aiera.common.core.constant.Constants;
import cn.hfstorm.aiera.common.core.constant.GlobalConstants;
import cn.hfstorm.aiera.common.core.enums.LoginType;
import cn.hfstorm.aiera.common.core.exception.user.CaptchaException;
import cn.hfstorm.aiera.common.core.exception.user.CaptchaExpireException;
import cn.hfstorm.aiera.common.core.utils.JsonUtils;
import cn.hfstorm.aiera.common.core.utils.MessageUtils;
import cn.hfstorm.aiera.common.core.utils.StringUtils;
import cn.hfstorm.aiera.common.core.utils.ValidatorUtils;
import cn.hfstorm.aiera.common.encrypt.utils.EncryptUtils;
import cn.hfstorm.aiera.common.redis.utils.RedisUtils;
import cn.hfstorm.aiera.common.satoken.utils.LoginHelper;
import cn.hfstorm.aiera.system.api.RemoteUserService;
import cn.hfstorm.aiera.system.api.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 密码认证策略
 *
 * @author hmy
 */
@Slf4j
@Service("password" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class PasswordAuthStrategy implements IAuthStrategy {

    private final CaptchaProperties captchaProperties;

    private final SysLoginService loginService;

	private final EncryptorProperties encryptorProperties;

    @DubboReference
    private RemoteUserService remoteUserService;

	public static void main(String[] args) {
		String hashpw = BCrypt.hashpw("123456");
		System.out.printf(hashpw);
	}

    @Override
    public LoginVo login(String body) {
        PasswordLoginBody loginBody = JsonUtils.parseObject(body, PasswordLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
	    //前端使用公钥进行加密，服务端私钥解密,私钥为空不解密
		if (StringUtils.isNotEmpty(encryptorProperties.getPrivateKey())){
			password = EncryptUtils.decryptByRsa(password, encryptorProperties.getPrivateKey());
		}
        String code = loginBody.getCode();
        String uuid = loginBody.getUuid();

        // 验证码开关
        if (captchaProperties.getEnabled()) {
            validateCaptcha(username, code, uuid);
        }

        LoginUser loginUser = remoteUserService.getUserInfo(username);
	    String finalPassword = password;
	    loginService.checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(finalPassword, loginUser.getPassword()));
        // loginUser.setClientKey(client.getClientKey());
        // loginUser.setDeviceType(client.getDeviceType());
        // 生成token
        LoginHelper.login(loginUser, null);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        // loginVo.setClientId(client.getClientId());
        return loginVo;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    private void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            loginService.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            loginService.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

}
