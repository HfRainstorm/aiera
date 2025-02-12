package cn.hfstorm.aiera.common.ai.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * ai model exception
 *
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
@Getter
public class ChatException extends RuntimeException {

    private static final long serialVersionUID = 8271158852822204228L;

    private final int code;

    public ChatException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ChatException(String message, Throwable cause) {
        super(message, cause);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ChatException(Throwable cause) {
        super(cause);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ChatException(int code, String message) {
        super(message);
        this.code = code;
    }
}
