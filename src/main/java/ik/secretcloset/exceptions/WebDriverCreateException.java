package ik.secretcloset.exceptions;

public class WebDriverCreateException extends ApplicationException {
    public WebDriverCreateException() {
    }

    public WebDriverCreateException(String message) {
        super(message);
    }

    public WebDriverCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebDriverCreateException(Throwable cause) {
        super(cause);
    }
}
