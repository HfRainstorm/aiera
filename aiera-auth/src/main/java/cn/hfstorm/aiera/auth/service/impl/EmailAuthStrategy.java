package cn.hfstorm.aiera.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hfstorm.aiera.auth.domain.vo.LoginVo;
import cn.hfstorm.aiera.auth.form.EmailLoginBody;
import cn.hfstorm.aiera.auth.service.IAuthStrategy;
import cn.hfstorm.aiera.auth.service.SysLoginService;
import cn.hfstorm.aiera.common.core.constant.Constants;
import cn.hfstorm.aiera.common.core.constant.GlobalConstants;
import cn.hfstorm.aiera.common.core.enums.LoginType;
import cn.hfstorm.aiera.common.core.exception.user.CaptchaExpireException;
import cn.hfstorm.aiera.common.core.utils.JsonUtils;
import cn.hfstorm.aiera.common.core.utils.MessageUtils;
import cn.hfstorm.aiera.common.core.utils.StringUtils;
import cn.hfstorm.aiera.common.core.utils.ValidatorUtils;
import cn.hfstorm.aiera.common.redis.utils.RedisUtils;
import cn.hfstorm.aiera.common.satoken.utils.LoginHelper;
import cn.hfstorm.aiera.system.api.RemoteUserService;
import cn.hfstorm.aiera.system.api.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 邮件认证策略
 *
 * @author hmy
 */
@Slf4j
@Service("email" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class EmailAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;

    @DubboReference
    private RemoteUserService remoteUserService;

    @Override
    public LoginVo login(String body) {
        EmailLoginBody loginBody = JsonUtils.parseObject(body, EmailLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String email = loginBody.getEmail();
        String emailCode = loginBody.getEmailCode();

        // 通过邮箱查找用户
        LoginUser loginUser = remoteUserService.getUserInfoByEmail(email);
        loginService.checkLogin(LoginType.EMAIL, loginUser.getUsername(), () -> !validateEmailCode(email, emailCode));
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
     * 校验邮箱验证码
     */
    private boolean validateEmailCode(String email, String emailCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + email);
        if (StringUtils.isBlank(code)) {
            loginService.recordLogininfor(email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(emailCode);
    }

}
