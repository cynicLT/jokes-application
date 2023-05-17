package org.cynic.jokes.service;

import org.cynic.jokes.client.chuck_norris.api.JokeControllerApiClient;
import org.cynic.jokes.client.chuck_norris.domain.Joke;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JokesService {

    private final JokeControllerApiClient jokeControllerApiClient;

    public JokesService(JokeControllerApiClient jokeControllerApiClient) {
        this.jokeControllerApiClient = jokeControllerApiClient;
    }

    public String random(Optional<String> category) {
        return jokeControllerApiClient.getRandomJokeValueUsingGET(category.orElse(null), null);
    }

    public List<String> search(String query) {
        return jokeControllerApiClient.searchUsingGET(query)
                .getResult()
                .stream()
                .map(Joke::getValue)
                .toList();
    }
}
