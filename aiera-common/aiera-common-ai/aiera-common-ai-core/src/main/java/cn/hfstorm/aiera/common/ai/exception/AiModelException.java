package cn.hfstorm.aiera.common.ai.exception;


import cn.hfstorm.aiera.common.core.exception.base.BaseException;

import java.io.Serial;

/**
 * ai 模型异常类
 * @author hmy
 */
public class AiModelException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AiModelException(String code, Object... args) {
        super("ai model", code, args, null);
    }
}

