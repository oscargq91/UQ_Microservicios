package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RequestJwtDTO {
    @NotNull(message = "%BAD_REQUEST_USUARIO%")
    private String usuario;

    @NotNull(message = "%BAD_REQUEST_CLAVE%")
    private String clave;
}
