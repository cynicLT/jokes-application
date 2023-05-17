package org.cynic.jokes.framework.environment;

import org.apache.commons.collections4.CollectionUtils;
import org.cynic.jokes.domain.ApplicationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.List;

public final class SingleActiveProfileEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());

        if (CollectionUtils.isEmpty(activeProfiles)) {
            throw new ApplicationException("error.no.profiles.active");
        } else if (activeProfiles.size() > 1) {
            throw new ApplicationException("error.multiple.profiles.active", activeProfiles);
        }
    }
}
