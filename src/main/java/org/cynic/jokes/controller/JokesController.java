package org.cynic.jokes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cynic.jokes.domain.http.ErrorHttp;
import org.cynic.jokes.service.JokesService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;


@Tag(name = "Jokes", description = "API for jokes data retrieval")
@RestController
@Validated
@RequestMapping("/jokes")
public class JokesController {

    private final JokesService jokesService;

    public JokesController(JokesService jokesService) {
        this.jokesService = jokesService;
    }


    @Operation(
            operationId = "random",
            summary = "Random joke",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "400",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorHttp.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorHttp.class))
                            }),
            }
    )
    @GetMapping(path = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public String random() {
        return jokesService.random(Optional.empty());
    }


    @Operation(
            operationId = "search",
            summary = "Search jokes",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "query",
                            schema = @Schema(implementation = String.class),
                            description = "Query"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "400",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorHttp.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorHttp.class))
                            }),
            }
    )
    @GetMapping(path = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Validated
    public List<String> search(@PathVariable @Size(min = 3, max = 120) String query) {
        return jokesService.search(query);
    }

    @Operation(
            operationId = "randomPerCategory",
            summary = "Random joke per category",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "category",
                            schema = @Schema(implementation = String.class),
                            description = "Category name"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "400",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorHttp.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorHttp.class))
                            }),
            }
    )
    @GetMapping(path = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Validated
    public String category(@PathVariable @Size(min = 3, max = 120) String category) {
        return jokesService.random(Optional.of(category));
    }
}
