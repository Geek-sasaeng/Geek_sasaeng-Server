package shop.geeksasang.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.exception.response.BaseResponseStatus;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private BaseResponseStatus status;
}
