package com.pantrypal.grocerytracker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pantrypal.grocerytracker.model.unit.Unit;
import com.pantrypal.grocerytracker.serialization.UnitDeserializer;
import com.pantrypal.grocerytracker.serialization.UnitSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing Jackson ObjectMapper.
 * Registers custom serializers and deserializers for the {@link Unit} class.
 * Also registers the JavaTimeModule for handling Java date/time types.
 */
@Configuration
public class JacksonConfig {
    /**
     * Configures and provides the customized Jackson ObjectMapper bean.
     *
     * @return The customized Jackson ObjectMapper.
     */
    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Unit.class, new UnitSerializer());
        module.addDeserializer(Unit.class, new UnitDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }
}

