package co.com.bancolombia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserDTO {
    @Schema(required = true, description = "Unique identifier for the user.", example = "abc1234")
    private String id;
    @Schema(required = true, description = "Unique identifier chosen by the user to access the system or platform.", example = "pepito")
    private String username;
    @Schema(required = true, description = "Secret code used to authenticate and access the system or platform securely.", example = "123456")
    private String password;
    @Schema(required = true, description = "Email address of the user for communication and authentication.", example = "pepito@uqvirtual.com.co")
    private String email;
}
