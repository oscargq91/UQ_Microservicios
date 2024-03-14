package co.com.bancolombia.api.exception.dto;

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
    private List<ErrorDescription> errors;
    @EqualsAndHashCode
    @Setter
    @Getter
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDescription {
        private Integer httpStatus;
        private String domain;
        private String message;
    }
}