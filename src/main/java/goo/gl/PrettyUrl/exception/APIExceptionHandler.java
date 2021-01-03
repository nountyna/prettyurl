package goo.gl.PrettyUrl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {APIRequestResponseException.class})
    public ResponseEntity<Object> handleApiException(APIRequestResponseException apiException) {
        HttpStatus alreadyReport = HttpStatus.BAD_REQUEST;
        APIException exception = new APIException(apiException.getMessage(), alreadyReport, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, alreadyReport);
    }
}
