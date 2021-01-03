package goo.gl.PrettyUrl.exception;

public class APIRequestResponseException extends RuntimeException {

    public APIRequestResponseException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public APIRequestResponseException(String msg) {
        super(msg);
    }
}
