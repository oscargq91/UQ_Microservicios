package co.com.bancolombia.api;

import co.com.bancolombia.api.config.RoutesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({RoutesProperties.class})
public class RouterRest {
    private final RoutesProperties routesProperties;

    @Bean
    public RouterFunction<ServerResponse> agreementRoutes(Handler handler) {
        return route()
                .POST(routesProperties.getUser(), handler::saveUser, OpenApi.saveUser())
                .GET(routesProperties.getUser(), handler::findAllUsers,OpenApi.findAllUsers())
                .GET(routesProperties.getUserId(), handler::getUserById,OpenApi.getUserById())
                .DELETE(routesProperties.getUserId(), handler::deleteUserById,OpenApi.deleteUserById())
                .PUT(routesProperties.getUser(), handler::updateUser,OpenApi.updateUserById())
                .POST(routesProperties.getLogin(), handler::validateCredentials,OpenApi.loginUser())
                .PUT(routesProperties.getPassword(), handler::updatePassword,OpenApi.updatePassword())
                .build();
    }
}
