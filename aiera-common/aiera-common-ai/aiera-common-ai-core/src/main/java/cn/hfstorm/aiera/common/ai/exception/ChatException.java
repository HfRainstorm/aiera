package cn.hfstorm.aiera.common.ai.exception;


import cn.hfstorm.aiera.common.core.exception.base.BaseException;

import java.io.Serial;

public class ChatException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ChatException(String code, Object... args) {
        super("chat", code, args, null);
    }
}

