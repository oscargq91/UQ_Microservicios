package co.com.bancolombia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class UserUpdateRequestDTO {

    @Valid
    @NotNull(message = "%BAD_REQUEST_BODY_NULL%")
    @Schema(required = true, description = "The user of the system.")
    private UserDTO user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class UserDTO{
        @NotBlank(message = "%BAD_REQUEST_ID%")
        @Schema(required = true, description = "Unique identifier for the user.", example = "abc1234")
        private String id;
        @NotBlank(message = "%BAD_REQUEST_USERNAME%")
        @Schema(required = true, description = "Unique identifier chosen by the user to access the system or platform.", example = "pepito")
        private String username;
        @NotBlank(message = "%BAD_REQUEST_PASSWORD%")
        @Schema(required = true, description = "Secret code used to authenticate and access the system or platform securely.", example = "123456")
        private String password;
        @NotBlank(message = "%BAD_REQUEST_EMAIL%")
        @Schema(required = true, description = "Email address of the user for communication and authentication.", example = "pepito@uqvirtual.com.co")
        private String email;
    }
}
