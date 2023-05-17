package org.cynic.jokes.service;

import org.assertj.core.api.Assertions;
import org.cynic.jokes.client.chuck_norris.api.JokeControllerApiClient;
import org.cynic.jokes.client.chuck_norris.domain.Joke;
import org.cynic.jokes.client.chuck_norris.domain.JokeSearchResult;
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
class JokesServiceTest {

    private JokesService jokesService;

    @Mock
    private JokeControllerApiClient jokeControllerApiClient;

    @BeforeEach
    void setUp() {
        jokesService = new JokesService(jokeControllerApiClient);
    }


    @Test
    void randomWhenOK() {
        Optional<String> category = Optional.ofNullable(Instancio.create(String.class));
        String random = Instancio.create(String.class);

        Mockito.when(jokeControllerApiClient.getRandomJokeValueUsingGET(category.orElse(null), null))
                .thenReturn(random);

        String result = jokesService.random(category);

        Assertions.assertThat(result)
                .isEqualTo(random);

        Mockito.verify(jokeControllerApiClient, Mockito.times(1))
                .getRandomJokeValueUsingGET(category.orElse(null), null);
    }

    @Test
    void searchWhenOK() {
        String query = Instancio.create(String.class);
        JokeSearchResult data = Instancio.create(JokeSearchResult.class);

        Mockito.when(jokeControllerApiClient.searchUsingGET(query))
                .thenReturn(data);

        List<String> result = jokesService.search(query);

        Assertions.assertThat(result)
                .containsExactlyElementsOf(
                        data.getResult()
                                .stream()
                                .map(Joke::getValue)
                                .toList()
                );
    }
}