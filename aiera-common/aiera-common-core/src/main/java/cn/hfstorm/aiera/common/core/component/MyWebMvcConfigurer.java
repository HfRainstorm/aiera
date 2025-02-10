package cn.hfstorm.aiera.common.core.component;

import cn.hfstorm.aiera.common.core.interceptor.MyI18nInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author : hmy
 * @date : 2025/2/10 10:47
 */
@Configuration
public class MyWebMvcConfigurer  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建一个自定义的国际化拦截器实例
        MyI18nInterceptor myHandlerInterceptor = new MyI18nInterceptor();
        // 使用拦截器注册器注册自定义的国际化拦截器
        InterceptorRegistration loginRegistry = registry.addInterceptor(myHandlerInterceptor);
        // 设置需要拦截的路径模式，这里配置为拦截所有路径（"/**"）
        loginRegistry.addPathPatterns("/**");
    }

//    @Bean
//    public LocaleResolver LocaleResolver() {
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//        //可以设置过期时间
//        localeResolver.setCookieMaxAge(60*6*24*30);
//        localeResolver.setCookieName("locale");
//
//        return localeResolver;
//    }
}
