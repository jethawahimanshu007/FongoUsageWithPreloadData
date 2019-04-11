package org.gluecoders.library.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.data.repository.init.ResourceReader;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class CustomRepositoryPopulatorFactoryBean
        extends Jackson2RepositoryPopulatorFactoryBean {

    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    static {
        DEFAULT_MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected ResourceReader getResourceReader() {
        return new CustomJackson2ResourceReader(DEFAULT_MAPPER);
    }
}