package co.com.bancolombia.consumer;

import co.com.bancolombia.model.user.Profile;
import co.com.bancolombia.model.user.gateways.ProfileGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ProfileGateway {
    private final WebClient client;
    @Override
    public Mono<Profile> getProfile(String username) {
        return client
                .get().uri("/".concat(username))
                .retrieve()
                .bodyToMono(Profile.class);
    }

    @Override
    public Mono<Profile> updateProfile(Profile profile, String token) {
        return client
                .put()
                .header("Authorization", token)
                .body(Mono.just(profile), Profile.class)
                .retrieve()
                .bodyToMono(Profile.class);
    }
}
