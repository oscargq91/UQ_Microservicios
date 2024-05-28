package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.dto.ProfileDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MongoDBRepository extends ReactiveMongoRepository<ProfileDTO, String>, ReactiveQueryByExampleExecutor<ProfileDTO> {
    @Query("{ 'username' : ?0 }")
    Mono<ProfileDTO> findByUsername(String username);
}
