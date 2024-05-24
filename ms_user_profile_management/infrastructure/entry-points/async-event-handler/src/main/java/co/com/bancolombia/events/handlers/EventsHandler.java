package co.com.bancolombia.events.handlers;

import co.com.bancolombia.events.dto.EventSpec;
import co.com.bancolombia.model.profile.Profile;
import co.com.bancolombia.usecase.profile.ProfileUseCase;
import lombok.AllArgsConstructor;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.async.impl.config.annotations.EnableEventListeners;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EnableEventListeners
public class EventsHandler {
    private final ProfileUseCase useCase;
    private final List<String>redes=new ArrayList<String>();


    public Mono<Void> handleEventA(DomainEvent<EventSpec> event) {
        System.out.println("event received: " + event.getName() + " ->" + event.getData());

        return Mono.just(event.getData())
                .filter(data->data.getData().getType().equals("PROFILE"))
                .map(data-> Profile.builder()
                        .username(data.getData().getSummary())
                        .biography("pending")
                        .country("pending")
                        .socialNetworks(redes)
                        .nickname("pending")
                        .personalPageUrl("pending")
                        .organization("pending")
                        .contactInfoPublic(false)
                        .mailingAddress("pending")
                        .build())
                .flatMap(useCase::saveProfile)
                .then();
    }
}
