package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.Profile;
import reactor.core.publisher.Mono;

public interface ProfileGateway {
    Mono<Profile> getProfile(String username);
    Mono<Profile> updateProfile(Profile profile, String token);
}
