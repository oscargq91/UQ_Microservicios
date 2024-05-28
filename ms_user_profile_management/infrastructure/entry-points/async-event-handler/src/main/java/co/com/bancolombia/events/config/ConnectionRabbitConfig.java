package co.com.bancolombia.events.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.PropertyMapper;
import org.reactivecommons.async.rabbit.config.ConnectionFactoryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


@Configuration
@RequiredArgsConstructor
public class ConnectionRabbitConfig {
    private static final String SSL_PROTOCOL = "TLSv1.2";


    private void configureSsl(ConnectionFactory connectionFactory) {
        try {
            connectionFactory.useSslProtocol(SSL_PROTOCOL);
        } catch (NoSuchAlgorithmException | KeyManagementException exception) {
            System.out.println("ERROR SSL_PROTOCOL");
        }
    }

    @Bean
    @Primary
    public ConnectionFactoryProvider connection(){

        final ConnectionFactory factory = new ConnectionFactory();

        PropertyMapper map = PropertyMapper.get();
        map.from("talller-api-rest-rabbit_mq-api-1").whenNonNull().to(factory::setHost);
        map.from("localhost").whenNonNull().to(factory::setHost);
        // map.from(5672).to(factory::setPort);
        map.from("guest").whenNonNull().to(factory::setUsername);
        map.from("guest").whenNonNull().to(factory::setPassword);
        map.from("/").whenNonNull().to(factory::setVirtualHost);
        map.from(false).whenTrue().as(ssl -> factory).to(this::configureSsl);
        return () -> factory;

    }

}