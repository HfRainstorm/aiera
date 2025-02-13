package cn.hfstorm.aiera.auth.properties;

import cn.hfstorm.aiera.common.encrypt.enumd.AlgorithmType;
import cn.hfstorm.aiera.common.encrypt.enumd.EncodeType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 加解密属性配置类
 *
 * @author hmy
 * @version 4.6.0
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "aiera-encryptor")
public class EncryptorProperties {

    /**
     * 默认算法
     */
    private AlgorithmType algorithm;

    /**
     * 安全秘钥
     */
    private String password;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 编码方式，base64/hex
     */
    private EncodeType encode;

}
