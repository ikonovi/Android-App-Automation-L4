package ik.secretcloset.exceptions;

public class ReadAppPropertyException extends ApplicationException {
    public ReadAppPropertyException() {
    }

    public ReadAppPropertyException(String message) {
        super(message);
    }

    public ReadAppPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadAppPropertyException(Throwable cause) {
        super(cause);
    }
}
