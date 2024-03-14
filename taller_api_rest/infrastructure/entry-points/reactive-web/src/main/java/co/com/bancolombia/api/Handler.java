package co.com.bancolombia.api;

import co.com.bancolombia.api.common.ValidationRequest;
import co.com.bancolombia.api.dto.request.UserSaveRequestDTO;
import co.com.bancolombia.api.dto.request.UserUpdateRequestDTO;
import co.com.bancolombia.api.helper.UserHelper;
import co.com.bancolombia.model.user.exception.BusinessException;
import co.com.bancolombia.model.user.exception.message.ErrorMessage;
import co.com.bancolombia.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class Handler {
    private final UserUseCase userUseCase;
    private final ValidationRequest validationRequest;
    private static final String ID = "id";

    @NonNull
    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(UserSaveRequestDTO.class)
                .flatMap(validationRequest::validateData)
                .map(UserHelper::getUserFromUserSaveRequestDto)
                .flatMap(userUseCase::saveUser)
                .map(UserHelper::getUserResponseDtoFromUser)
                .flatMap(userResponseDTO -> ServerResponse.status(201).bodyValue(userResponseDTO));
    }

    @NonNull
    public Mono<ServerResponse> findAllUsers(ServerRequest serverRequest) {
        return userUseCase.findAllUsers()
                .collectList()
                .map(UserHelper::getUserListResponseDtoFromUser)
                .flatMap(userListResponseDTO -> ServerResponse.ok().bodyValue(userListResponseDTO));

    }
    @NonNull
    public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable(ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.BAD_REQUEST_ID)))
                .flatMap(userUseCase::deleteUserById)
                .then(ServerResponse.noContent().build());
    }
    @NonNull
    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable(ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.BAD_REQUEST_ID)))
                .flatMap(userUseCase::getUserById)
                .map(UserHelper::getUserResponseDtoFromUser)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.NOT_FOUND_USER)))
                .flatMap(userResponseDTO-> ServerResponse.ok().bodyValue(userResponseDTO));
    }
    @NonNull
    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserUpdateRequestDTO.class)
                .flatMap(validationRequest::validateData)
                .map(UserHelper::getUserFromUserUpdateRequestDto)
                .flatMap(user -> userUseCase.getUserById(user.getId()))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.NOT_FOUND_USER)))
                .flatMap(userUseCase::saveUser)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.NOT_FOUND_USER)))
                .map(UserHelper::getUserResponseDtoFromUser)
                .flatMap(userResponseDTO -> ServerResponse.ok().bodyValue(userResponseDTO));
    }
}
