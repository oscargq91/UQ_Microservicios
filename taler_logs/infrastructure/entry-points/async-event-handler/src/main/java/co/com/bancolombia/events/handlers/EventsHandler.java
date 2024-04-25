package co.com.bancolombia.events.handlers;

import co.com.bancolombia.events.dto.EventSpec;
import co.com.bancolombia.model.log.Log;
import co.com.bancolombia.usecase.log.LogUseCase;
import lombok.AllArgsConstructor;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.async.impl.config.annotations.EnableEventListeners;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@EnableEventListeners
public class EventsHandler {
   private final LogUseCase useCase;


    public Mono<Void> handleEventA(DomainEvent<EventSpec> event) {
        System.out.println("event received: " + event.getName() + " ->" + event.getData());

           return Mono.just(event.getData())
                   .map(data-> Log.builder()
                           .type(data.getData().getType())
                           .summary(data.getData().getSummary())
                           .description(data.getData().getDescription())
                           .application(data.getInvoker())
                           .timestamp(LocalDateTime.parse(data.getTime(), DateTimeFormatter.ISO_DATE_TIME))
                           .module(data.getSource())
                           .build())
                   .flatMap(useCase::createLog)
                   .then();
    }
}
