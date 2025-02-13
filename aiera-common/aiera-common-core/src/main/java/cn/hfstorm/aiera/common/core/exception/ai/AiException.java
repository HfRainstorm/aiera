package cn.hfstorm.aiera.common.core.exception.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * ai exception
 *
 * @author hmy
 * @date : 2025/2/13 17:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class AiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    public AiException(String message) {
        this.message = message;
    }

    public AiException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public AiException setMessage(String message) {
        this.message = message;
        return this;
    }

    public AiException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
