package co.com.bancolombia.model.profile;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Profile {
    private String username;
    private String personalPageUrl;
    private String nickname;
    private boolean contactInfoPublic;
    private String mailingAddress;
    private String biography;
    private String organization;
    private String country;
    private List<String> socialNetworks;

}
