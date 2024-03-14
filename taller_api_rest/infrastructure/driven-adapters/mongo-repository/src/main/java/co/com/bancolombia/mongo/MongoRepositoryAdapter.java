package co.com.bancolombia.mongo;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.mongo.dto.UserDTO;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<User, UserDTO, String, MongoDBRepository> implements UserRepository{

    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> getUserById(String id) {
        return super.findById(id);
    }

    @Override
    public Mono<User> saveUser(User user) {
        return super.save(user);
    }

    @Override
    public Flux<User> findAllUsers() {
        return super.findAll();
    }

    @Override
    public Mono<Void> deleteUserById(String id) {
        return super.deleteById(id);
    }
}
