package co.com.bancolombia.api;

import co.com.bancolombia.api.common.ValidationRequest;
import co.com.bancolombia.api.dto.ProfileRequestDTO;
import co.com.bancolombia.model.profile.Profile;
import co.com.bancolombia.model.profile.exception.BusinessException;
import co.com.bancolombia.model.profile.exception.message.ErrorMessage;
import co.com.bancolombia.usecase.profile.ProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final ProfileUseCase useCase;
    private static final String USERNAME = "username";

    @NonNull
    public Mono<ServerResponse> updateProfile(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Profile.class)
                .flatMap(profile->  useCase.updateProfileVerified(profile, getAuthorization(serverRequest)))
                .flatMap(userResponseDTO -> ServerResponse.ok().bodyValue(userResponseDTO));
    }
    @NonNull
    public Mono<ServerResponse> getProfileById(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable(USERNAME))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.BAD_REQUEST_ID)))
                .flatMap(useCase::getProfileByUsername)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.NOT_FOUND_USER)))
                .flatMap(profile -> ServerResponse.ok().bodyValue(profile));
    }
    @NonNull
    public Mono<ServerResponse> findAllProfiles(ServerRequest serverRequest) {
        String number = getheader(serverRequest, "page-number");
        String size = getheader(serverRequest, "page-size");
        if (size != null && number != null && !size.isEmpty() && !number.isEmpty()) {
            int pageNumber = Integer.parseInt(number);
            int pageSize = Integer.parseInt(size);
            if (pageSize > 0 && pageNumber > 0) {
                int startIndex = (pageNumber - 1) * pageSize;
                return useCase.getProfiles()
                        .skip(startIndex)
                        .take(pageSize)
                        .collectList()
                        .flatMap(profiles -> ServerResponse.ok().bodyValue(profiles));
            }

        }
        return useCase.getProfiles()
                .collectList()
                .flatMap(profiles -> ServerResponse.ok().bodyValue(profiles));

    }

    public String getAuthorization(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("authorization");
        if(token!=null){
            return token;
        }
        throw new BusinessException(ErrorMessage.TOKEN_REQUERIDO);

    }
    public static String getheader(ServerRequest serverRequest, String  headerName) {
        return serverRequest.headers().firstHeader(headerName);
    }

}
