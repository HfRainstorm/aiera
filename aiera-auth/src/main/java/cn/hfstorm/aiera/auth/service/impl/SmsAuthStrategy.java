package cn.hfstorm.aiera.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hfstorm.aiera.auth.domain.vo.LoginVo;
import cn.hfstorm.aiera.auth.form.SmsLoginBody;
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
 * 短信认证策略
 *
 * @author hmy
 */
@Slf4j
@Service("sms" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SmsAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;

    @DubboReference
    private RemoteUserService remoteUserService;

    @Override
    public LoginVo login(String body) {
        SmsLoginBody loginBody = JsonUtils.parseObject(body, SmsLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String phonenumber = loginBody.getPhonenumber();
        String smsCode = loginBody.getSmsCode();

        // 通过手机号查找用户
        LoginUser loginUser = remoteUserService.getUserInfoByPhonenumber(phonenumber);
        loginService.checkLogin(LoginType.SMS, loginUser.getUsername(), () -> !validateSmsCode(phonenumber, smsCode));
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
     * 校验短信验证码
     */
    private boolean validateSmsCode(String phonenumber, String smsCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + phonenumber);
        if (StringUtils.isBlank(code)) {
            loginService.recordLogininfor(phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }

}
