package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.dto.UserDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MongoDBRepository extends ReactiveMongoRepository<UserDTO, String>, ReactiveQueryByExampleExecutor<UserDTO> {
    @Query("{ 'username' : ?0 }")
    Mono<UserDTO> findByUsername(String username);
}
