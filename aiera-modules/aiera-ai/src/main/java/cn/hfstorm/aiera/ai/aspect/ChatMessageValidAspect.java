package cn.hfstorm.aiera.ai.aspect;

import cn.hfstorm.aiera.ai.annotation.ValidChatMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * @author : hmy
 * @date : 2025/2/10 9:05
 */

@Aspect
@Component
public class ChatMessageValidAspect {

    private static final String EMPTY_MESSAGE_ERROR = "对话消息不能为空。";

    /**
     * 校验消息内容是否为空
     *
     * @param joinPoint
     * @param validMessage
     */
    @Before("@annotation(validChatMessage)")
    public void validateMessage(JoinPoint joinPoint, ValidChatMessage validMessage) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        String paramName = validMessage.paramName();

        for (int i = 0; i < parameters.length; i++) {
            String msg = extractMessage(parameters[i], args[i], paramName);
            if (msg != null && msg.trim().isEmpty()) {
                throw new IllegalArgumentException(EMPTY_MESSAGE_ERROR);
            }
        }
    }

    /**
     * 根据参数类型和注解提取消息内容
     *
     * @param parameter 参数对象
     * @param arg       参数值
     * @param paramName 获取的参数名
     * @return 消息内容
     */
    private String extractMessage(Parameter parameter, Object arg, String paramName) {
        if (arg == null) return null;

        if (parameter.isAnnotationPresent(RequestParam.class) && arg instanceof String) {
            return (String) arg;
        } else if (parameter.isAnnotationPresent(RequestBody.class)) {
            try {
//                return (String) arg.getClass().getMethod("get" + capitalize(paramName)).invoke(arg);
                // 直接使用字段名访问记录中的字段
                Method method = arg.getClass().getMethod(paramName);
                Object result = method.invoke(arg);
                if (result instanceof Collection<?>) {
                    // 如果 messages 是 List 类型，可以进一步处理或返回
                    return CollectionUtils.isEmpty((Collection<?>) result) ? null : "non-empty";
                }
                return result.toString();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
