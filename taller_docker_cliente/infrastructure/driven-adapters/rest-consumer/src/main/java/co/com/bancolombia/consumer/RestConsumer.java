package co.com.bancolombia.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RestConsumer implements CommandLineRunner {

    private final WebClient client;
    private static final String[] FIRST_NAMES = {
            "John", "Alice", "Michael", "Emily", "David", "Emma", "James", "Olivia", "Daniel", "Sophia"
    };

    @Override
    public void run(String... args) throws Exception {

        Flux.interval(Duration.ofSeconds(5))
                .flatMap(i -> client
                        .post()
                        .bodyValue(getRequest())
                        .retrieve()
                        .bodyToMono(ResponseDTO.class))
                .subscribe(responseEntity -> System.out.println("jwt: " + responseEntity.getJwt()));
    }
    public static RequestDTO getRequest(){
        return RequestDTO.builder()
                        .usuario(generateRandomName())
                        .clave(generateRandomKey(10))
                        .build();
    }
    public static String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        return firstName;

    }
    public static String generateRandomKey(int length) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[length];
            secureRandom.nextBytes(keyBytes);
            return Base64.getEncoder().encodeToString(keyBytes);

    }
}

