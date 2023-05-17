package org.cynic.jokes.controller.error;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.cynic.jokes.controller.error.GlobalErrorController;
import org.cynic.jokes.domain.http.ErrorHttp;
import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

@ExtendWith({
        MockitoExtension.class,
        InstancioExtension.class
})
class GlobalErrorControllerTest {

    private GlobalErrorController globalErrorController;

    @BeforeEach
    void setUp() {
        this.globalErrorController = new GlobalErrorController();
    }

    @Test
    void handleErrorWhenStatusPresent() {
        Integer errorCode = Instancio.of(Integer.class)
                .generate(Select.root(),
                        gen -> gen.oneOf(Arrays.stream(HttpStatus.values()).map(HttpStatus::value).collect(
                                Collectors.toList())))
                .create();
        String errorMessage = Instancio.create(String.class);

        Assertions.assertThat(globalErrorController.handleError(Optional.of(errorCode), errorMessage))
                .matches(it -> it.getStatusCode().value() == errorCode)
                .matches(HttpEntity::hasBody)
                .extracting(HttpEntity::getBody)
                .matches(it -> StringUtils.equals(it.code(), "error.global"))
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(errorMessage);
    }

    @Test
    void handleErrorWhenStatusNotPresent() {
        String errorMessage = Instancio.create(String.class);

        Assertions.assertThat(globalErrorController.handleError(Optional.empty(), errorMessage))
                .matches(it -> HttpStatus.INTERNAL_SERVER_ERROR.equals(it.getStatusCode()))
                .matches(HttpEntity::hasBody)
                .extracting(HttpEntity::getBody)
                .matches(it -> StringUtils.equals(it.code(), "error.global"))
                .extracting(ErrorHttp::values)
                .asInstanceOf(InstanceOfAssertFactories.ARRAY)
                .containsExactly(errorMessage);
    }
}