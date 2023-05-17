package org.cynic.jokes.controller.error;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.Optional;
import org.cynic.jokes.domain.http.ErrorHttp;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.RequestDispatcher;

@Hidden
@RestController
public class GlobalErrorController implements ErrorController {

    @GetMapping("${server.error.path:/error}")
    public ResponseEntity<ErrorHttp> handleError(
            @RequestAttribute(name = RequestDispatcher.ERROR_STATUS_CODE, required = false) Optional<Integer> errorStatusCode,
            @RequestAttribute(RequestDispatcher.ERROR_MESSAGE) String errorMessage) {

        HttpStatus httpStatus = errorStatusCode
                .map(HttpStatus::resolve)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(httpStatus)
                .body(new ErrorHttp("error.global", errorMessage));
    }
}
