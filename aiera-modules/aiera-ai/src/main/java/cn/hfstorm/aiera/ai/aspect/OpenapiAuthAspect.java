package cn.hfstorm.aiera.ai.aspect;

import cn.dev33.satoken.same.SaSameUtil;
import cn.hfstorm.aiera.ai.annotation.OpenapiAuth;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * 权限校验 切面类
 *
 * @author : hmy
 */

@Slf4j
@Aspect
@Configuration
@AllArgsConstructor
public class OpenapiAuthAspect {


    @Around("@annotation(openapiAuth)")
    public Object around(ProceedingJoinPoint point, OpenapiAuth openapiAuth) throws Throwable {
        String authorization = SaSameUtil.getToken();

        if (authorization == null) {
            throw new AuthException("Authentication Token invalid");
        }

        String value = openapiAuth.value();
        return point.proceed();
    }

}