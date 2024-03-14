package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Mono;

public interface LoginGateway {
    Mono<String> getTokenJwt(User user);
    Mono<String> validateToken(String token);
}
