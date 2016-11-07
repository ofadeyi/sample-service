package uk.co.whitbread.sample.exception;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
public abstract class AbstractMALException extends RuntimeException{

    public AbstractMALException(String message) {
        super(message);
    }

    public abstract String getErrorCode();
}
