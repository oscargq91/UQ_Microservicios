package co.com.bancolombia.events;

import co.com.bancolombia.events.handlers.EventsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivecommons.async.api.HandlerRegistry;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HandlerRegistryConfigurationTest {

    EventsHandler eventsHandler;


    @BeforeEach
    void setUp() {
        eventsHandler = new EventsHandler();

    }

    @Test
    void testHandlerRegistry() {
        HandlerRegistryConfiguration handlerRegistryConfiguration = new HandlerRegistryConfiguration();
        HandlerRegistry handlerRegistry = handlerRegistryConfiguration.handlerRegistry(eventsHandler);
        assertNotNull(handlerRegistry);

    }
}
