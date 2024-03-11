package co.com.bancolombia.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "entry-points.reactive-web")
public class ApiProperties {

    private String greeting;
    private String jwt;
}
