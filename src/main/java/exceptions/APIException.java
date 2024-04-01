package exceptions;

import lombok.*;

@Getter
public class APIException extends RuntimeException {
    private int statusCode;

    public APIException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
