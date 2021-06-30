package ik.secretcloset.exceptions;

public class ScrollToElementException extends ApplicationException {
    public ScrollToElementException() {
    }

    public ScrollToElementException(String message) {
        super(message);
    }

    public ScrollToElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScrollToElementException(Throwable cause) {
        super(cause);
    }
}
