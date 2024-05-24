package co.com.bancolombia.api;

import co.com.bancolombia.api.common.ValidationRequest;
import co.com.bancolombia.api.dto.ProfileRequestDTO;
import co.com.bancolombia.model.profile.Profile;
import co.com.bancolombia.model.profile.exception.BusinessException;
import co.com.bancolombia.model.profile.exception.message.ErrorMessage;
import co.com.bancolombia.usecase.profile.ProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final ProfileUseCase useCase;

    @NonNull
    public Mono<ServerResponse> updateProfile(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Profile.class)
                .flatMap(profile->  useCase.updateProfileVerified(profile, getAuthorization(serverRequest)))
                .flatMap(userResponseDTO -> ServerResponse.ok().bodyValue(userResponseDTO));
    }
    public String getAuthorization(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("authorization");
        if(token!=null){
            return token;
        }
        throw new BusinessException(ErrorMessage.TOKEN_REQUERIDO);

    }
}
