package xyz.catuns.audiototext.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    protected final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
