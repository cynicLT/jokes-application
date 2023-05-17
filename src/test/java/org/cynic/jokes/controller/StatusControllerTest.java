package org.cynic.jokes.controller;

import org.assertj.core.api.Assertions;
import org.cynic.jokes.controller.StatusController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatusControllerTest {

    private StatusController statusController;

    @BeforeEach
    void setUp() {
        statusController = new StatusController();
    }

    @Test
    void indexWhenOk() {
        String response = statusController.index();

        Assertions.assertThat(response)
                .isEqualTo("OK");
    }
}