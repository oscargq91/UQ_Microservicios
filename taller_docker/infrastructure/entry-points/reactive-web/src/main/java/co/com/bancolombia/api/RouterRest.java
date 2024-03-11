package co.com.bancolombia.api;

import co.com.bancolombia.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final ApiProperties apiProperties;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET(apiProperties.getGreeting()), handler::sendGreeting)
                .andRoute(POST(apiProperties.getJwt()), handler::getJwt);
    }
}
