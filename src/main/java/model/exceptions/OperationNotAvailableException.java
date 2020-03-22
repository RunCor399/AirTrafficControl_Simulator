package model.exceptions;

/**
 * 
 * This exception is meant to be used when a plane cannot perform the requested
 * task.
 *
 */
public class OperationNotAvailableException extends Exception {

    private static final long serialVersionUID = -8910627660291867657L;

    public OperationNotAvailableException(final String message) {
        super(message);
    }

}
