package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.HeaderRequestDTO;
import co.com.bancolombia.api.dto.RequestJwtDTO;
import co.com.bancolombia.api.helper.ResponseHelper;
import co.com.bancolombia.api.helper.ValidationRequest;
import co.com.bancolombia.api.jwt.ReactiveJwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ValidationRequest validationRequest;
    private static final String NAME = "nombre";

    public Mono<ServerResponse> getJwt(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestJwtDTO.class)
                .flatMap(validationRequest::validateData)
                .flatMap(requestJwtDTO -> ReactiveJwtGenerator.getResponseJwtDTO(requestJwtDTO.getUsuario()))
                .flatMap(responseJwtDTO -> ServerResponse.ok().bodyValue(responseJwtDTO));


    }

    public Mono<ServerResponse> sendGreeting(ServerRequest serverRequest) {
        return Mono.just(HeaderRequestDTO.builder().authorization(serverRequest.headers().firstHeader("authorization")).build())
                .flatMap(validationRequest::validateData)
                .flatMap(headerRequestDTO -> ReactiveJwtGenerator
                        .isTokenValid(headerRequestDTO.getAuthorization(), ResponseHelper.getParam(serverRequest.queryParam(NAME), true)))
                .flatMap(ResponseHelper::getResponseGreeting)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }
}
