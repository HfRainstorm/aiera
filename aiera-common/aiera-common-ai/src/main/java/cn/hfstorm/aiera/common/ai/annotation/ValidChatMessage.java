package cn.hfstorm.aiera.common.ai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * valid request chat message
 *
 * @author : hmy
 * @date : 2025/2/10 9:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidChatMessage {

    String paramName() default "messages";
}
