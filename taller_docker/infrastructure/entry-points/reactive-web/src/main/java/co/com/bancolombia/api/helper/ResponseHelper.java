package co.com.bancolombia.api.helper;

import co.com.bancolombia.api.dto.ResponseDTO;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Optional;

@UtilityClass
public class ResponseHelper {


    public Mono<ResponseDTO> getResponseGreeting(String username) {
        return Mono.just(ResponseDTO.builder()
                        .mensaje("Hola "+username)
                .build());
    }

    public static String getParam(Optional<String> optional, boolean required) {
        var optionalValue = "";
        if (optional.isPresent()) {
            optionalValue = optional.get();
        }
        if (required && optionalValue.isEmpty()) {
            throw new BusinessException(BusinessErrorMessage.PARAM_NAME_IS_REQUIRED);
        }
        return optionalValue;
    }
}
