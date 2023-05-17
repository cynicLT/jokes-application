package org.cynic.jokes.controller.advice;

import feign.FeignException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cynic.jokes.domain.ApplicationException;
import org.cynic.jokes.domain.http.ErrorHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalErrorAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorAdvice.class);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationException.class)
    public final ErrorHttp handleApplicationException(ApplicationException exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp(
                exception.getCode(),
                exception.getValues()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.FeignServerException.class)
    public final ErrorHttp handleFeignServerException(FeignException.FeignServerException exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp(
                "error.feign.server",
                ExceptionUtils.getRootCauseMessage(exception)
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.FeignClientException.class)
    public final ErrorHttp handleFeignClientException(FeignException.FeignClientException exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp(
                "error.feign.client",
                ExceptionUtils.getRootCauseMessage(exception)
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public final ErrorHttp handleFeignException(FeignException exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp(
                "error.feign",
                ExceptionUtils.getRootCauseMessage(exception)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ErrorHttp handleHttpMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp(
                "error.http-method.not-supported",
                exception.getMethod()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ErrorHttp handleConstraintValidationException(ConstraintViolationException exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp(
                "error.request.not-valid",
                exception.getConstraintViolations()
                        .stream()
                        .map(it -> String.join(StringUtils.SPACE, it.getPropertyPath().toString(), it.getMessage()))
                        .toArray()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public final ErrorHttp handleUnknownException(Throwable exception) {
        LOGGER.error(StringUtils.EMPTY, exception);

        return new ErrorHttp("error.unknown",
                ExceptionUtils.getRootCauseMessage(exception),
                ExceptionUtils.getStackTrace(exception)
        );
    }
}
