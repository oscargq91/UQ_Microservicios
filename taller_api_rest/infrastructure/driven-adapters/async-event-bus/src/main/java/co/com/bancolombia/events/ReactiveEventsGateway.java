package co.com.bancolombia.events;

import co.com.bancolombia.events.dto.EventDataDTO;
import co.com.bancolombia.events.dto.EventSpec;
import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.user.exception.BusinessException;
import co.com.bancolombia.model.user.exception.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.logging.Level;

import static reactor.core.publisher.Mono.from;

@Log
@Component
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements EventsGateway {
    public static final String EVENT_NAME = "user.management.api.rest";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    public static final String APPLICATION_JSON="application/json";
    public static final String  X_WIP = "1.x-wip";

    private final DomainEventBus domainEventBus;



    @Override
    public Mono<Void> emit(String description, String summary , String type) {
        return generateEvent(new EventSpec(),description, summary, type)
                .flatMap(this::emitEvent)
                .onErrorMap(e ->new BusinessException(ErrorMessage.EVENT_ERROR));
    }
    private Mono<Void> emitEvent(DomainEvent<?> domainEvent) {
        return from(domainEventBus.emit(domainEvent))
                .doAfterTerminate(() -> log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{EVENT_NAME, domainEvent.toString()}))
                .onErrorMap(e -> new BusinessException(ErrorMessage.EVENT_ERROR));

    }

    private Mono<DomainEvent<?>> generateEvent(EventSpec evt, String description, String summary, String type) {
        return Mono.just(evt)
                .map(data -> {
                    data.setData(EventDataDTO.builder()
                                    .description(description)
                                    .summary(summary)
                                    .type(type)
                            .build());
                    data.setDataContentType(APPLICATION_JSON);
                    data.setId(UUID.randomUUID().toString());
                    data.setSpecVersion(X_WIP);
                    data.setInvoker("userManagementApiRest");
                    data.setSource("UseCase");
                    data.setTime(DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));
                    data.setType(EVENT_NAME);
                    return data;
                })
                .map(eventSpec -> new DomainEvent<>(eventSpec.getType(), eventSpec.getId(), eventSpec));
    }
}
