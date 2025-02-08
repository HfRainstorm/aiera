package cn.hfstorm.aiera.common.core.constant;

/**
 * @author : hmy
 * @date : 2025/2/8 15:29
 */
public interface CacheConst {

    /**
     * 系统所有Redis缓存Key前缀 prefix
     */
    String REDIS_KEY_PREFIX = "aiera:";

    /**
     * Auth缓存前缀
     */
    String AUTH_PREFIX = REDIS_KEY_PREFIX + "auth:";

    /**
     * Auth Session缓存前缀
     */
    String AUTH_SESSION_PREFIX = AUTH_PREFIX + "session:";

    /**
     * Auth Session缓存变量前缀
     */
    String AUTH_USER_INFO_KEY = "USER_INFO";

    /**
     * Auth Token缓存变量前缀
     */
    String AUTH_TOKEN_INFO_KEY = "TOKEN_INFO";

    /**
     * 用户信息缓存
     */
    String USER_DETAIL_KEY = REDIS_KEY_PREFIX + "user_details";

    /**
     * 验证码缓存前缀
     */
    String CAPTCHA_PREFIX = REDIS_KEY_PREFIX + "auth:captcha:";

}
