package co.com.bancolombia.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class LivenessStateHealthIndicator implements ReactiveHealthIndicator {
    private static final LocalDateTime START_TIME = LocalDateTime.now();

    @Override
    public Mono<Health> health() {

        return Mono.just(Health.up()
                .withDetail("from", START_TIME)
                .withDetail("status", "ALIVE")
                .withDetail("uptimeSeconds", getUptimeSeconds())
                .build());
    }
    private long getUptimeSeconds() {
        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        return Duration.ofMillis(uptimeMillis).getSeconds();
    }
}
