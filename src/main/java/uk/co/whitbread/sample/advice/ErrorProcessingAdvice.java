package uk.co.whitbread.sample.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.co.whitbread.sample.exception.AbstractMALException;
import uk.co.whitbread.sample.model.ErrorResponse;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Oleksandr Murha on 02/11/2016.
 */
@RestControllerAdvice
public class ErrorProcessingAdvice {

    private static final Logger LOG = LogManager.getLogger(ErrorProcessingAdvice.class);
    public static final String VALIDATION_ERROR_CODE = "001";
    public static final String UNCLASSIFIED_ERROR_CODE = "999";

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerException(Exception exception) {
        LOG.error("Processing internal server exception", exception);
        return new ErrorResponse(UNCLASSIFIED_ERROR_CODE, Arrays.asList(exception.getMessage()));
    }

    @ExceptionHandler(value = AbstractMALException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleErrorCode(AbstractMALException exception) {
        LOG.error("Processing MAL exception", exception);
        return new ErrorResponse(exception.getErrorCode(), Arrays.asList(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        LOG.error("Processing MethodArgumentNotValidException");
        return new ErrorResponse("001", getValidationErrorDetails(exception.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException exception) {
        LOG.error("Processing BindException");
        return new ErrorResponse(VALIDATION_ERROR_CODE, getValidationErrorDetails(exception));
    }

    private List<String> getValidationErrorDetails(Errors errors){
       return errors.getFieldErrors().stream().map(this::formatErrorMessage).collect(toList());
    }

    private String formatErrorMessage(FieldError error) {
        return String.format("%s %s", error.getField(), error.getDefaultMessage());
    }
}
