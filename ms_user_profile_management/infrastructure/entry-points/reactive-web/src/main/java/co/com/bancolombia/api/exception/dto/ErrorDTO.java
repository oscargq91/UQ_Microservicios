package co.com.bancolombia.api.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    @Schema(required = true, description = "List of error descriptions providing details about any encountered errors during the operation.")
    private List<ErrorDescription> errors;
    @EqualsAndHashCode
    @Setter
    @Getter
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDescription {
        @Schema(required = true, description = "The HTTP status code indicating the outcome of the operation.", example = "404")
        private Integer httpStatus;
        @Schema(required = true, description = "The domain or context to which the message pertains.", example = "/api/v1/users")
        private String domain;
        @Schema(required = true, description = "A descriptive message providing additional information about the operation outcome.",example = "Error in request body.")
        private String message;
    }
}