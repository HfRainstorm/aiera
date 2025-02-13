package cn.hfstorm.aiera.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hfstorm.aiera.auth.domain.vo.LoginVo;
import cn.hfstorm.aiera.auth.form.XcxLoginBody;
import cn.hfstorm.aiera.auth.service.IAuthStrategy;
import cn.hfstorm.aiera.auth.service.SysLoginService;
import cn.hfstorm.aiera.common.core.utils.JsonUtils;
import cn.hfstorm.aiera.common.core.utils.ValidatorUtils;
import cn.hfstorm.aiera.common.satoken.utils.LoginHelper;
import cn.hfstorm.aiera.system.api.RemoteUserService;
import cn.hfstorm.aiera.system.api.model.XcxLoginUser;
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
@Service("xcx" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class XcxAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;

    @DubboReference
    private RemoteUserService remoteUserService;

    @Override
    public LoginVo login(String body) {
        XcxLoginBody loginBody = JsonUtils.parseObject(body, XcxLoginBody.class);
        ValidatorUtils.validate(loginBody);
        // xcxCode 为 小程序调用 wx.login 授权后获取
        String xcxCode = loginBody.getXcxCode();
        // 多个小程序识别使用
        String appid = loginBody.getAppid();

        // todo 以下自行实现
        // 校验 appid + appsrcret + xcxCode 调用登录凭证校验接口 获取 session_key 与 openid
        String openid = "";
        XcxLoginUser loginUser = remoteUserService.getUserInfoByOpenid(openid);
        // loginUser.setClientKey(client.getClientKey());
        // loginUser.setDeviceType(client.getDeviceType());

        // 生成token
        LoginHelper.login(loginUser, null);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        // loginVo.setClientId(client.getClientId());
        loginVo.setOpenid(openid);
        return loginVo;
    }

}
