package co.com.bancolombia.api;

import co.com.bancolombia.api.config.RoutesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({RoutesProperties.class})
public class RouterRest {
    private final RoutesProperties routesProperties;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(PUT(routesProperties.getProfile()), handler::updateProfile)
                .andRoute(GET(routesProperties.getProfiles()),handler::findAllProfiles)
                .andRoute(GET(routesProperties.getProfileUsername()),handler::getProfileById);
    }
}
