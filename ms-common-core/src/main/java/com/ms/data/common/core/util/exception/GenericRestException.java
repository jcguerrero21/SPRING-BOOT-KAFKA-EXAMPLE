package com.ms.data.common.core.util.exception;

import org.springframework.http.HttpStatus;

public class GenericRestException extends RuntimeException {

    private String message;

    private HttpStatus httpStatus;

    public GenericRestException() {
        super();
    }

    public GenericRestException(final String message) {
        super(message);
    }

    public GenericRestException(final Throwable cause) {
        super(cause);
    }

    public GenericRestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public GenericRestException withMessage(final String message) {
        this.message = message;
        return this;
    }

    public GenericRestException withHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public GenericRestException build() {
        final GenericRestException genericRestException = new GenericRestException();
        genericRestException.setMessage(this.message);
        genericRestException.setHttpStatus(this.httpStatus);

        return genericRestException;
    }
}
