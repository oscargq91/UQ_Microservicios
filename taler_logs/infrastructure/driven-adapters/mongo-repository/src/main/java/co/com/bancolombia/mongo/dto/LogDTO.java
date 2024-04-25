package co.com.bancolombia.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "logs")
public class LogDTO {
    @Id
    private String id;
    private String application;
    private String type;
    private String module;
    private LocalDateTime timestamp;
    private String summary;
    private String description;
}
