package co.com.bancolombia.model.events.gateways;

import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<Void> emit(String description, String summary, String type);
}
