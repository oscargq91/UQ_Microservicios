package co.com.bancolombia.mongo;

import co.com.bancolombia.model.profile.Profile;
import co.com.bancolombia.model.profile.gateways.ProfileRepository;
import co.com.bancolombia.mongo.dto.ProfileDTO;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<Profile, ProfileDTO, String, MongoDBRepository>
implements ProfileRepository
{

    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Profile.class));
    }

    @Override
    public Mono<Profile> updateProfile(Profile profile) {
        return super.save(profile);
    }
}
