package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.user.Login;
import co.com.bancolombia.model.user.Profile;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.UserInfo;
import co.com.bancolombia.model.user.exception.BusinessException;
import co.com.bancolombia.model.user.exception.message.ErrorMessage;
import co.com.bancolombia.model.user.gateways.LoginGateway;
import co.com.bancolombia.model.user.gateways.ProfileGateway;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final LoginGateway loginGateway;
    private final EventsGateway eventsGateway;
    private final ProfileGateway profileGateway;

    public Mono<User> getUserById(String id){
        return userRepository.getUserById(id);
    }
    public Mono<UserInfo> getUserAndProfile(String token){
        return loginGateway.validateToken(token)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.TOKEN_VALID_ERROR)))
                .flatMap(username ->Mono.zip(userRepository.findByUsername(username),profileGateway.getProfile(username))
                        .map(tuple-> UserInfo.builder()
                                .user(tuple.getT1())
                                .profile(tuple.getT2())
                                .build()));

    }
    public Mono<User>saveUser(User user){
        return userRepository.saveUser(user)
                .doOnSuccess(user1 -> eventsGateway.emit(user1.getId(),
                                user1.getUsername(), "PROFILE")
                        .subscribe());
    }
    public Flux<User> findAllUsers(){
        return userRepository.findAllUsers();
    }
    public Mono<Void>deleteUserById(String id){
        return userRepository.deleteUserById(id);
    }
    public Mono<String> getToken(Login login){
        return userRepository.findByUsername(login.getUsername())
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.NOT_FOUND_USER)))
                .filter(user -> user.getPassword().equals(login.getPassword()))
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.INVALID_PASSWORD)))
                .flatMap(loginGateway::getTokenJwt)
                .doOnSuccess(name -> eventsGateway.emit("Usuario ".concat(login.getUsername()).concat(" ha generado el token de sesion de forma correcta"),
                                "Autenticacion exitosa", "INFO")
                        .subscribe())
                .doOnError(exception -> eventsGateway.emit(exception.getMessage(), "Autenticacion Fallida","ERROR").subscribe());

    }
    public Mono<User> updatePassword(String password, String token){
        return loginGateway.validateToken(token)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.TOKEN_VALID_ERROR)))
                .flatMap(userRepository::findByUsername)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.NOT_FOUND_USER)))
                .flatMap(u-> userRepository.saveUser(User.builder()
                                .username(u.getUsername())
                                .email(u.getEmail())
                                .id(u.getId())
                                .password(password)
                        .build()));
    }
    public Mono<UserInfo>updateUserAndUsername(User user, Profile profile, String token){
        return  loginGateway.validateToken(token)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.TOKEN_VALID_ERROR)))
                        .flatMap(t-> Mono.zip(userRepository.saveUser(user),profileGateway.updateProfile(profile,token)))
                .map(tuple -> UserInfo.builder().user(tuple.getT1()).profile(tuple.getT2()).build());
    }

}
