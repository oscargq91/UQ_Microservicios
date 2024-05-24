package co.com.bancolombia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProfileRequestDTO {
    private String personalPageUrl;
    private String nickname;
    private boolean contactInfoPublic;
    private String mailingAddress;
    private String biography;
    private String organization;
    private String country;
    private List<String> socialNetworks;

}
