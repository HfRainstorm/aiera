package cn.hfstorm.aiera.common.ai.exception;


import cn.hfstorm.aiera.common.core.exception.base.BaseException;

import java.io.Serial;

public class EmbedException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmbedException(String code, Object... args) {
        super("embed", code, args, null);
    }
}

