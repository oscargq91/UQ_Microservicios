package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> getUserById(String id){
        return userRepository.getUserById(id);
    }
    public Mono<User>saveUser(User user){
        return userRepository.saveUser(user);
    }
    public Flux<User> findAllUsers(){
        return userRepository.findAllUsers();
    }
    public Mono<Void>deleteUserById(String id){
        return userRepository.deleteUserById(id);
    }
}
