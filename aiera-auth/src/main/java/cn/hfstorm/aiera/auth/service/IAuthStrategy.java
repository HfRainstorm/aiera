package cn.hfstorm.aiera.auth.service;


import cn.hfstorm.aiera.auth.domain.vo.LoginVo;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import cn.hfstorm.aiera.common.core.utils.SpringUtils;

/**
 * 授权策略
 *
 * @author hmy
 */
public interface IAuthStrategy {

    String BASE_NAME = "AuthStrategy";

    /**
     * 登录
     */
    static LoginVo login(String body, String grantType) {
        // 授权类型和客户端id
        String beanName = grantType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw new ServiceException("授权类型不正确!");
        }
        IAuthStrategy instance = SpringUtils.getBean(beanName);
        return instance.login(body);
    }

    /**
     * 登录
     */
    LoginVo login(String body);

}
