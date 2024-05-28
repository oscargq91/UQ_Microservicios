package co.com.bancolombia.model.profile.gateways;

import co.com.bancolombia.model.profile.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepository {
    Mono<Profile> updateProfile(Profile profile);
    Mono<Profile> getProfileByUsername(String username);
    Flux<Profile> getProfiles();
}
