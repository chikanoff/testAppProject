package by.itransition.chikanoff.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataExistException extends RuntimeException {

    public DataExistException(String message) {
        super(message);
    }
}
