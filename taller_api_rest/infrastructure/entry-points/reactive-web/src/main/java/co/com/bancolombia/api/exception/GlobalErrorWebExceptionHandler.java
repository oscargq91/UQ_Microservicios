package co.com.bancolombia.api.exception;

import co.com.bancolombia.api.exception.dto.ErrorDTO;
import co.com.bancolombia.model.user.exception.BusinessException;
import co.com.bancolombia.model.user.exception.message.ErrorMessage;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private static final String ERRORS = "errors";
    private static final String CACHE_RESPONSE_BODY = "CACHE_RESPONSE_BODY";


    public GlobalErrorWebExceptionHandler(DefaultErrorAttributes errorAttributes, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());

    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        return Mono.just(request)
                .map(this::getError)
                .flatMap(Mono::error)
                .onErrorResume(BusinessException.class, this::buildErrorResponse)
                .onErrorResume(this::buildErrorResponse)
                .cast(Tuple2.class)
                .map(tuple -> getErrorAndStatus(tuple, request))
                .flatMap(newTuple -> buildResponse((ErrorDTO.ErrorDescription) newTuple.getT1(),
                        request, (HttpStatus) newTuple.getT2()));
    }



    private Mono<Tuple2<ErrorDTO.ErrorDescription, HttpStatus>> buildErrorResponse(
            BusinessException businessException) {
        return Mono.just(ErrorDTO.ErrorDescription.builder()
                        .message(businessException.getErrorMessage().getReason())
                        .httpStatus(businessException.getErrorMessage().getCode())
                        .build())
                .zipWith(getStatusCode(businessException));
    }

    private Mono<Tuple2<ErrorDTO.ErrorDescription, HttpStatus>> buildErrorResponse(Throwable throwable) {

        String reason = buildMessageDefault(throwable);
        HttpStatus httpStatus = (reason.contains("404 NOT_FOUND")) ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;

        return Mono.just(ErrorDTO.ErrorDescription.builder()
                        .httpStatus((reason.contains("404 NOT_FOUND")) ? ErrorMessage.URI_NOT_FOUND.getCode() :
                              ErrorMessage.UNEXPECTED_EXCEPTION.getCode())
                        .message((reason.contains("404 NOT_FOUND")) ? ErrorMessage.URI_NOT_FOUND.getReason() :
                                ErrorMessage.UNEXPECTED_EXCEPTION.getReason())
                        .build())
                .zipWith(Mono.just(httpStatus));
    }

    private String buildMessageDefault(Throwable throwable) {
        return Optional.ofNullable(throwable.getMessage())
                .orElse(ErrorMessage.UNEXPECTED_EXCEPTION.getReason());

    }



    public static Mono<HttpStatus> getStatusCode(BusinessException businessException) {
        return Mono.just(HttpStatus.valueOf(businessException.getErrorMessage().getCode()));
    }

    private Mono<ServerResponse> buildResponse(ErrorDTO.ErrorDescription errorDto,
                                               final ServerRequest request, HttpStatus httpStatus) {
        Map<String, List<ErrorDTO.ErrorDescription>> errorResponse = Map.of(ERRORS, List.of(errorDto));
        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse)
                .doOnNext(response -> request.attributes().put(CACHE_RESPONSE_BODY, errorResponse));
    }

    private Tuple2<ErrorDTO.ErrorDescription, HttpStatus> getErrorAndStatus(Tuple2<ErrorDTO.ErrorDescription,
            HttpStatus> tuple2, ServerRequest request) {
        ErrorDTO.ErrorDescription error = tuple2.getT1();
        HttpStatus status = tuple2.getT2();
        error = error.toBuilder().domain(request.methodName().concat(":").concat(request.path())).build();
        return Tuples.of(error, status);
    }
}
