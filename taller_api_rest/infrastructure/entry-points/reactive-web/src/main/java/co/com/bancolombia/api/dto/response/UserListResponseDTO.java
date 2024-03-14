package co.com.bancolombia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserListResponseDTO {
    @Valid
    @Schema(required = true, description = "The user List of the system.")
    private List<UserDTO> users;

}
