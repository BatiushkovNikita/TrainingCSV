package by.expert.soft.web.exception;

public class WebException extends Exception {

    public WebException() {
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebException(String message) {
        super(message);
    }

    public WebException(Throwable cause) {
        super(cause);
    }
}
