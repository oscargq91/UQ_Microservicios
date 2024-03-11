package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class HeaderRequestDTO {
    @NotNull(message = "%TOKEN_REQUERIDO%")
    private String authorization;

}
