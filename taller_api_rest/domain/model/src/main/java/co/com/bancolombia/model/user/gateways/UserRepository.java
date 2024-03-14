package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User>getUserById(String id);
    Mono<User>saveUser(User user);
    Flux<User>findAllUsers();
    Mono<Void>deleteUserById(String id);
    Mono<User> findByUsername(String username);
}
