package co.com.bancolombia.events;
import co.com.bancolombia.events.dto.EventSpec;
import co.com.bancolombia.events.handlers.EventsHandler;
import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerRegistryConfiguration {

    @Bean
    public HandlerRegistry handlerRegistry(EventsHandler events) {
        return HandlerRegistry.register()
                .listenEvent("user.management.api.rest", events::handleEventA, EventSpec.class);

    }
}
