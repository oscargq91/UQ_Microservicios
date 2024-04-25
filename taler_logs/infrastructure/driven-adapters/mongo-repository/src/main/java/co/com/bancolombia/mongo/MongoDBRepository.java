package co.com.bancolombia.mongo;


import co.com.bancolombia.mongo.dto.LogDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface MongoDBRepository extends ReactiveMongoRepository<LogDTO, String>, ReactiveQueryByExampleExecutor<LogDTO> {
    @Query("{ 'timestamp' : { $gte: ?0, $lte: ?1 } }")
    Flux<LogDTO> findAllLogsBetween(LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable);

    @Query(value = "{ 'application' : ?0, 'timestamp' : { $gte: ?1, $lte: ?2 } }")
    Flux<LogDTO> findLogsByApplicationAndDateBetween(String application, LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable);
}

