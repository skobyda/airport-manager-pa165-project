package cz.fi.muni.pa165.project.exceptions;

/**
 * @author Petr Hendrych
 **/
public class AirportManagerException extends RuntimeException {

    public AirportManagerException() {
        super();
    }

    public AirportManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AirportManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirportManagerException(String message) {
        super(message);
    }

    public AirportManagerException(Throwable cause) {
        super(cause);
    }
}
