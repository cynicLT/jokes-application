package org.cynic.jokes.controller;

import org.assertj.core.api.Assertions;
import org.cynic.jokes.service.JokesService;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;


@ExtendWith({
        MockitoExtension.class,
        InstancioExtension.class
})
class JokesControllerTest {

    private JokesController jokesController;

    @Mock
    private JokesService jokesService;

    @BeforeEach
    void setUp() {
        this.jokesController = new JokesController(jokesService);
    }

    @Test
    void randomWhenOK() {
        String random = Instancio.create(String.class);

        Mockito.when(jokesService.random(Optional.empty())).thenReturn(random);

        String result = jokesController.random();

        Assertions.assertThat(result)
                .isEqualTo(random);

        Mockito.verify(jokesService, Mockito.times(1)).random(Optional.empty());
    }

    @Test
    void searchWhenOK() {
        String query = Instancio.create(String.class);
        List<String> response = Instancio.ofList(String.class)
                .size(10)
                .create();

        Mockito.when(jokesService.search(query)).thenReturn(response);

        List<String> result = jokesController.search(query);

        Assertions.assertThat(result)
                .isEqualTo(response);

        Mockito.verify(jokesService, Mockito.times(1)).search(query);
    }

    @Test
    void categoryWhenOK() {
        String category = Instancio.create(String.class);
        String random = Instancio.create(String.class);

        Mockito.when(jokesService.random(Optional.of(category))).thenReturn(random);

        String result = jokesController.category(category);

        Assertions.assertThat(result)
                .isEqualTo(random);

        Mockito.verify(jokesService, Mockito.times(1)).random(Optional.of(category));
    }
}