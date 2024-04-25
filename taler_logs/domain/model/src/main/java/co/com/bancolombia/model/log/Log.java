package co.com.bancolombia.model.log;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Log {
    private String id;
    private String application;
    private String type;
    private String module;
    private LocalDateTime timestamp;
    private String summary;
    private String description;
}
