package co.com.bancolombia.usecase.log;

import co.com.bancolombia.model.log.Log;
import co.com.bancolombia.model.log.gateways.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;

@RequiredArgsConstructor
public class LogUseCase {
    private final LogRepository logRepository;

    public Mono<Log> createLog(Log log){
        return logRepository.createLog(log);
    }
    public Flux<Log> getLogs(LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable){
        return logRepository.getLogs(minDate, maxDate, pageable);
    }
    public Flux<Log> getLogsByApplication(String application, LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable){
        return logRepository.getLogsByApplication(application,minDate, maxDate, pageable);
    }
}
