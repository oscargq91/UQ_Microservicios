package co.com.bancolombia.usecase.profile;

import co.com.bancolombia.model.profile.Profile;
import co.com.bancolombia.model.profile.gateways.LoginGateway;
import co.com.bancolombia.model.profile.gateways.ProfileRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProfileUseCase {
    private final ProfileRepository profileRepository;
    private final LoginGateway loginGateway;
    public Mono<Profile> updateProfileVerified(Profile profile, String token){
        return loginGateway.validateToken(token)
                        .map(username -> {
                            Profile profile1 = profile;
                            profile1.setUsername(username);
                            return profile1;
                        })
                                .flatMap(profileRepository::updateProfile);

    }
    public Mono<Profile> saveProfile(Profile profile){
        return profileRepository.updateProfile(profile);

    }
}
