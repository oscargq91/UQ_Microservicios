package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogDTO {
    private String id;
    @NotBlank(message = "%BAD_REQUEST_ATTR%")
    private String application;
    @NotBlank(message = "%BAD_REQUEST_ATTR%")
    private String type;
    @NotBlank(message = "%BAD_REQUEST_ATTR%")
    private String module;
    private LocalDateTime timestamp;
    @NotBlank(message = "%BAD_REQUEST_ATTR%")
    private String summary;
    @NotBlank(message = "%BAD_REQUEST_ATTR%")
    private String description;
}
