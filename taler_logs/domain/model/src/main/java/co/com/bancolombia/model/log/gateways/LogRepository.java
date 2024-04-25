package co.com.bancolombia.model.log.gateways;

import co.com.bancolombia.model.log.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface LogRepository {
    Mono<Log> createLog(Log log);
    Flux<Log> getLogs(LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable);
    Flux<Log> getLogsByApplication(String application,LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable);

}
