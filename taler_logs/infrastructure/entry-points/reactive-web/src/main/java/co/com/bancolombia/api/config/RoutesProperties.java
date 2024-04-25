package co.com.bancolombia.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.path-mapping")
public class RoutesProperties {

    private String logs;
    private String logsId;
    private String log;


}