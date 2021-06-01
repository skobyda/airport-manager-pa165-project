package cz.fi.muni.pa165.project.exceptions;

/**
 * @author Petr Hendrych
 * @created 27.05.2021
 * @project airport-manager
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
