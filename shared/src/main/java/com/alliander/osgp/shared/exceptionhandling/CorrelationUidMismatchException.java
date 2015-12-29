package com.alliander.osgp.shared.exceptionhandling;

/**
 * Exception to be thrown when the correlationUid does exist, but is not coupled
 * to the expected (type of) data.
 *
 */
public class CorrelationUidMismatchException extends FunctionalException {

    private static final String MESSAGE = "CorrelationUid does not match any data of the requested type.";
    private static final long serialVersionUID = -2029350937138219841L;

    public CorrelationUidMismatchException(final ComponentType componentType, final Throwable cause) {
        super(FunctionalExceptionType.VALIDATION_ERROR, componentType, cause);
    }

    public CorrelationUidMismatchException(final ComponentType componentType) {
        super(FunctionalExceptionType.VALIDATION_ERROR, componentType);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
