package cn.hfstorm.aiera.common.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * @author : hmy
 * @date : 2025/2/8 10:33
 */
@Getter
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 5217759563277879342L;

    private final int code;

    public ServiceException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
}
