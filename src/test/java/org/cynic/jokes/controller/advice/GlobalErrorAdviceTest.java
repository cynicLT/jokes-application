package org.cynic.jokes.controller.advice;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import feign.FeignException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.cynic.jokes.domain.ApplicationException;
import org.cynic.jokes.domain.http.ErrorHttp;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@ExtendWith({
        MockitoExtension.class,
        InstancioExtension.class
})
@Execution(ExecutionMode.SAME_THREAD)
class GlobalErrorAdviceTest {

    private GlobalErrorAdvice globalErrorAdvice;

    private ListAppender<ILoggingEvent> eventListAppender;

    @BeforeEach
    void setUp() {
        globalErrorAdvice = new GlobalErrorAdvice();
        eventListAppender = new ListAppender<>();

        Logger logger = Logger.class.cast(LoggerFactory.getLogger(globalErrorAdvice.getClass()));
        logger.setLevel(Level.ERROR);
        logger.addAppender(eventListAppender);
        eventListAppender.list.clear();
        eventListAppender.start();
    }

    @Test
    void handleFeignServerExceptionWhenOk() {
        FeignException.FeignServerException exception = Instancio.create(FeignException.FeignServerException.class);

        ErrorHttp result = globalErrorAdvice.handleFeignServerException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo("error.feign.server");

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(ExceptionUtils.getRootCauseMessage(exception));

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

    @Test
    void handleFeignClientExceptionWhenOk() {
        FeignException.FeignClientException exception = Instancio.create(FeignException.FeignClientException.class);

        ErrorHttp result = globalErrorAdvice.handleFeignClientException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo("error.feign.client");

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(ExceptionUtils.getRootCauseMessage(exception));

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

    @Test
    void handleFeignExceptionWhenOk() {
        FeignException exception = Instancio.create(FeignException.class);

        ErrorHttp result = globalErrorAdvice.handleFeignException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo("error.feign");

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(ExceptionUtils.getRootCauseMessage(exception));

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

    @Test
    void handleApplicationExceptionWhenOk() {
        ApplicationException exception = Instancio.create(ApplicationException.class);

        ErrorHttp result = globalErrorAdvice.handleApplicationException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo(exception.getCode());

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(exception.getValues());

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

    @Test
    void handleHttpMethodNotSupportedExceptionWhenOK() {
        HttpRequestMethodNotSupportedException exception = Instancio.create(
                HttpRequestMethodNotSupportedException.class);

        ErrorHttp result = globalErrorAdvice.handleHttpMethodNotSupportedException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo("error.http-method.not-supported");

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(exception.getMethod());

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

    @Test
    void handleConstraintValidationExceptionWhenOK() {
        Set<ConstraintViolation<?>> constraintViolations =
                Set.of(
                        Instancio.of(ConstraintViolationImpl.class)
                                .withTypeParameters(String.class)
                                .set(Select.field("propertyPath"), Instancio.create(PathImpl.class))
                                .create(),
                        Instancio.of(ConstraintViolationImpl.class)
                                .withTypeParameters(String.class)
                                .set(Select.field("propertyPath"), Instancio.create(PathImpl.class))
                                .create(),
                        Instancio.of(ConstraintViolationImpl.class)
                                .withTypeParameters(String.class)
                                .set(Select.field("propertyPath"), Instancio.create(PathImpl.class))
                                .create()
                );
        ConstraintViolationException exception = new ConstraintViolationException(null, constraintViolations);

        ErrorHttp result = globalErrorAdvice.handleConstraintValidationException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo("error.request.not-valid");

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactlyInAnyOrder(
                        exception.getConstraintViolations()
                                .stream()
                                .map(it -> String.join(StringUtils.SPACE, it.getPropertyPath().toString(),
                                        it.getMessage()))
                                .toArray()
                );

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

    @Test
    void handleUnknownExceptionWhenOk() {
        Exception exception = Instancio.create(Exception.class);

        ErrorHttp result = globalErrorAdvice.handleUnknownException(exception);

        Assertions.assertThat(result)
                .extracting(ErrorHttp::code)
                .isEqualTo("error.unknown");

        Assertions.assertThat(result)
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(ExceptionUtils.getRootCauseMessage(exception),
                        ExceptionUtils.getStackTrace(exception));

        Assertions.assertThat(eventListAppender.list.size())
                .isEqualTo(1);

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getMessage)
                .isEqualTo("");

        Assertions.assertThat(eventListAppender.list)
                .element(0)
                .extracting(ILoggingEvent::getThrowableProxy)
                .extracting("throwable")
                .isEqualTo(exception);
    }

}