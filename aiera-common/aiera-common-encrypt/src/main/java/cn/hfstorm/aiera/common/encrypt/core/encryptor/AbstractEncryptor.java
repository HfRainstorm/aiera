package cn.hfstorm.aiera.common.encrypt.core.encryptor;

import cn.hfstorm.aiera.common.encrypt.core.EncryptContext;
import cn.hfstorm.aiera.common.encrypt.core.IEncryptor;

/**
 * 所有加密执行者的基类
 *
 * @author hmy
 * @version 4.6.0
 */
public abstract class AbstractEncryptor implements IEncryptor {

    public AbstractEncryptor(EncryptContext context) {
        // 用户配置校验与配置注入
    }

}
