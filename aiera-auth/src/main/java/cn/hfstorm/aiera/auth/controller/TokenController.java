package cn.hfstorm.aiera.auth.controller;

import cn.hfstorm.aiera.auth.domain.vo.LoginVo;
import cn.hfstorm.aiera.auth.form.RegisterBody;
import cn.hfstorm.aiera.auth.service.IAuthStrategy;
import cn.hfstorm.aiera.auth.service.SysLoginService;
import cn.hfstorm.aiera.common.core.domain.R;
import cn.hfstorm.aiera.common.core.domain.model.LoginBody;
import cn.hfstorm.aiera.common.core.utils.JsonUtils;
import cn.hfstorm.aiera.common.satoken.utils.LoginHelper;
import cn.hfstorm.aiera.system.api.RemoteConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;

/**
 * token 控制
 *
 * @author hmy
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final SysLoginService sysLoginService;
    private final ScheduledExecutorService scheduledExecutorService;

    @DubboReference
    private final RemoteConfigService remoteConfigService;

    /**
     * 登录方法
     *
     * @param body 登录信息
     * @return 结果
     */
    // @ApiEncrypt
    @PostMapping("/login")
    public R<LoginVo> login(@RequestBody String body) {
        LoginBody loginBody = JsonUtils.parseObject(body, LoginBody.class);
        // 授权类型
        String grantType = loginBody.getGrantType();

        // 登录
        LoginVo loginVo = IAuthStrategy.login(body, grantType);

        Long userId = LoginHelper.getUserId();
        return R.ok(loginVo);
    }


    /**
     * 登出方法
     */
    @PostMapping("logout")
    public R<Void> logout() {
        sysLoginService.logout();
        return R.ok();
    }

    /**
     * 用户注册
     */
    // @ApiEncrypt
    @PostMapping("register")
    public R<Void> register(@RequestBody RegisterBody registerBody) {
        if (!remoteConfigService.selectRegisterEnabled()) {
            return R.fail("当前系统没有开启注册功能！");
        }
        // 用户注册
        sysLoginService.register(registerBody);
        return R.ok();
    }


}
