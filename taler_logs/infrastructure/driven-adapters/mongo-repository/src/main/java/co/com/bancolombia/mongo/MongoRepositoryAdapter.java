package co.com.bancolombia.mongo;

import co.com.bancolombia.model.log.Log;
import co.com.bancolombia.model.log.gateways.LogRepository;
import co.com.bancolombia.mongo.dto.LogDTO;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<Log, LogDTO, String, MongoDBRepository> implements LogRepository {

    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Log.class));
    }

    @Override
    public Mono<Log> createLog(Log log) {
        return super.save(log);
    }

    @Override
    public Flux<Log> getLogs(LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable) {
        return repository.findAllLogsBetween(minDate, maxDate, pageable)
                .map(logDTO -> Log.builder()
                        .id(logDTO.getId())
                        .description(logDTO.getDescription())
                        .summary(logDTO.getSummary())
                        .timestamp(logDTO.getTimestamp())
                        .type(logDTO.getType())
                        .module(logDTO.getModule())
                        .application(logDTO.getApplication())
                        .build());
    }

    @Override
    public Flux<Log> getLogsByApplication(String application, LocalDateTime minDate, LocalDateTime maxDate, Pageable pageable) {
        return repository.findLogsByApplicationAndDateBetween(application,minDate,maxDate,pageable)
                .map(logDTO -> Log.builder()
                        .id(logDTO.getId())
                        .description(logDTO.getDescription())
                        .summary(logDTO.getSummary())
                        .timestamp(logDTO.getTimestamp())
                        .type(logDTO.getType())
                        .module(logDTO.getModule())
                        .application(logDTO.getApplication())
                        .build());
    }
}
