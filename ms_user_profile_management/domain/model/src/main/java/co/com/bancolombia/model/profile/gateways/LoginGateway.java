package co.com.bancolombia.model.profile.gateways;

import reactor.core.publisher.Mono;

public interface LoginGateway {
    Mono<String> validateToken(String token);
}
