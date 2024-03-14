package co.com.bancolombia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class UpdatePasswordRequestDTO {

    @NotBlank(message = "%BAD_REQUEST_PASSWORD%")
    @Schema(required = true, description = "Secret code used to authenticate and access the system or platform securely.", example = "123456")
    private String password;
}
